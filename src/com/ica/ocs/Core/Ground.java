package com.ica.ocs.Core;

import java.util.*;

public class Ground {
	private Terrain terrain;
	
	private String combatOdds;
	private String combatResults;
	private String surpriseResults;

    private String[] unitar = new String[] {"-1", "0", "1", "2", "3", "4", "5"};
    private String[] hedgehog = new String[] {"0", "1", "2", "3", "4"};
    private ArrayList<Odds> openOdds = new ArrayList<Odds>();
    private ArrayList<Odds> closeOdds = new ArrayList<Odds>();
    private ArrayList<Odds> veryCloseOdds = new ArrayList<Odds>();
    private ArrayList<Odds> extremelyCloseOdds = new ArrayList<Odds>();
	private ArrayList<String[]> resultsTable = new ArrayList<String[]>();
    
	public Ground(Terrain terrain) {
		this.terrain = terrain;
		init();
	}
	public Ground() {
		terrain = new Terrain();
		init();
	}
	
	public String[] getUnitAR() {
        return unitar;
	}
	
	public String[] getHedgehog() {
        return hedgehog;
	}
	
	public String getCombatOdds() {
        return combatOdds;
	}
	
	public String getSurpriseResults() {
        return surpriseResults;
	}
	
	public String getCombatResults() {
        return combatResults;
	}
	
	public int calculate(double attackerArmor, double attackerMech, double attackerOther, boolean attackerCombatSupply, boolean attackerTraceSupply, 
						double defenderArmor, double defenderMech, double defenderOther, boolean defenderCombatSupply, boolean defenderTraceSupply, 
						String terrainwithin, String terrainintervening) {
		combatOdds = "";
		double attackerTotal = modifiedAttackerStrength(attackerArmor, attackerMech, attackerOther, 
														attackerCombatSupply, attackerTraceSupply, 
														defenderArmor, 
														terrainwithin, terrainintervening);
														
		double defenderTotal = modifiedDefenderStrength(defenderArmor, defenderMech, defenderOther, 
														defenderCombatSupply, defenderTraceSupply, 
														terrainwithin, terrainintervening);
														
		if (attackerTotal <= 0 || defenderTotal <= 0) return 0;
		
		int odds = (int)(((attackerTotal > defenderTotal) 
							? (attackerTotal/defenderTotal) 
							: (defenderTotal/attackerTotal)) + 0.5);
		combatOdds = (attackerTotal>defenderTotal) 
							? combatOdds = odds + ":1"
							: "1:" + odds;
		
		return (attackerTotal>defenderTotal) ? odds : (odds*-1);
	}						
	
	public void resolve(double attackerArmor, double attackerMech, double attackerOther, int attackerAR, boolean attackerCombatSupply, boolean attackerTraceSupply, 
						double defenderArmor, double defenderMech, double defenderOther, int defenderAR, boolean defenderCombatSupply, boolean defenderTraceSupply, int defenderHedgehog, 
						String terrainwithin, String terrainintervening, boolean regularcombat,
						int combatdice, int surprisedice, int surprisedie) {
		combatResults = "";
		surpriseResults = "";
	
		int drm = arValue(attackerAR) - arValue(defenderAR) - hedgehogValue(defenderHedgehog);
		
		int odds = calculate(attackerArmor, attackerMech, attackerOther, attackerCombatSupply, attackerTraceSupply, 
						defenderArmor, defenderMech, defenderOther, defenderCombatSupply, defenderTraceSupply, 
						terrainwithin, terrainintervening);
		
		int shift = findSurprise(surprisedice, surprisedie, drm, regularcombat);
		findResults(odds, terrainwithin, combatdice, drm, shift);
	}
	
	private double modifiedAttackerStrength(double attackerArmor, double attackerMech, double attackerOther, 
											boolean attackerCombatSupply, boolean attackerTraceSupply, 
											double defenderArmor, 
											String terrainwithin, String terrainintervening) {
		TerrainEffect terrainWithin = terrain.get(terrainwithin);
		TerrainEffect terrainIntervening = terrain.get(terrainintervening);
		
		if (defenderArmor > 0 && terrainWithin.getAttackerArmor() >= 2)
			attackerArmor *= 1.5;
		else
			attackerArmor *= terrainWithin.getAttackerArmor();
		attackerArmor *= terrainIntervening.getAttackerArmor();
						
		if (defenderArmor > 0 && terrainWithin.getAttackerMech() >= 2)
			attackerMech *= 1.5;
		else
			attackerMech *= terrainWithin.getAttackerMech();
		attackerMech *= terrainIntervening.getAttackerMech();
						
		attackerOther *= terrainWithin.getAttackerOther();
		attackerOther *= terrainIntervening.getAttackerOther();
		
		attackerArmor = modifyForSupply(attackerArmor, attackerCombatSupply);
		attackerArmor = modifyForSupply(attackerArmor, attackerTraceSupply);
		
		attackerMech = modifyForSupply(attackerMech, attackerCombatSupply);
		attackerMech = modifyForSupply(attackerMech, attackerTraceSupply);
		
		attackerOther = modifyForSupply(attackerOther, attackerCombatSupply);
		attackerOther = modifyForSupply(attackerOther, attackerTraceSupply);
			
		return attackerArmor + attackerMech + attackerOther;
	}												
	
