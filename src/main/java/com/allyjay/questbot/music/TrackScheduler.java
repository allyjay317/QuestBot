package com.allyjay.questbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class TrackScheduler implements AudioLoadResultHandler {
    private final AudioPlayer player;

    public TrackScheduler(final AudioPlayer player){
        this.player = player;
    }

    @Override
    public void trackLoaded(final AudioTrack track){
        player.playTrack(track);
        System.out.println("loaded " + track.getInfo().title);
    }

    @Override
    public void playlistLoaded(final AudioPlaylist playlist){

    }

    @Override
    public void noMatches(){

    }
    @Override
    public void loadFailed(final FriendlyException exception){

    }
}
