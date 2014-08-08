package com.ica.ocs.Core;

import java.util.*;

/**
 * Created by jcapuano on 7/30/2014.
 */
public class Weather {
    private DiceDefinition dice;
    private ArrayList<TurnEffects> effects;

    public Weather() {
        dice = new DiceDefinition();
        effects = new ArrayList<TurnEffects>();
    }

    public DiceDefinition getDice() {
        return dice;
    }

    public void setDice(DiceDefinition dice) {
        this.dice = dice;
    }

    public ArrayList<TurnEffects> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<TurnEffects> effects) {
        this.effects = effects;
    }

    public String resolve(int turn, int die1, int die2, int die3, int die4) {
        if (this.dice.isOp("B")) {
            return getEffects(turn, 10*die1 + die2);
        }
        else if (this.dice.isOp("C")) {
            return getEffects(turn, die1);
        }
        else if (this.dice.isOp("D")) {
			return Integer.toString(die1) + " / " + Integer.toString(die2);
        }
        else if (this.dice.isOp("E")) {
			return Integer.toString(10*die1 + die2) + " / " + Integer.toString(die3) + " / " + Integer.toString(die4);
        }
        else {//if (this.dice.isOp("A")) {
            return getEffects(turn, die1 + die2);
        }
    }
    
    private String getEffects(int turn, int dice) {
        for (TurnEffects te : effects) {
            if (te.inRange(turn)) {
                Effect e = te.find(dice);
                if (e != null)
                    return e.getEffect();
            }
        }
    
        return "Clear";
    }        
}
