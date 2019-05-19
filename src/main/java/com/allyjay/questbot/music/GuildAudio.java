package com.allyjay.questbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.voice.AudioProvider;
import discord4j.voice.VoiceConnection;

public class GuildAudio {
    final AudioPlayer player = VoiceManager.getPlayer();
    final AudioProvider provider = new LavaPlayerAudioProvider(player);
    private VoiceConnection connection;

    public GuildAudio(){
    }

    public AudioPlayer getPlayer(){
        return player;
    }

    public void disconnect(){
        connection.disconnect();
    }
    public void setConnection(VoiceConnection c){
        connection = c;
    }
}
