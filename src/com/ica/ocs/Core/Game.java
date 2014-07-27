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
    private int dateIncr1;
    private int dateIncr2;
    private ArrayList<Player> players;

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

    public int getDateIncr1() {
        return dateIncr1;
    }

    public void setDateIncr1(int dateIncr1) {
        this.dateIncr1 = dateIncr1;
    }

    public int getDateIncr2() {
        return dateIncr2;
    }

    public void setDateIncr2(int dateIncr2) {
        this.dateIncr2 = dateIncr2;
    }
    
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    
    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
