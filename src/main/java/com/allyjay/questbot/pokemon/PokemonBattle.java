package com.allyjay.questbot.pokemon;

import discord4j.core.object.entity.User;
import java.sql.*;


import java.util.HashMap;
import java.util.Map;

public class PokemonBattle {
    public static final Map<User, PokeballBelt> users = new HashMap<>();
    public static final Map<String, Pokemon> pokemon = new HashMap<>();

    public static void loadPokemon(){
        Connection c = null;

        try{
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:G:/QuestBot/pokemon.db");
            Statement s = c.createStatement();
            if(s.execute("SELECT * FROM 'Pokemon'")){
                ResultSet results = s.getResultSet();
            }
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //return;
            System.exit(0);
        }

        System.out.println("Connected Successfully to database");

    }

}
