package com.allyjay.questbot;

import com.allyjay.questbot.music.SoundBoardClass;
import com.allyjay.questbot.pokemon.PokemonBattle;
import com.allyjay.questbot.utilities.CommandHandler;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;



public class QuestBot {



    private static DiscordClient client;

    public static void main(String [] args){

        //PokemonBattle.loadPokemon();
        SoundBoardClass.loadBoard();
        client = new DiscordClientBuilder(args[0]).build();
        CommandHandler.startListening(client);
        client.login().block();




    }

    public static void logout(){
        client.logout().block();
    }



}
