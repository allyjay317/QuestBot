package com.allyjay.questbot.pathfinder;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;

import java.util.ArrayList;

public class InitiativeList {
    private class Character{
        String name;
        int initiative;


        public Character(String n, int i){
            name = n;
            initiative = i;
        }


    }
    ArrayList<Character> list = new ArrayList<>();
    int pos = 0;
    Message message;

    public InitiativeList(){

    }

    public void add(String name, int init){
        for(Character c : list){
            if(c.name.compareToIgnoreCase(name) == 0){
                c.initiative = init;
                return;
            }

        }
        list.add(new Character(name, init));
        if(list.size()>1){
            sortList();
        }
    }
    public void showInitiative(MessageChannel channel){
        String init = "///////////////        Battle Start        ///////////////\n";
        for(int i = 0; i< list.size(); i++){
            if(i == pos){
                init = init + "--->";
            }
            init = init + list.get(i).name + ": " + list.get(i).initiative + "\n";
        }
        init = init + "/////////////////        Do  Good        /////////////////";

        message = channel.createMessage(init).block();

    }

    public void remove(String name) {
        for(int i=0;i<list.size();i++){
            if(list.get(i).name.compareToIgnoreCase(name) == 0){
                list.remove(i);
            }
        }

    }


    private void sortList(){
        for(int i=0;i<list.size();i++){
            for(int j=i+1;j<list.size();j++){
                if(list.get(i).initiative<list.get(j).initiative){
                    Character c = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, c);
                }
            }
        }
    }
}
