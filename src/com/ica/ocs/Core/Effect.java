package com.ica.ocs.Core;

public class Effect {
    private int lo;
    private int hi;
    private String effect;

    public int getLo() {
        return lo;
    }

    public void setLo(int lo) {
        this.lo = lo;
    }

    public int getHi() {
        return hi;
    }

    public void setHi(int hi) {
        this.hi = hi;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public boolean inRange(int d) {
        return d < lo || (d >= lo && d <= hi);
    }
}

