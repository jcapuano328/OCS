package com.ica.ocs.Core;

public class Odds {
	private int odds;
	private int index;

	public Odds(int odds, int index) {
		this.odds = odds;
		this.index = index;
	}

    public int getOdds() {
        return odds;
    }

    public void setOdds(int odds) {
        this.odds = odds;
    }

    public int getResultsIndex() {
        return index;
    }

    public void setResultsIndex(int index) {
        this.index = index;
    }
}