	private double modifiedDefenderStrength(double defenderArmor, double defenderMech, double defenderOther,
											boolean defenderCombatSupply, boolean defenderTraceSupply, 
											String terrainwithin, String terrainintervening) {
		TerrainEffect terrainWithin = terrain.get(terrainwithin);
		TerrainEffect terrainIntervening = terrain.get(terrainintervening);
		
		defenderArmor *= terrainWithin.getDefenderArmor();
		defenderArmor *= terrainIntervening.getDefenderArmor();
						
		defenderMech *= terrainWithin.getDefenderMech();
		defenderMech *= terrainIntervening.getDefenderMech();
						
		defenderOther *= terrainWithin.getDefenderOther();
		defenderOther *= terrainIntervening.getDefenderOther();
		
		defenderArmor = modifyForSupply(defenderArmor, defenderCombatSupply);
		defenderArmor = modifyForSupply(defenderArmor, defenderTraceSupply);
		
		defenderMech = modifyForSupply(defenderMech, defenderCombatSupply);
		defenderMech = modifyForSupply(defenderMech, defenderTraceSupply);
		
		defenderOther = modifyForSupply(defenderOther, defenderCombatSupply);
		defenderOther = modifyForSupply(defenderOther, defenderTraceSupply);
			
		return defenderArmor + defenderMech + defenderOther;
	}												
	
	private double modifyForSupply(double v, boolean s) {
		if (!s) v *= 0.5;
		return v;
	}
	
	private int findSurprise(int surprisedice, int surprisedie, int drm, boolean regularcombat) {
		int shift=0;
		surpriseResults = "None";

		int dice = surprisedice + drm;
		if (regularcombat) {
			//  regular: <= 5 is defender; >= 10 is attacker
			if (dice <= 5) {
				surpriseResults = "Defender";
				shift = surprisedie * -1;
			}
			else if (dice >= 10) {
				surpriseResults = "Attacker";
				shift = surprisedie;
			}
		}
		else {
			//  overrun: <= 6 is defender; >= 9 is attacker
			if (dice <= 6) {
				surpriseResults = "Defender";
				shift = surprisedie * -1;
			}
			else if (dice >= 9)	{
				surpriseResults = "Attacker";
				shift = surprisedie;
			}
		}
		return shift;
	}

	private void findResults(int odds, String terrainwithin, int combatdice, int drm, int shift) {
		TerrainEffect terrain = this.terrain.get(terrainwithin);
		
		ArrayList<Odds> co = getOdds(terrain.getDensity());
		int index=co.size();
		for (index--; index>=0; index--) {
            Odds o = co.get(index);
			if (o.getOdds() <= odds || odds > o.getOdds())
				break;
		}
		if (index < 0) index = 0;
		index += shift;
		if 		(index < 0) 		index = 0;
		else if	(index >= co.size()) index = co.size() - 1;
		
		int dice = combatdice + drm;
		if 		(dice < 1)  dice = 1;
		else if (dice > 15) dice = 15;
		
		String[] results = resultsTable.get(index);
		combatResults = results[dice - 1];
	}
	
	private int arValue(int ar) {
		return Integer.parseInt(unitar[ar]);
	}
	private int hedgehogValue(int hh) {
		return Integer.parseInt(hedgehog[hh]);
	}
	
	private void init() {
        initOpenOdds();
        initCloseOdds();
        initVeryCloseOdds();
        initExtremelyCloseOdds();
        initResults();
	}
	
    private ArrayList<Odds> getOdds(String density) {
        if (density == "Open") 
            return openOdds;
        else if (density == "Close") 
            return closeOdds;
        else if (density == "Very Close") 
            return veryCloseOdds;
        else if (density == "Extremely Close") 
            return extremelyCloseOdds;
        return openOdds;
    }
    
