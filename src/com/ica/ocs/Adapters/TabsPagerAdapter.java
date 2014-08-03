package com.ica.ocs.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ica.ocs.AdminFragment;
import com.ica.ocs.GroundFragment;
import com.ica.ocs.BarrageFragment;
import com.ica.ocs.AirFragment;
import com.ica.ocs.SeaFragment;
import com.ica.ocs.SupplyFragment;

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
            case 3:
                return new AirFragment();
            case 4:
                return new SeaFragment();
            case 5:
                return new SupplyFragment();
		}

		return new Fragment();
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 6;
	}

}
