package com.allyjay.questbot.utilities;

import com.allyjay.questbot.QuestBot;
import com.allyjay.questbot.music.SoundBoard;
import com.allyjay.questbot.music.VoiceManager;
import com.allyjay.questbot.pathfinder.PathfinderUtils;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {

    private static final Map<String, Command> commands = new HashMap<>();
    private static final Map<String, Command> pCommands = new HashMap<>();
    private static User self;


    public static void startListening(DiscordClient d){

        VoiceManager.init();

        d.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    final String content = event.getMessage().getContent().orElse("");
                    for(final Map.Entry<String, Command> entry : commands.entrySet()){
                        if(content.startsWith('!' + entry.getKey())) {
                            entry.getValue().execute(event);
                            break;
                        }
                    }
                });
        self = d.getSelf().block();



    }
    //music
    static{
        commands.put("joinvoice", event -> {
            VoiceChannel channel = getVoiceChannel(event);
                   if(channel != null){
                       VoiceManager.joinVoice(channel);

                       VoiceManager.loadItem(channel, "https://www.youtube.com/watch?v=i2I1wK-yllI");
                   }

        });
        commands.put("leavevoice", event -> {
            VoiceChannel channel = getVoiceChannel(event);
                   if(channel != null){
                       VoiceManager.leaveVoice(channel);
                   }

        });
        commands.put("play", event -> {
            VoiceChannel channel = getVoiceChannel(event);
            List<String> command = messageToList(event.getMessage());
                    if(channel != null) {
                        VoiceManager.loadItem(channel, command.get(0));
                    }



        });
        commands.put("stop", event ->{
            VoiceChannel channel = getVoiceChannel(event);
                    if(channel != null) {
                        VoiceManager.stop(channel);
                    }




        });

        commands.put("sb", event ->{
            VoiceChannel channel = getVoiceChannel(event);
            List<String> command = messageToList(event.getMessage());
            String song = SoundBoard.getMusicLink(command.get(0));
            VoiceManager.loadItem(channel, song);
        });

    }
    //utilities
    static {
        commands.put("killme", event ->{
            event.getMessage().delete().block();
            QuestBot.logout();
        });
        commands.put("say", event -> {
           Message m = event.getMessage();
           String say = m.getContent().orElse(null);
           m.delete().block();
           m.getChannel().block().createMessage(say.substring(5)).block();
        });
    }
    //Pathfinder Commands
    static{
        commands.put("p", event ->{
            final List<String> command = messageToList(event.getMessage());
            if(pCommands.containsKey(command.get(0))){
                pCommands.get(command.get(0)).execute(event);
            }
        });

        pCommands.put("init", event ->{
            final List<String> command = messageToList(event.getMessage());
            int init = Integer.parseInt(command.get(command.size()-1));
            String name = "";
            for(int i=1;i<command.size()-1;i++){
                name = name + command.get(i) + " ";
            }
            name = name.trim();
            PathfinderUtils.setInit(event.getGuild().block(), name, init);
            event.getMessage().delete().block();
            event.getMessage().getChannel().block().createMessage(name + " has been added to the initiative order").block();

        });
        pCommands.put("begin", event ->{
            MessageChannel m = event.getMessage().getChannel().block();
            event.getMessage().delete().block();
            PathfinderUtils.showInitiative(event.getGuild().block(), m);
        });
        pCommands.put("next", event ->{
            MessageChannel m = event.getMessage().getChannel().block();
            event.getMessage().delete().block();
            PathfinderUtils.nextTurn(event.getGuild().block(), m);
        });
        pCommands.put("clear", event ->{
            MessageChannel m = event.getMessage().getChannel().block();
            event.getMessage().delete().block();
            PathfinderUtils.clearInit(event.getGuild().block(), m);
        });
        pCommands.put("remove", event ->{
            List<String> command = messageToList(event.getMessage());
            MessageChannel m = event.getMessage().getChannel().block();
            event.getMessage().delete().block();
            PathfinderUtils.removeInit(event.getGuild().block(), command, m);
        });
        pCommands.put("enemyinit", event -> {
            List<String> command = messageToList(event.getMessage());
            MessageChannel m = event.getMessage().getChannel().block();
            event.getMessage().delete().block();
            PathfinderUtils.enemyInit(event.getGuild().block(), command, m);
        });

    }
    static{
        commands.put("pokemon", event -> {

        });
    }
    private static List<String> messageToList(Message message){
        String content = message.getContent().get();
        content = content.substring(content.indexOf(' ')+1);
        List<String> command = Arrays.asList(content.split(" "));
        return command;
    }
    private static VoiceChannel getVoiceChannel(MessageCreateEvent event){
        Member member = event.getMember().orElse((null));
        if(member != null) {
            VoiceState voice = member.getVoiceState().block();
            if (voice != null) {
                VoiceChannel channel = voice.getChannel().block();
                    return channel;
            }
        }
        return null;
    }
}