	private void initOpenOdds() {
		openOdds.add(new Odds(-5, 0));
		openOdds.add(new Odds(-4, 1));
		openOdds.add(new Odds(-3, 2));
		openOdds.add(new Odds(-2, 3));
		openOdds.add(new Odds( 1, 4));
		openOdds.add(new Odds( 2, 5));
		openOdds.add(new Odds( 3, 6));
		openOdds.add(new Odds( 4, 7));
		openOdds.add(new Odds( 5, 8));
		openOdds.add(new Odds( 7, 9));
		openOdds.add(new Odds( 9, 10));
		openOdds.add(new Odds(11, 11));
		openOdds.add(new Odds(13, 12));
	}
	private void initCloseOdds() {
		closeOdds.add(new Odds(-4, 0));
		closeOdds.add(new Odds(-3, 1));
		closeOdds.add(new Odds(-2, 2));
		closeOdds.add(new Odds( 1, 3));
		closeOdds.add(new Odds( 2, 4));
		closeOdds.add(new Odds( 3, 5));
		closeOdds.add(new Odds( 4, 6));
		closeOdds.add(new Odds( 6, 7));
		closeOdds.add(new Odds( 8, 8));
		closeOdds.add(new Odds(10, 9));
		closeOdds.add(new Odds(12, 10));
		closeOdds.add(new Odds(15, 11));
		closeOdds.add(new Odds(18, 12));
	}
	private void initVeryCloseOdds() {
		veryCloseOdds.add(new Odds(-3, 0));
		veryCloseOdds.add(new Odds(-2, 1));
		veryCloseOdds.add(new Odds( 1, 2));
		veryCloseOdds.add(new Odds( 2, 3));
		veryCloseOdds.add(new Odds( 3, 4));
		veryCloseOdds.add(new Odds( 4, 5));
		veryCloseOdds.add(new Odds( 6, 6));
		veryCloseOdds.add(new Odds( 9, 7));
		veryCloseOdds.add(new Odds(12, 8));
		veryCloseOdds.add(new Odds(15, 9));
		veryCloseOdds.add(new Odds(18, 10));
		veryCloseOdds.add(new Odds(21, 11));
		veryCloseOdds.add(new Odds(24, 12));
	}
	private void initExtremelyCloseOdds() {
		extremelyCloseOdds.add(new Odds(-2, 0));
		extremelyCloseOdds.add(new Odds( 1, 1));
		extremelyCloseOdds.add(new Odds( 2, 2));
		extremelyCloseOdds.add(new Odds( 3, 3));
		extremelyCloseOdds.add(new Odds( 4, 4));
		extremelyCloseOdds.add(new Odds( 8, 5));
		extremelyCloseOdds.add(new Odds(12, 6));
		extremelyCloseOdds.add(new Odds(16, 7));
		extremelyCloseOdds.add(new Odds(20, 8));
		extremelyCloseOdds.add(new Odds(28, 9));
		extremelyCloseOdds.add(new Odds(36, 10));
		extremelyCloseOdds.add(new Odds(44, 11));
		extremelyCloseOdds.add(new Odds(52, 12));
	}
    private void initResults() {
		resultsTable.add(new String[] {"AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1"});
		resultsTable.add(new String[] {"AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1"});
		resultsTable.add(new String[] {"AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2"});
		resultsTable.add(new String[] {"AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2"});
		resultsTable.add(new String[] {"AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2"});
		resultsTable.add(new String[] {"AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG"});
		resultsTable.add(new String[] {"AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG"});
		resultsTable.add(new String[] {"AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG"});
		resultsTable.add(new String[] {"AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG"});
		resultsTable.add(new String[] {"AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG"});
		resultsTable.add(new String[] {"AL1/Do1", "AL1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG"});
		resultsTable.add(new String[] {"AL1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG"});
		resultsTable.add(new String[] {"AL1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG"});
    }
}

/*
Odds1 = -2; -3; -4; -5
"AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1"

Odds2 = 1; -2; -3; -4
"AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1"

Odds3 = 2; 1; -2; -3
"AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2"

Odds4 = 3; 2; 1; -2
"AL2/NE", "AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2"

Odds5 = 4; 3; 2; 1
"AL2/NE", "AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2"

Odds6 = 8; 4; 3; 2
"AL2/NE", "AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG"

Odds7 = 12; 6; 4; 3
"AL2/NE", "AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG"

Odds8 = 16; 9; 6; 4
"AL1o1/NE", "AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG"

Odds9 = 20; 12; 8; 5
"AL1o1/Do1", "AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG"

Odds10 = 28; 15; 10; 7
"AL1o1/Do1", "AL1/Do1", "AL1/Do1", "Ao1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG"

Odds11 = 36; 18; 12; 9
"AL1/Do1", "AL1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG"

Odds12 = 44; 21; 15; 11
"AL1/Do1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG"

Odds13 = 52; 24; 18; 13
"AL1/DL1o1", "Ao1/DL1o1", "Ao1/DL1o1", "Ao1e4/DL1o2", "Ae4/DL1o2", "Ae4/DL1o2", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae3/DL2o2DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG", "Ae2/DL2o3DG"
*/