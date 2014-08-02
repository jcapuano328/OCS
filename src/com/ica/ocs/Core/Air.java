package com.ica.ocs.Core;

import java.util.*;

public class Air {

	private String[] airBaseLevels = new String[] {"None", "Air Strip", "Level 1", "Level 2", "Level 3"};
	private String[] reductionStates = new String[] {"Active", "Inactive"};
	private String[] transportTypes = new String[] {"Glider", "Paradrop"};
	private String[] transportStatuses = new String[] {"Enemy", "Friendly"};
	private String[] transportTerrains = new String[] {"Clear", "Closed"};
	private String[] patrolZones = new String[] {"No Patrol", "Escort", "No Escort"};

	public Air() {
	}
	
	public String[] getAirBaseLevels() {
        return airBaseLevels;
	}
	public String[] getReductionStates() {
        return reductionStates;
	}
	public String[] getTransportTypes() {
        return transportTypes;
	}
	public String[] getTransportStatuses() {
        return transportStatuses;
	}
	public String[] getTransportTerrains() {
        return transportTerrains;
	}
	public String[] getPatrolZones() {
        return patrolZones;
	}
	
	public String combat(int dice, int die, int attackerstr, int defenderstr) {
		String results = "";
	
		dice += attackerstr;
		dice -= defenderstr;
		if (dice <= 6)
			results = "A Abort";
		else if (dice >= 8)
			results = "D Abort";
		else	
			results = "Both Abort";
		if (die >= 5 && results.length() > 0)
			results += " : Loss";
        return results;            
	}
	
	public String flak(int dice, int airbase, int intrinsic, boolean density, boolean hq, int patrol, boolean trainbusting) {
		String results = "";
		int flak = getAirBaseFlakValue(airbase);
		flak += intrinsic;
		flak += getPatrolZoneFlakValue(patrol);
		if (density) flak++;
		if (hq) flak++;
		if (trainbusting) flak++;

		dice += flak;
		if (dice < 11)
			results = "NE";				
		else
			results = "Loss";
        return results;            
	}
	
	public String transport(int dice, int type, int status, int terrain, boolean soviethandicap) {
		String results = "Success";
		
		if (soviethandicap) dice--;

		if (status == 0) {// friendly
			if (terrain == 0) {	// clear
					// glider
				if ((type == 0 && dice <= 3) ||
					// paradrop
					(type == 1 && dice <= 4))
					results = "Failure";
			}
			else {	// closed
					// glider
				if ((type == 0 && dice <= 4) ||
					// paradrop
					(type == 1 && dice <= 5))
					results = "Failure";
			}
		}
		else {	// enemy
			if (terrain == 0) {	// clear
					// glider
				if ((type == 0 && dice <= 4) ||
					// paradrop
					(type == 1 && dice <= 5))
					results = "Failure";
			}
			else {	// closed
					// glider
				if ((type == 0 && dice <= 5) ||
					// paradrop
					(type == 1 && dice <= 6))
					results = "Failure";
			}
		}
        return results;
	}
	
	public String baseCapture(int dice, int status, boolean advanceaftercombat)  {
		String results = "";
		
		if (advanceaftercombat) dice += 2;
	
		if (status == 0) {// active
			if (dice <= 4)
				results = "Reduce";
			else	
				results = "NE";
		}
		else {	// inactive
			if (dice <= 6)
				results = "Reduce";
			else	
				results = "NE";
		}
        return results;
	}
	
	private int getAirBaseFlakValue(int index) {
		if (index <= 1) return 0;
		return index - 2;
	}
	private int getPatrolZoneFlakValue(int index) {
		if (index < 1)
			return 0;
		else if (index == 1)
			return 1;
		else if (index == 3)
			return 2;
		return 0;
	}
}
