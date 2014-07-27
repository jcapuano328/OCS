package com.ica.ocs.Core;

import android.util.JsonReader;
import android.util.JsonWriter;
import java.io.*;
/**
 * Created by jcapuano on 5/31/2014.
 */
public class SavedRepository {

    public static Saved read(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readSaved(reader);
        }
        finally {
            reader.close();
        }
    }
    private static Saved readSaved(JsonReader reader) throws IOException {
        Saved saved = new Saved();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("game")) {
                saved.setGame(reader.nextInt());
            } 
            else if (name.equals("turn")) {
                saved.setTurn(reader.nextInt());
            } 
            else if (name.equals("phase")) {
                saved.setPhase(reader.nextInt());
            } 
            else if (name.equals("weather")) {
                saved.setWeather(reader.nextString());
            } 
            else if (name.equals("initiative")) {
                saved.setInitiative(reader.nextString());
            } 
            else if (name.equals("player1Supply")) {
                saved.setPlayer1Supply(reader.nextString());
            } 
            else if (name.equals("player1Reinforcements")) {
                saved.setPlayer1Reinforcements(reader.nextString());
            } 
            else if (name.equals("player2Supply")) {
                saved.setPlayer2Supply(reader.nextString());
            } 
            else if (name.equals("player2Reinforcements")) {
                saved.setPlayer2Reinforcements(reader.nextString());
            } 
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return saved;
    }

    public static void write(OutputStream out, Saved saved) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        try {
            writeSaved(writer, saved);
        }
        finally {
            writer.close();
        }
    }
    private static void writeSaved(JsonWriter writer, Saved saved) throws IOException {
        writer.beginObject();
        writer.name("game").value(saved.getGame());
        writer.name("turn").value(saved.getTurn());
        writer.name("phase").value(saved.getPhase());
        writer.name("weather").value(saved.getWeather());
        writer.name("initiative").value(saved.getInitiative());
        writer.name("player1Supply").value(saved.getPlayer1Supply());
        writer.name("player1Reinforcements").value(saved.getPlayer1Reinforcements());
        writer.name("player2Supply").value(saved.getPlayer2Supply());
        writer.name("player2Reinforcements").value(saved.getPlayer2Reinforcements());
        writer.endObject();
    }    
}
