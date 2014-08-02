package com.ica.ocs.Core;

import java.util.*;

public class BarrageEffect {
	private String description;
	private String[] results;

	public BarrageEffect(String description, String[] results) {
		this.description = description;
		this.results = results;
	}
	
    public String getDescription() {
        return description;
    }
    
    public String getResult(int idx) {
        if (idx < 0) idx = 0;
        else if (idx >= results.length) idx = results.length - 1;
        return results[idx];
    }
    
}
