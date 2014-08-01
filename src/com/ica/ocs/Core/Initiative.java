package com.ica.ocs.Core;

/**
 * Created by jcapuano on 7/30/2014.
 */
public class Initiative {

    private String[] players;

    public Initiative(String[] players) {
        this.players = players;
    }

    public String resolve(int dice1, int dice2) {
        int diff = dice1 - dice2;
        if      (diff < 0) return players[1];
        else if (diff > 0) return players[0];
        else			   return "Tie: reroll";

    }
}
