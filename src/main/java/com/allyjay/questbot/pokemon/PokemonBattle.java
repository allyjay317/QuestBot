package com.allyjay.questbot.pokemon;

import com.iwebpp.crypto.TweetNaclFast;
import discord4j.core.object.entity.User;
import java.sql.*;


import java.util.HashMap;
import java.util.Map;

public class PokemonBattle {
    public static final Map<User, PokeballBelt> users = new HashMap<>();
    public static final Map<String, Pokemon> pokemon = new HashMap<>();
    public static final Map<String, Move> moves = new HashMap<>();
    public static double[][] typeAttributes= {
            {1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 0.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {2.0, 1.0, 0.5, 0.5, 1.0, 2.0, 0.5, 0.0, 2.0, 1.0, 1.0 ,1.0, 1.0, 0.5, 2.0, 1.0, 2.0, 0.5},
            {1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 0.5, 0.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0},
            {1.0, 1.0, 0.0, 2.0, 1.0, 2.0, 0.5, 1.0, 2.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0},
            {1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0},
            {1.0, 0.5, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 2.0, 0.5},
            {0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 0.5, 1.0, 2.0, 1.0, 1.0, 2.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 2.0, 0.5, 0.5, 2.0, 1.0, 1.0, 2.0, 0.5, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0},
            {1.0, 1.0, 0.5, 0.5, 2.0, 2.0, 0.5, 1.0, 0.5, 0.5, 2.0, 0.5, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0},
            {1.0, 1.0, 2.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5, 1.0, 1.0, 0.5, 1.0, 1.0},
            {1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 0.0, 1.0},
            {1.0, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 2.0, 1.0, 1.0, 0.5, 2.0, 1.0, 1.0},
            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 0.0},
            {1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5},
            {1.0, 2.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0},

    };
    public static void loadPokemon(){
        loadMoves();



        Connection c = null;

        try{
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:G:/QuestBot/pokemon.db");
            Statement s = c.createStatement();
            if(s.execute("SELECT * FROM 'Pokemon'")){
                ResultSet results = s.getResultSet();
                while(!results.last()){
                    String name = results.getString("Name");
                    String move1 = results.getString("Move1");
                    String move2 = results.getString("Move2");
                    String move3 = results.getString("Move3");
                    String move4 = results.getString("Move4");
                    int hitpoints = results.getInt("HP");
                    pokemon.put(name, new Pokemon(name, move1, move2, move3, move4, hitpoints));
                }
            }
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            //return;
            System.exit(0);
        }

        System.out.println("Connected Successfully to database");

    }

    private static void loadMoves(){
        try {
            Connection c;
            c = DriverManager.getConnection("jdbc:sqlite:G:/QuestBot/pokemon.db");
            Statement s = c.createStatement();
            if(s.execute("SELECT * FROM 'Moves'")){
                ResultSet results = s.getResultSet();
                while(!results.last()){
                    String name = results.getString("Name");
                    int att = results.getInt("Att");
                    int acc = results.getInt("Acc");
                    int eff = results.getInt("Eff");
                    Type type = Type.valueOf(results.getString("Type").toUpperCase());
                    Effect effect = Effect.valueOf(results.getString("Effect").toUpperCase());
                    moves.put(name, new Move(name, att, acc, eff, type, effect));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
