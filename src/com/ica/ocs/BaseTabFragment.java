package com.ica.ocs;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.ica.ocs.Core.*;
import com.ica.dice.*;


public class BaseTabFragment {

    protected Activity activity;
    protected View rootView;
    protected Dice dice;
	protected PlayAudio audio;

    public BaseTabFragment(Activity activity, View rootView) {
        this.activity = activity;
        this.rootView = rootView;
		audio = new PlayAudio (activity);
    }

    public void create() {
	}
    
}