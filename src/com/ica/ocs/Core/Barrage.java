package com.ica.ocs.Core;

import java.util.*;

public class Barrage {
    private Terrain terrain;
    
	private String[] types = new String[] {"Ground", "Facility", "Ship-to-Shore", "Ship-to-Ship"};
	private String[] densities = new String[] {"1 or less", "1+ to 3", "3+ to 4", "4+ to 5", "5+ to 6", "6+ or more"};

    private ArrayList<BarrageEffect> groundEffects = new ArrayList<BarrageEffect>();
    private ArrayList<BarrageEffect> facilityEffects = new ArrayList<BarrageEffect>();
    private ArrayList<BarrageEffect> shipToShoreEffects = new ArrayList<BarrageEffect>();
    private ArrayList<BarrageEffect> shipToShipEffects = new ArrayList<BarrageEffect>();
    
	public Barrage(Terrain terrain) {
		this.terrain = terrain;
		init();
	}
	
	public String[] getTypes() {
        return types;
	}
	
	public String[] getDensities() {
        return densities;
	}
	
    public String[] getStrengths(int type) {
        ArrayList<String> l = new ArrayList<String>();
        
        ArrayList<BarrageEffect> effects = getBarrageEffects(type);
        for (BarrageEffect be : effects) {
            l.add(be.getDescription());
        }
        String[] a = new String[l.size()];
        l.toArray(a);
        return a;
    }
    
	public String resolve(int dice, int die, int type, 
						int strength, boolean airclosetobase, boolean spotter, 
						int density, String terrain, boolean stratmovemode, boolean hedgehog) {
		String results = "";
	
		switch (type) {
			case 0:
				results = resolveGround(dice, die, strength, airclosetobase, spotter, density, terrain, stratmovemode, hedgehog);
				break;
			case 1:
				results = resolveFacility(die, strength);
				break;
			case 2:
				results = resolveShipToShore(dice, die, strength);
				break;
			case 3:
				results = resolveShipToShip(dice, die, strength);
				break;
		}
        
        return results;
	}
	
	private String resolveGround(int dice, int die, int strength, boolean airclosetobase, boolean spotter, 
								int density, String terrainWithin, boolean stratmovemode, boolean hedgehog) {
		if 		(dice < 2)  dice = 2;
		else if (dice > 12) dice = 12;

		// determine modifiers
		int shift = 0;
		if (hedgehog)
			shift--;
		if (!spotter)
			shift -= 3;
		if (stratmovemode)
			shift += 3;
		// terrain effects...    
		TerrainEffect te = terrain.get(terrainWithin);
		if (te.getDensity() == "Extremely Close")
			shift -= 2;
		else if (te.getDensity().indexOf("Close") > -1)
			shift--;
		// target density
		shift += (density - 1);
		// air unit proximity to base
		if (airclosetobase)
			shift++;
    
		String results = resolveBarrage(dice-2, shift, strength, 0);
		
		if (results.indexOf("1/2") > -1)
			results += (die > 3) ? " (loss)" : " (no loss)";
        return results;            
	}		
	private String resolveFacility(int die, int strength) {
		if 		(die < 1) die = 1;
		else if (die > 6) die = 6;
		
		return resolveBarrage(die-1, 0, strength, 1);
	}									
	
	private String resolveShipToShore(int dice, int die, int strength) {
		if 		(dice < 2)  dice = 2;
		else if (dice > 12) dice = 12;

		String results = resolveBarrage(dice-2, 0, strength, 2);
		
		if (results.indexOf("1/2") > -1)
			results += (die > 3) ? " (loss)" : " (no loss)";
        return results;            
	}									
	
	private String resolveShipToShip(int dice, int die, int strength) {
		if 		(dice < 2)  dice = 2;
		else if (dice > 12) dice = 12;

		String results = resolveBarrage(dice-2, 0, strength, 3);
		
		if (results.indexOf("1/2") > -1)
			results += (die > 3) ? " (loss)" : " (no loss)";
        return results;            
	}									
	
