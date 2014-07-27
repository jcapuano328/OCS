package com.ica.ocs.Core;

import java.util.*;

public class Supply {
    private int start;
    private int end;
    private ArrayList<Effect> effects;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<Effect> effects) {
        this.effects = effects;
    }

    public boolean inRange(int d) {
        return d < start || (d >= start && d <= end);
    }
}

