package com.ica.ocs.Core;

/**
 * Created by jcapuano on 5/30/2014.
 */
public class Saved {
    private int game;
    private int turn;
    private int phase;
    private String weather;
    private String initiative;
    private String player1Supply;
    private String player1Reinforcements;
    private String player2Supply;
    private String player2Reinforcements;

    public Saved() {
        game = -1;
        turn = 0;
        phase = 0;
        weather = "";
        initiative = "";
        player1Supply = "";
        player1Reinforcements = "";
        player2Supply = "";
        player2Reinforcements = "";
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }
    
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getInitiative() {
        return initiative;
    }

    public void setInitiative(String initiative) {
        this.initiative = initiative;
    }

    public String getPlayer1Supply() {
        return player1Supply;
    }

    public void setPlayer1Supply(String player1Supply) {
        this.player1Supply = player1Supply;
    }

    public String getPlayer1Reinforcements() {
        return player1Reinforcements;
    }

    public void setPlayer1Reinforcements(String player1Reinforcements) {
        this.player1Reinforcements = player1Reinforcements;
    }

    public String getPlayer2Supply() {
        return player2Supply;
    }

    public void setPlayer2Supply(String player2Supply) {
        this.player2Supply = player2Supply;
    }

    public String getPlayer2Reinforcements() {
        return player2Reinforcements;
    }

    public void setPlayer2Reinforcements(String player2Reinforcements) {
        this.player2Reinforcements = player2Reinforcements;
    }

    public boolean isValid() {
        return game >= 0;
    }

    public void reset(Game g) {
		game = g.getId();
		turn = 0;
		phase = 0;
        weather = "";
        initiative = "";
        player1Supply = "";
        player1Reinforcements = "";
        player2Supply = "";
        player2Reinforcements = "";
	}    
}
