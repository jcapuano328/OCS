package com.ica.ocs.Core;

import java.util.*;

public class Player {
    private String player;
    private String name;
    private ArrayList<Supply> supply;
    private ArrayList<Effect> reinforcements;
    
    public Player() {
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Supply> getSupply() {
        return supply;
    }

    public void setSupply(ArrayList<Supply> supply) {
        this.supply = supply;
    }

    public ArrayList<Effect> getReinforcements() {
        return reinforcements;
    }

    public void setReinforcements(ArrayList<Effect> reinforcements) {
        this.reinforcements = reinforcements;
    }

    public String findSupply(int dice, int turn) {
        for (Supply s : supply) {
            if (s.inRange(turn)) {
                Effect e = s.find(dice);
                if (e != null)
                    return e.getEffect();
            }
        }
        return "None";
    }

    public String findReinforcements(int dice, int turn) {
        Effect e = Effect.find(dice, this.reinforcements);
        if (e != null)
            return e.getEffect();
        return "None";
    }
}
