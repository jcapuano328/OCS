package com.ica.ocs;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.ica.ocs.Core.*;


public class SupplyBaseTabFragment extends BaseTabFragment{

    protected SupplyOps supply;

    public SupplyBaseTabFragment(Activity activity, View rootView) {
        super(activity, rootView);
        
        supply = new SupplyOps();
    }

    @Override
    public void create() {
	}
    
}