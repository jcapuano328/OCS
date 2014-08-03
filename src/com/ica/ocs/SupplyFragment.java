package com.ica.ocs;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.*;

import java.util.*;

public class SupplyFragment extends Fragment {

    private View rootView;
    
    private TabHost tabHost;
    private ArrayList<BaseTabFragment> tabs = new ArrayList<BaseTabFragment>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            final ViewParent parent = rootView.getParent();
            if (parent != null && parent instanceof ViewGroup)
                ((ViewGroup) parent).removeView(rootView);  
        }
        else {
            rootView = inflater.inflate(R.layout.supply, container, false);
            
            tabHost = (TabHost)rootView.findViewById(R.id.tabHostSupply);//android.R.id.tabhost);
            tabHost.setup();
            
            createTab("supplyattrition", R.id.tabSupplyAttrition, "Attrition", new SupplyAttritionFragment(getActivity(), rootView));
            createTab("supplycapture", R.id.tabSupplyCapture, "Capture", new SupplyCaptureFragment(getActivity(), rootView));
            createTab("supplydestruction", R.id.tabSupplyDestruction, "Destruction", new SupplyDestructionFragment(getActivity(), rootView));
        }
        return rootView;
    }            
    
    private void createTab(String tag, int resid, String title, BaseTabFragment fragment){
        try {
            fragment.create();
            tabs.add(fragment);

            TabHost.TabSpec spec = tabHost.newTabSpec(tag);
            spec.setContent(resid);
            spec.setIndicator(title);
            tabHost.addTab(spec);
        }
        catch (Exception e) {
            Log.e("failed to create tab", e.getMessage());
        }
    }
    
}