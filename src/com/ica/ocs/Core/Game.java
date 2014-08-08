package com.ica.ocs.Core;

import java.util.*;

/**
 * Created by jcapuano on 5/25/2014.
 */
public class Game {
    private int id;
    private String name;
    private String desc;
    private String image;
    private Calendar startDate;
    private int turns;
    private ArrayList<Integer> turnTable;
    private Weather weather;
    private ArrayList<Player> players;
    private static final String[] phases = {
		"Weather",
		"First Player",				
		"1: Air Unit Refit",
		"1: Reinforcement",				
		"1: Breakout",
		"1: Mode and Movement",
		"1: Mode and Movement - Barrage",
		"1: Supply",
		"1: Reaction - Movement",
		"1: Reaction - Barrage",
		"1: Combat - Barrage",
		"1: Combat - Ground",
		"1: Exploit - Movement",
		"1: Exploit - Barrage",
		"1: Exploit - Ground",
		"1: Clean up",				
		"2: Air Unit Refit",
		"2: Reinforcement",
		"2: Breakout",
		"2: Mode and Movement",
		"2: Mode and Movement - Barrage",
		"2: Supply",
		"2: Reaction - Movement",
		"2: Reaction - Barrage",
		"2: Combat - Barrage",
		"2: Combat - Ground",
		"2: Exploit - Movement",
		"2: Exploit - Barrage",
		"2: Exploit - Ground",
		"2: Clean up"
    };    

    public Game() {
        players = new ArrayList<Player>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDateTime) {
        try {
            this.startDate = com.ica.ocs.Helpers.ISO8601.toCalendar(startDateTime);
        }
        catch (Exception ex) {
        }
    }
    
    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }
    
    public void setTurnTable(ArrayList<Integer> turnTable) {
        this.turnTable = turnTable;
    }
    public ArrayList<Integer> getTurnTable() {
        return this.turnTable;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String[] getPlayerList() {
        ArrayList<String> l = new ArrayList<String>();
        for (Player p : players)
            l.add(p.getName());
        String[] a = new String[l.size()];
        l.toArray(a);
        return a;
    }
    public int getPlayerIndex(String p) {
        for (int i=0; i<players.size(); i++) {
            if (players.get(i).getName().equals(p))
                return i;
        }
        return 0;
    }


    public String[] getPhases() {
        return phases;
    }
    
    public String getPhase(int phase) {
        if (phase < 0)
            return phases[0];
        if (phase >= phases.length)
            return phases[phases.length-1];
        return phases[phase];
    }    
}
