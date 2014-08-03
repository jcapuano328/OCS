package com.ica.ocs.Core;

public class SupplyOps {
    private String[] attritionDensity = new String[] {"< 5", "5+"};
	private String[] attritionAR = new String[] {"-1", "0", "1", "2", "3", "4", "5"};
    private String results;
    private String capturedUnits;
    private String capturedSupply;
    private String capturedTokens;
    private String remainingUnits;
    private String remainingSupply;
    private String remainingTokens;

	public String[] getAttritionDensities() {
        return attritionDensity;
	}
	public String[] getAttritionARs() {
        return attritionAR;
	}
	
	public String getResults() {
        return results;
	}
	
	public String getCapturedUnits() {
        return capturedUnits;
	}
	public String getCapturedSupply() {
        return capturedSupply;
	}
	public String getCapturedTokens() {
        return capturedTokens;
	}

	public String getRemainingUnits() {
        return remainingUnits;
	}
	public String getRemainingSupply() {
        return remainingSupply;
	}
	public String getRemainingTokens() {
        return remainingTokens;
	}

	public void attrition(int dice, int ar, int steps) {
		results = "No Loss";

		if (steps > 0) dice += 3;
		if (ar <= 1) {	// 0
			if 		(dice <= 3)	results = "-1 Step";
			else if (dice <= 5)	results = "-2 Step";
			else if (dice <= 7)	results = "-4 Step";
			else 				results = "All Steps";
		}
		else if (ar <= 2) {	// 1
			if 		(dice <= 2)				results = "No Loss";
			else if (dice <= 4)				results = "-1 Step";
			else if (dice <= 6)				results = "-2 Step";
			else if (dice <= 8)				results = "-4 Step";
			else 							results = "All Steps";
		}
		else if (ar <= 3) {	// 2
			if 		(dice <= 3)				results = "No Loss";
			if 		(dice <= 5)				results = "-1 Step";
			else if (dice <= 7)				results = "-2 Step";
			else if (dice <= 9)				results = "-4 Step";
			else 							results = "All Steps";
		}
		else if (ar <= 4) {	// 3
			if 		(dice <= 5)				results = "No Loss";
			else if (dice <= 7)				results = "-1 Step";
			else if (dice <= 9)				results = "-2 Step";
			else if (dice <= 11)			results = "-4 Step";
			else 							results = "All Steps";
		}
		else { // 4+
			if 		(dice <= 8)				results = "No Loss";
			else if (dice <= 10)			results = "-1 Step";
			else if (dice <= 11)			results = "-2 Step";
			else if (dice <= 12)			results = "-4 Step";
			else 							results = "All Steps";
		}
	}

	public void capture(int die, boolean dump, boolean trucks, boolean wagons, int units, int supply, int tokens) {
		results = "";
		capturedUnits = "";
		capturedSupply = "";
		capturedTokens = "";
		remainingUnits = "";
		remainingSupply = "";
		remainingTokens = "";
	
		double mod = 0.0;
		
		if (dump) {
			if (die <= 1)
				mod = 0.0;
			else if (die <= 3)
				mod = 0.25;
			else if (die <= 5)
				mod = 0.5;
			else if (die <= 6)
				mod = 0.75;
			units = 0;
		}
		else if (trucks) {
			if (die <= 2)
				mod = 0.0;
			else if (die <= 3)
				mod = 0.25;
			else if (die <= 5)
				mod = 0.5;
			else if (die <= 6)
				mod = 0.75;
		}
		else if (wagons) {
			if (die <= 2)
				mod = 0.0;
			else if (die <= 3)
				mod = 0.25;
			else if (die <= 4)
				mod = 0.5;
			else if (die <= 5)
				mod = 0.75;
			else if (die <= 6)
				mod = 1.0;
		}

		setResults(mod, units, supply, tokens);
	}
	
	public void destruction(int die, int supply, int tokens) {
		results = "";
		capturedUnits = "";
		capturedSupply = "";
		capturedTokens = "";
		remainingUnits = "";
		remainingSupply = "";
		remainingTokens = "";
	
		double mod;
		if 		(die <= 1)	mod = 0.25;
		else if (die <= 3)	mod = 0.5;
		else if (die <= 5)	mod = 0.75;
		else 				mod = 1.0;
		
		setResults(mod, 0, supply, tokens);
	}
	
	private void setResults(double mod, int units, int supply, int tokens) {		
		results = Double.toString(mod * 100.0) + "%";
		
		int capturedunits = (int)(((double)units * mod) + 0.5);
		int remainingunits = units - capturedunits;

		int totalsupply = (supply * 4) + tokens;
		int totalcaptured = (int)(((double)totalsupply * mod) + 0.5);
		int totalremaining = totalsupply - totalcaptured;
		
		int capturedsupply = totalcaptured / 4;
		int capturedtokens = totalcaptured % 4;
		
		int remainingsupply = totalremaining / 4;
		int remainingtokens = totalremaining % 4;
		
		capturedUnits = Integer.toString(capturedunits);
		capturedSupply = Integer.toString(capturedsupply);
		capturedTokens = Integer.toString(capturedtokens);
		
		remainingUnits = Integer.toString(remainingunits);
		remainingSupply = Integer.toString(remainingsupply);
		remainingTokens = Integer.toString(remainingtokens);
	}
}

