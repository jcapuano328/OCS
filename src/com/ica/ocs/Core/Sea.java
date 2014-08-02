package com.ica.ocs.Core;

public class Sea {
	private String[] terrains = new String[] {"Open/Village", "Low Hills", "Hills", "Rough", "Mountain/City", "Port"};
    private int[] openALT = new int[] {5, 3, 2};
    private int[] lowhillsALT = new int[] {6, 4, 3};
    private int[] hillsALT = new int[] {7, 4, 3};
    private int[] roughALT = new int[] {8, 4, 3};
    private int[] mountainALT = new int[] {9, 3, 2};
    private int[] portALT = new int[] {4, 2, 1};

	public Sea() {
	}
	
	public String[] getTerrains() {
        return terrains;
	}
	
	public String landing(int dice, int terrain, boolean nondgunit, boolean dgunit, int coastalarty) {
		String results = "";
		int[] alt = getALT(terrain);
		if (alt != null) {
			if (nondgunit) dice -= 2;
			if (dgunit) dice--;
			dice += (-1 * coastalarty);
			
			if 		(dice >= alt[0]) results = "Success";
			else if (dice >= alt[1]) results = "Mixed (1/2 LC destroyed)";
			else if (dice <= alt[2]) results = "Failure (1/2 LC/units destroyed)";
		}
        return results;
	}
	
	private int[] getALT(int terrain) {
        switch (terrain) {
            case 0: // open
                return openALT;
            case 1: // low hills
                return lowhillsALT;
            case 2: // hills
                return hillsALT;
            case 3: // rough
                return roughALT;
            case 4: // mountain
                return mountainALT;
            case 5: // port
                return portALT;
        }
        return openALT;
	}
}
