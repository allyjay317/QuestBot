package com.allyjay.questbot.pokemon;

public class Pokemon {
    String p_name;
    Move [] p_moves = new Move[4];
    int p_hp;

    public Pokemon(String name, String move1, String move2, String move3, String move4, int hitpoints) {
        p_name = name;
        p_moves[0] = PokemonBattle.moves.get(move1);
        p_moves[1] = PokemonBattle.moves.get(move2);
        p_moves[2] = PokemonBattle.moves.get(move3);
        p_moves[3] = PokemonBattle.moves.get(move4);
        p_hp = hitpoints;
    }
}
