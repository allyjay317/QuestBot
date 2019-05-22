package com.allyjay.questbot.music;

public enum SoundBoard {
    HELLO ("https://www.youtube.com/watch?v=i2I1wK-yllI"),
    SORRY ("https://www.youtube.com/watch?v=X2xcnXyiIAM"),
    HELP ("https://www.youtube.com/watch?v=WwUO3hZsDXM"),
    THANKS ("https://www.youtube.com/watch?v=lCOQYchMm2Y"),
    GOOD ("https://www.youtube.com/watch?v=MTLtFDkyryI"),
    COMEDY ("https://www.youtube.com/watch?v=8udVih2pRu8")
    ;
    private final String musicLink;
    SoundBoard(String sound){
        this.musicLink = sound;
    }
    public static String getMusicLink(String name){
        name = name.toUpperCase();
        if(name.compareTo("HELLO") == 0){
            return HELLO.musicLink;
        }
        if(name.compareTo("SORRY") == 0){
            return SORRY.musicLink;
        }
        if(name.compareTo("HELP") == 0){
            return HELP.musicLink;
        }
        if(name.compareTo("THANKS") == 0){
            return THANKS.musicLink;
        }
        if(name.compareTo("GOOD") == 0){
            return GOOD.musicLink;
        }
        if(name.compareTo("COMEDY") == 0){
            return COMEDY.musicLink;
        }
        return "https://www.youtube.com/watch?v=OGp9P6QvMjY";
    }

}
