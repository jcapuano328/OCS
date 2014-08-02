package com.ica.ocs;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.ica.ocs.Core.*;
import com.ica.dice.*;


public class BaseTabFragment {

    protected View rootView;
    protected Dice dice;
	protected PlayAudio audio;
    protected Air air;

    public BaseTabFragment(Activity activity, View rootView) {
        this.rootView = rootView;
		audio = new PlayAudio (activity);
        air = new Air();
    }

    public void create() {
	}
    
}