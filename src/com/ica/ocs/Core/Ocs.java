package com.ica.ocs.Core;

import android.content.Context;
import android.util.Log;
import java.io.*;
import java.text.*;
import java.util.*;


public class Ocs {
    private static Context ctx;
    private static List<Game> games;
    private static Saved saved;

    public static void initialize(Context c) {
        ctx = c;
    }
    
    public static List<Game> getGames() {
        try {
            if (games == null) {
                games = GameRepository.read(ctx.getAssets().open("games.json"));
            }
        }
        catch (Exception ex) {
            Log.e("getGames", "Failed to get games", ex);
        }
        return games;
    }
    
    public static Game getGame(int id) {
        List<Game> l = getGames();
        for (Game g : l) {
			if (g.getId() == id)
				return g;
		}
		return null;        
    }
    
    public static Saved getSaved(Game game) {
        try {
            if (saved == null) {
                saved = SavedRepository.read(ctx.openFileInput("saved.json"));
            }
        }
        catch (FileNotFoundException fex) {
            saved = new Saved();
            if (game != null)
                saved.setGame(game.getId());
        }
        catch (Exception ex) {
            Log.e("getSaved", "Failed to get saved game", ex);
        }
        return saved;
    }
    
    public static void saveSaved() {
        try {
            if (saved != null) {
                SavedRepository.write(ctx.openFileOutput("saved.json", 0), saved);
            }
        }
        catch (Exception ex) {
            Log.e("saveSaved", "Failed to save game", ex);
        }
    }
    
    public static void prevTurn(Game game, Saved saved) {
        int turn = saved.getTurn();
        if (--turn < 0) {
            turn = 0;
        }
        else if (turn >= game.getTurns()) {
            turn = game.getTurns() - 1;
        }
        saved.setTurn(turn);
    }
    
    public static void nextTurn(Game game, Saved saved) {
        int turn = saved.getTurn();
        if (turn < 0) {
            turn = 0;
        }
        else if (++turn >= game.getTurns()) {
            turn = game.getTurns() - 1;
        }
        saved.setTurn(turn);
    }
    
    public static String getCurrentTurn(Game game, Saved saved) {
        Calendar start = game.getStartDate();
        int year = start.get(Calendar.YEAR);
        int month = start.get(Calendar.MONTH);
        int lastday = start.getActualMaximum(Calendar.DAY_OF_MONTH);
        int turn = saved.getTurn();
        ArrayList<Integer> turns = game.getTurnTable();
        int turnindex = getTurnIndex(game);
		for (int i=0; i<turn; i++) {
            turnindex++;
			if (turnindex >= turns.size() || (turns.get(turnindex) > lastday)) {
				turnindex = 0;
				if (++month > Calendar.DECEMBER) {
					year++;
					month = Calendar.JANUARY;
				}
			}
		}
        int day = turns.get(turnindex);
        Calendar current = Calendar.getInstance();
        current.set(Calendar.YEAR, year);
        current.set(Calendar.MONTH, month);
        current.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat tft = new SimpleDateFormat ("MMMM d, yyyy");
        return tft.format(current.getTime());
    }
    private static int getTurnIndex(Game game) {
        Calendar start = game.getStartDate();
        int day = start.get(Calendar.DAY_OF_MONTH);
        ArrayList<Integer> turns = game.getTurnTable();
        for (int i=0; i<turns.size(); i++) {
            if (day == turns.get(i))
                return i;
        }
        return 0;
    }
    
    public static void prevPhase(Game game, Saved saved) {
        int phase = saved.getPhase();
		if (--phase < 0) {
            prevTurn(game, saved);
            phase = game.getPhases().length - 1;
		}
		else if (phase >= game.getPhases().length) {
            nextTurn(game, saved);
		    phase = 0;
		}
        saved.setPhase(phase);
    }   
    
    public static void nextPhase(Game game, Saved saved) {
        int phase = saved.getPhase();
		if (phase < 0) {
            prevTurn(game, saved);
            phase = game.getPhases().length - 1;
		}
		else if (++phase >= game.getPhases().length) {
            nextTurn(game, saved);
		    phase = 0;
		}
        saved.setPhase(phase);
    }
    
    public static String getCurrentPhase(Game game, Saved saved) {
        return game.getPhase(saved.getPhase());
    }
}
