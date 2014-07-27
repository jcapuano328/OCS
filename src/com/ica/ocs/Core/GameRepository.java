package com.ica.ocs.Core;

import android.util.JsonReader;
import android.util.JsonToken;
import java.io.*;
import java.util.*;

public class GameRepository {

    public static ArrayList<Game> read(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readGames(reader);
        }
        finally {
            reader.close();
        }
    }
    private static ArrayList<Game> readGames(JsonReader reader) throws IOException {
        ArrayList<Game> games = new ArrayList<Game>();

        reader.beginArray();
        while (reader.hasNext()) {
            games.add(readGame(reader));
        }
        reader.endArray();
        return games;
    }

    private static Game readGame(JsonReader reader) throws IOException {
        Game game = new Game();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("id")) {
                game.setId(reader.nextInt());
            } 
            else if (name.equals("name")) {
                game.setName(reader.nextString());
            }                
            else if (name.equals("desc")) {
                game.setDesc(reader.nextString());
            }                
            else if (name.equals("image")) {
                game.setImage(reader.nextString());
            }                
            else if (name.equals("startDate")) {
                game.setStartDate(reader.nextString());
            }                
            else if (name.equals("turns")) {
                game.setTurns(reader.nextInt());
            } 
            else if (name.equals("dateIncr1")) {
                game.setDateIncr1(reader.nextInt());
            } 
            else if (name.equals("dateIncr2")) {
                game.setDateIncr2(reader.nextInt());
            } 
            else if (name.equals("players") && reader.peek() != JsonToken.NULL) {
                game.setPlayers(readPlayers(reader));
            } 
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return game;
    }
    
    
    private static ArrayList<Player> readPlayers(JsonReader reader) throws IOException {
        ArrayList<Player> l = new ArrayList<Player>();
        
        reader.beginArray();
        while (reader.hasNext()) {
            l.add(readPlayer(reader));
        }            
        reader.endArray();
        return l;
    }
    private static Player readPlayer(JsonReader reader) throws IOException {
        Player p = new Player();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("player")) {
                p.setPlayer(reader.nextString());
            }
            else if (name.equals("name")) {
                p.setName(reader.nextString());
            }
            else if (name.equals("supply") && reader.peek() != JsonToken.NULL) {
                p.setSupply(readSupplies(reader));
            } 
            else if (name.equals("reinforcements") && reader.peek() != JsonToken.NULL) {
                p.setReinforcements(readEffects(reader));
            } 
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return p;
    }
    
    private static ArrayList<Supply> readSupplies(JsonReader reader) throws IOException {
        ArrayList<Supply> l = new ArrayList<Supply>();
        
        reader.beginArray();
        while (reader.hasNext()) {
            l.add(readSupply(reader));
        }            
        reader.endArray();
        return l;
    }
    private static Supply readSupply(JsonReader reader) throws IOException {
        Supply s = new Supply();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("turnStart")) {
                s.setStart(reader.nextInt());
            }
            else if (name.equals("turnEnd")) {
                s.setEnd(reader.nextInt());
            }
            else if (name.equals("effects") && reader.peek() != JsonToken.NULL) {
                s.setEffects(readEffects(reader));
            } 
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return s;
    }
    
    private static ArrayList<Effect> readEffects(JsonReader reader) throws IOException {
        ArrayList<Effect> l = new ArrayList<Effect>();
        
        reader.beginArray();
        while (reader.hasNext()) {
            Effect e = new Effect();
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("low")) {
                    e.setLo(reader.nextInt());
                }
                else if (name.equals("high")) {
                    e.setHi(reader.nextInt());
                }
                else if (name.equals("effect")) {
                    e.setEffect(reader.nextString());
                }
                else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            l.add(e);
        }            
     
        reader.endArray();
        return l;
    }
}
