package com.allyjay.questbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.VoiceChannel;
import discord4j.voice.AudioProvider;
import discord4j.voice.VoiceConnection;

public class VoiceManager {
    private static final Map<Channel, GuildAudio> voiceChannels = new HashMap<>();
    final static AudioPlayerManager playerManager = new DefaultAudioPlayerManager();


    public static AudioProvider getProvider(Channel c){
        return voiceChannels.get(c).provider;
    }
    public static void joinVoice(VoiceChannel channel){
        if(!voiceChannels.containsKey(channel)){
            voiceChannels.put(channel, new GuildAudio());
        }
        GuildAudio guild = voiceChannels.get(channel);
        VoiceConnection voice = channel.join(spec -> spec.setProvider(guild.provider)).block();
        guild.setConnection(voice);


    }
    public static void leaveVoice(Channel c){
        if(voiceChannels.containsKey(c)){
            voiceChannels.get(c).disconnect();
        }
    }
    public static AudioPlayer getPlayer(){
        return playerManager.createPlayer();
    }
    public static void stop(Channel c){
        GuildAudio g = voiceChannels.get(c);
        g.player.stopTrack();
    }

    public static void init(){
        playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        AudioSourceManagers.registerRemoteSources(playerManager);
    }
    public static void loadItem(Channel c, String track){
        AudioPlayer player = voiceChannels.get(c).getPlayer();
        final TrackScheduler scheduler = new TrackScheduler(player);

        playerManager.loadItem(track, scheduler);
    }
}
