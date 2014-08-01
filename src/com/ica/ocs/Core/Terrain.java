package com.ica.ocs.Core;

import java.util.*;

public class Terrain {
    private ArrayList<TerrainEffect> effects;

	public Terrain() {
		init();
	}
	
	public TerrainEffect get(int index) {
        return effects.get(index);
	}
	public TerrainEffect get(String terrain) {
		for (TerrainEffect te : effects) {
			if (te.getDescription() == terrain)
				return te;
		}
		return new TerrainEffect();
	}

    public String[] getList(boolean intervening) {
        ArrayList<String> l = new ArrayList<String>();
        if (intervening)
            l.add("");
        for (TerrainEffect te : effects) {
            if (te.getIsIntervening() == intervening)
                l.add(te.getDescription());
        }
        String[] a = new String[l.size()];
        l.toArray(a);
        return a;
    }
	
	private void init() {
        effects = new ArrayList<TerrainEffect>();
		effects.add(new TerrainEffect("Open", "Open", 2, 2, 1, 1, 1, 1));
		effects.add(new TerrainEffect("Woods", "Close", 1, 1, 1, 1, 1, 1));
		effects.add(new TerrainEffect("Forest", "Close", .5, 1, 1, .5, 1, 1));
		effects.add(new TerrainEffect("Low Hills", "Close", 1, 1, 1, 1, 1, 1));
		effects.add(new TerrainEffect("Hills", "Close", 1, 1, 1, 1, 1, 1));
		effects.add(new TerrainEffect("Rough", "Close", .5, .5, 1, 1, 1, 1));
		effects.add(new TerrainEffect("Mountain", "Very Close", .333, .5, 1, .333, 1, 1));
		effects.add(new TerrainEffect("Village", "Close", 1, 1, 1, 1, 1, 1));
		effects.add(new TerrainEffect("Minor City", "Very Close", .5, .5, 1, .5, 1, 1));
		effects.add(new TerrainEffect("Major City", "Extremely Close", .333, .5, 1, .333, 1, 1));
		effects.add(new TerrainEffect("Minor River", "Other", .5, .5, .5, 1, 1, 1));
		effects.add(new TerrainEffect("Major River", "Other", .25, .333, .5, 1, 1, 1));
		effects.add(new TerrainEffect("Frozen Minor River", "Other", .5, 1, 1, 1, 1, 1));
		effects.add(new TerrainEffect("Frozen Major River", "Other", .5, .5, .5, 1, 1, 1));
		effects.add(new TerrainEffect("Salt Marsh", "Close", .5, .5, 1, .5, 1, 1));
		effects.add(new TerrainEffect("Swamp", "Very Close", .5, .5, 1, .5, .5, 1));
		effects.add(new TerrainEffect("Frozen Swamp", "Close", .5, .5, 1, .5, 1, 1));
		effects.add(new TerrainEffect("Dry Lake", "Other", .333, .5, 1, .333, .5, 1));
		effects.add(new TerrainEffect("Wadi", "Other", .5, .5, 1, 1, 1, 1));
		effects.add(new TerrainEffect("Moscow Defense", "Close", 1, 1, 1, 2, 2, 2));
	}
}