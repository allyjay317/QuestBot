package com.allyjay.questbot.music;

import com.allyjay.questbot.QuestBot;
import discord4j.core.object.entity.MessageChannel;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SoundBoardClass {
    private static final Map<String, String> soundboard = new HashMap<>();
    private static URL database;
    public static boolean addCell(MessageChannel channel, String command, String link){
        if(soundboard.containsKey(command)) {
            channel.createMessage("Command: " + command + " already exists");
            return false;
        }
        soundboard.put(command, link);
        Connection c = null;
        Statement s = null;
        boolean added = false;
        try {
            c = connect();
            s = c.createStatement();
            String sql = "INSERT into links (command, link) values ('"+command+"', '"+link+"')";
            if (s.execute(sql)) {

                added = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{ if(s != null) s.close();}catch (SQLException e){}
            if(c != null) close(c);
        }
        return added;
    }
    public static void loadBoard(){
        Connection c = null;
        Statement s = null;
        ResultSet res = null;
        try{
            c = connect();
            s = c.createStatement();
            s.execute("SELECT * FROM links");
            res = s.getResultSet();
            if(!res.next()){
                //data is empty
            }
            else{
                do{
                    String command = res.getString("command");
                    String link = res.getString("link");
                    soundboard.put(command, link);
                }while(res.next());

            }


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try{ if(res != null) res.close();}catch (SQLException e){}
            try{ if(s != null) s.close();}catch (SQLException e){}
            if(c != null) close(c);
        }



    }
    private static Connection connect() throws SQLException{
        //if(database == null)
            //database = QuestBot.class.getResource("/resources/soundboard.db");
        Connection c;
        c = DriverManager.getConnection("jdbc:sqlite:soundboard.db");
        c.setAutoCommit(false);
        return c;
    }
    private static void close(Connection c)
    {
        if(c == null)
            return;
        try {
            c.commit();
            c.close();
        }
        catch (SQLException e){
            System.err.println("Could not close soundboard.db, data may have been lost");
        }
    }

    public static String getLink(String command) {
        String res = soundboard.get(command);
        if(res == null) return "https://www.youtube.com/watch?v=OGp9P6QvMjY";
        else return res;
    }

    public static Set<String> getCommands() {
        return soundboard.keySet();
    }
}
