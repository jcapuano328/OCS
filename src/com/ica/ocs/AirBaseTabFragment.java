package com.ica.ocs;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.ica.ocs.Core.*;
import com.ica.dice.*;


public class AirBaseTabFragment extends BaseTabFragment{

    protected Air air;

    public AirBaseTabFragment(Activity activity, View rootView) {
        super(activity, rootView);
        air = new Air();
    }

    @Override
    public void create() {
	}
    
}