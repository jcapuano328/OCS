package com.ica.ocs.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ica.ocs.AdminFragment;
import com.ica.ocs.GroundFragment;
import com.ica.ocs.BarrageFragment;
/*
import com.ica.ocs.CombatFragment;
import com.ica.ocs.VictoryFragment;
import com.ica.ocs.DopFragment;
*/

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);        
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
            return new AdminFragment();
		case 1:
            return new GroundFragment();
		case 2:
            return new BarrageFragment();
        /*
		case 3:
            return new VictoryFragment();
        */
		}

		return new Fragment();
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 6;
	}

}