	private String resolveBarrage(int dice, int shift, int strength, int type) {
        ArrayList<BarrageEffect> be = getBarrageEffects(type);
		
		int index = strength + shift;
		if (index < 0) index = 0;
		else if (index >= be.size()) index = be.size() - 1;
	
		BarrageEffect e = be.get(index);
		return e.getResult(dice);
	}
	
	private void init() {
		initGroundEffects();
		initFacilityEffects();
		initShipToShoreEffects();
        initSupplyTransportEffects();
	}
	
	private void initGroundEffects() {
		groundEffects.add(new BarrageEffect("1 or less - 1T",    new String[] {"NE", "NE", "NE", "NE", "NE", "NE", "NE", "NE", "NE", "DG", "DG"}));
		groundEffects.add(new BarrageEffect("2 to 2 - 1T",       new String[] {"NE", "NE", "NE", "NE", "NE", "NE", "NE", "NE", "DG", "DG", "1/2"}));
		groundEffects.add(new BarrageEffect("3 to 4 - 2T",       new String[] {"NE", "NE", "NE", "NE", "NE", "NE", "NE", "DG", "DG", "DG", "1/2"}));
		groundEffects.add(new BarrageEffect("5 to 7 - 2T",       new String[] {"NE", "NE", "NE", "NE", "NE", "NE", "DG", "DG", "DG", "1/2", "1/2"}));
		groundEffects.add(new BarrageEffect("8 to 11 - 2T",      new String[] {"NE", "NE", "NE", "NE", "NE", "DG", "DG", "DG", "[1/2]", "1/2", "1/2"}));
		groundEffects.add(new BarrageEffect("12 to 16 - 3T",     new String[] {"NE", "NE", "NE", "NE", "DG", "DG", "DG", "[1/2]", "[1/2]", "1/2", "1"}));
		groundEffects.add(new BarrageEffect("17 to 24 - 3T",     new String[] {"NE", "NE", "NE", "DG", "DG", "DG", "DG", "[1/2]", "1/2", "1/2", "1"}));
		groundEffects.add(new BarrageEffect("25 to 40 - 4T",     new String[] {"NE", "NE", "DG", "DG", "DG", "DG", "[1/2]", "[1/2]", "1/2", "1/2", "1"}));
		groundEffects.add(new BarrageEffect("41 to 68 - 5T",     new String[] {"NE", "DG", "DG", "DG", "DG", "[1/2]", "[1/2]", "1/2", "1/2", "1", "1"}));
		groundEffects.add(new BarrageEffect("69 to 116 - 8T",    new String[] {"DG", "DG", "DG", "DG", "[1/2]", "[1/2]", "1/2", "1/2", "1", "1", "2"}));
		groundEffects.add(new BarrageEffect("117 or more - 10T", new String[] {"DG", "DG", "DG", "[1/2]", "[1/2]", "1/2", "1/2", "1/2", "1", "2", "3"}));
	}
	private void initFacilityEffects() {
		facilityEffects.add(new BarrageEffect("1 - 1T",          new String[] {"NE",  "NE",  "NE",  "NE",  "NE",  "(6)*"}));
		facilityEffects.add(new BarrageEffect("2 - 1T",          new String[] {"NE",  "NE",  "NE",  "NE", "(6)",  "(5)*"}));
		facilityEffects.add(new BarrageEffect("3 to 4 - 1T",     new String[] {"NE",  "NE",  "NE",  "NE", "(6)*", "1*(5)"}));
		facilityEffects.add(new BarrageEffect("5 to 10 - 1T",    new String[] {"NE",  "NE",  "NE",  "(6)*", "1*(5)",  "1*(4)"}));
		facilityEffects.add(new BarrageEffect("11 to 20 - 2T",   new String[] {"NE",  "NE",  "(5)", "1*(5)", "1*(4)",  "1*(4)"}));
		facilityEffects.add(new BarrageEffect("21 to 40 - 4T",   new String[] {"NE",  "(5)",  "1(5)",  "1*(4)", "2*(4)",  "2*(4)"}));
		facilityEffects.add(new BarrageEffect("41 to 80 - 6T",   new String[] {"(5)",  "1(4)",  "1(4)", "1*(4)", "2*(4)",  "2*(3)"}));
		facilityEffects.add(new BarrageEffect("81 or more - 8T", new String[] {"1(5)",  "1(4)",  "1(4)",  "2*(4)", "2*(3)",  "2*(3)"}));
	}
	private void initSupplyTransportEffects() {
		shipToShoreEffects.add(new BarrageEffect("1 or 1 - 1T",     new String[] {"0", "0", "0", "0", ".05", ".05"}));
		shipToShoreEffects.add(new BarrageEffect("2 to 4 - 1T",     new String[] {"0", "0", "0", ".05", ".05", ".1"}));
		shipToShoreEffects.add(new BarrageEffect("5 to 8 - 1T",     new String[] {"0", "0", ".05", ".05", ".1", ".1"}));
		shipToShoreEffects.add(new BarrageEffect("9 to 12 - 2T",    new String[] {"0", ".05", ".05", ".1", ".1", ".15"}));
		shipToShoreEffects.add(new BarrageEffect("13 to 24 - 3T",   new String[] {".05", ".05", ".1", ".1", ".15", ".2"}));
		shipToShoreEffects.add(new BarrageEffect("25 or 48 - 4T",   new String[] {".05", ".1", ".1", ".15", ".2", ".25"}));
		shipToShoreEffects.add(new BarrageEffect("49 or 62 - 6T",   new String[] {".1", ".1", ".15", ".2", ".25", ".3"}));
		shipToShoreEffects.add(new BarrageEffect("63 or more - 8T", new String[] {".1", ".15", ".2", ".25", ".3", ".3"}));
	}
	private void initShipToShoreEffects() {
		shipToShipEffects.add(new BarrageEffect("1 or less - 1T",    new String[] {"NE", "NE", "NE", "NE", "NE", "NE", "NE", "NE", "NE", "DG", "DG"}));
		shipToShipEffects.add(new BarrageEffect("2 to 2 - 1T",       new String[] {"NE", "NE", "NE", "NE", "NE", "NE", "NE", "NE", "DG", "DG", "DG"}));
		shipToShipEffects.add(new BarrageEffect("3 to 4 - 2T",       new String[] {"NE", "NE", "NE", "NE", "NE", "NE", "NE", "DG", "DG", "DG", "DG"}));
		shipToShipEffects.add(new BarrageEffect("5 to 7 - 2T",       new String[] {"NE", "NE", "NE", "NE", "NE", "NE", "DG", "DG", "DG", "DG", "DG"}));
		shipToShipEffects.add(new BarrageEffect("8 to 11 - 2T",      new String[] {"NE", "NE", "NE", "NE", "NE", "DG", "DG", "DG", "DG", "DG", "DG"}));
		shipToShipEffects.add(new BarrageEffect("12 to 16 - 3T",     new String[] {"NE", "NE", "NE", "NE", "DG", "DG", "DG", "DG", "DG", "DG", "1/2"}));
		shipToShipEffects.add(new BarrageEffect("17 to 24 - 3T",     new String[] {"NE", "NE", "NE", "DG", "DG", "DG", "DG", "DG", "DG", "DG", "1/2"}));
		shipToShipEffects.add(new BarrageEffect("25 to 40 - 4T",     new String[] {"NE", "NE", "DG", "DG", "DG", "DG", "DG", "DG", "DG", "DG", "1/2"}));
		shipToShipEffects.add(new BarrageEffect("41 to 68 - 5T",     new String[] {"NE", "DG", "DG", "DG", "DG", "DG", "DG", "DG", "DG", "1/2", "1/2"}));
		shipToShipEffects.add(new BarrageEffect("69 to 116 - 8T",    new String[] {"DG", "DG", "DG", "DG", "DG", "DG", "DG", "DG", "1/2", "1/2", "2"}));
		shipToShipEffects.add(new BarrageEffect("117 or more - 10T", new String[] {"DG", "DG", "DG", "DG", "DG", "DG", "DG", "DG", "1/2", "1", "2"}));
	}

    private ArrayList<BarrageEffect> getBarrageEffects(int type) {
        switch (type) {
            case 0:
                return groundEffects;
            case 1:
                return facilityEffects;
            case 2:
                return shipToShoreEffects;
            case 3:
                return groundEffects;
        }
        return groundEffects;
    }
    
}

