package com.allyjay.questbot.pathfinder;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.MessageChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathfinderUtils {

    private static final Map<Guild, InitiativeList> initiativeLists = new HashMap<>();

    public static void setInit(Guild guild, String name, int init){
        if(!initiativeLists.containsKey(guild)){
            initiativeLists.put(guild, new InitiativeList());
        }
        InitiativeList list = initiativeLists.get(guild);

        list.add(name, init);

    }
    public static void showInitiative(Guild guild, MessageChannel channel){
        if(!initiativeLists.containsKey(guild)) {
            channel.createMessage("I don't have an initiative list stored for you");
            return;
        }
        InitiativeList list = initiativeLists.get(guild);
        list.showInitiative(channel);

    }
    public static void nextTurn(Guild guild, MessageChannel channel){
        if(!initiativeLists.containsKey(guild)) {
            channel.createMessage("I don't have an initiative list stored for you");
            return;
        }
        InitiativeList list = initiativeLists.get(guild);

        list.pos ++;
        if(list.pos == list.list.size()){
            list.pos = 0;
        }
        list.message.delete().block();
        list.showInitiative(channel);

    }
    public static void clearInit(Guild guild, MessageChannel channel){
        if(!initiativeLists.containsKey(guild)) {
            channel.createMessage("I don't have an initiative list stored for you");
            return;
        }
        InitiativeList list = initiativeLists.get(guild);
        list.message.delete().block();
        initiativeLists.remove(guild);
    }
    public static void removeInit(Guild guild, List<String> command, MessageChannel channel) {
        if (!initiativeLists.containsKey(guild)) {
            channel.createMessage("I don't have an initiative list stored for you");
            return;
        }
        InitiativeList list = initiativeLists.get(guild);
        list.message.delete().block();
        list.remove(command.get(0));
        list.showInitiative(channel);
    }
    public static void enemyInit(Guild guild, List<String> command, MessageChannel channel){
        InitiativeList list = initiativeLists.get(guild);
        channel.createMessage("Sorry, I don't have that functionality yet...");
    }
}
