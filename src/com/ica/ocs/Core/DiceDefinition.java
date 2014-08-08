package com.ica.ocs.Core;

/**
 * Created by jcapuano on 5/30/2014.
 */
public class DiceDefinition {
    private int number;
    private int sides;
    private String op;

    public DiceDefinition() {
        number = 0;
        sides = 0;
        op = "";
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public boolean isOp(String op) {
        return this.op.equals(op);
    }
}
