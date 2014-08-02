package com.ica.ocs;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.content.*;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.*;

import com.ica.ocs.Core.*;
import com.ica.dice.*;


public class BarrageFragment extends Fragment {

    private View rootView;
    
    private Spinner spinBarrageType; 
    private Spinner spinBarrageREs; 
    private Spinner spinBarrageStrength; 
    private Spinner spinBarrageTerrain; 
    private CheckBox checkBarrageAirCloseToBase; 
    private CheckBox checkBarrageSpotter; 
    private CheckBox checkBarrageHedgehog; 
    private CheckBox checkBarrageStratMove; 
    private TextView txtBarrageResults; 
    
    private ImageView imgBarrageDie1;
    private ImageView imgBarrageDie2;
    private ImageView imgBarrageDie3;
	private Button btnBarrageDiceRoll;
	
    private Dice dice;
	private PlayAudio audio;
    private Terrain terrain;
    private Barrage barrage;
    private int type;
    private int res;
    private int strength;
    private TerrainEffect terrainWithin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            final ViewParent parent = rootView.getParent();
            if (parent != null && parent instanceof ViewGroup)
                ((ViewGroup) parent).removeView(rootView);  
        }
        else {
		    dice = new Dice(5, 1, 6);
		    audio = new PlayAudio (getActivity());
            terrain = new Terrain();
            barrage = new Barrage(terrain);
        
            rootView = inflater.inflate(R.layout.barrage, container, false);
        
            spinBarrageType = (Spinner )rootView.findViewById(R.id.spinBarrageType );
            spinBarrageREs = (Spinner )rootView.findViewById(R.id.spinBarrageREs );
            spinBarrageStrength = (Spinner )rootView.findViewById(R.id.spinBarrageStrength );
            spinBarrageTerrain = (Spinner )rootView.findViewById(R.id.spinBarrageTerrain );
            checkBarrageAirCloseToBase = (CheckBox)rootView.findViewById(R.id.checkBarrageAirCloseToBase);
            checkBarrageSpotter = (CheckBox)rootView.findViewById(R.id.checkBarrageSpotter );
            checkBarrageHedgehog = (CheckBox)rootView.findViewById(R.id.checkBarrageHedgehog );
            checkBarrageStratMove = (CheckBox)rootView.findViewById(R.id.checkBarrageStratMove );
            txtBarrageResults = (TextView)rootView.findViewById(R.id.txtBarrageResults );
            
            imgBarrageDie1 = (ImageView  )rootView.findViewById(R.id.imgBarrageDie1);
            imgBarrageDie2 = (ImageView  )rootView.findViewById(R.id.imgBarrageDie2);
            imgBarrageDie3 = (ImageView  )rootView.findViewById(R.id.imgBarrageDie3);
            btnBarrageDiceRoll = (Button)rootView.findViewById(R.id.btnBarrageDiceRoll);
            
            txtBarrageResults  = (TextView   )rootView.findViewById(R.id.txtBarrageResults );
            
		    ArrayAdapter<String> typeadapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, barrage.getTypes());
		    typeadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinBarrageType.setAdapter(typeadapter);
            
		    ArrayAdapter<String> resadapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, barrage.getDensities());
		    resadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinBarrageREs.setAdapter(resadapter); 
            
		    ArrayAdapter<String> stradapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, barrage.getStrengths(0));
		    stradapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinBarrageStrength.setAdapter(stradapter); 
            
		    ArrayAdapter<String> terrainadapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, terrain.getList(false));
		    terrainadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinBarrageTerrain.setAdapter(terrainadapter);
    
            checkBarrageAirCloseToBase.setChecked(false);
            checkBarrageSpotter.setChecked(true); 
            checkBarrageHedgehog.setChecked(false); 
            checkBarrageStratMove.setChecked(false); 
            
            // event handlers
		    spinBarrageType.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    type = pos;
                    loadStrengths();
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
		
		    spinBarrageREs.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    res = pos;
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
        
		    spinBarrageStrength.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    strength = pos;
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
        
		    spinBarrageTerrain.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    terrainWithin = terrain.get(pos);
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
        
            checkBarrageAirCloseToBase.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            checkBarrageSpotter.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            checkBarrageHedgehog.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            checkBarrageStratMove.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            
            imgBarrageDie1.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(0);
			        displayDice();
                    updateResults();
			    }
		    });
            imgBarrageDie2.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(1);
			        displayDice();
                    updateResults();
			    }
		    });
            imgBarrageDie3.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(2);
			        displayDice();
                    updateResults();
			    }
		    });

		    btnBarrageDiceRoll.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        audio.play();
			        dice.roll();
			        displayDice();
			        updateResults();
			    }
		    });
		
            txtBarrageResults.setText("", TextView.BufferType.NORMAL);
		    displayDice();
        }
        return rootView;
    }            
    
	void updateResults() {
        int barragedice = dice.getDie(0) + dice.getDie(1);
        int die = dice.getDie(2);
				
        String result = barrage.resolve(barragedice, die, type, strength, getAirCloseToBase(), getSpotter(), res, getTerrainWithin(), getStratMoveMode(), getHedgehog());
						
        txtBarrageResults.setText(result, TextView.BufferType.NORMAL);
	}

	void displayDice() {
        dice.set(0, DieColor.WHITE_BLACK, imgBarrageDie1);
		dice.set(1, DieColor.RED_WHITE,   imgBarrageDie2);
        dice.set(2, DieColor.YELLOW_BLACK, imgBarrageDie3);
	}
    
    private void loadStrengths() {
        ArrayAdapter<String> stradapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, barrage.getStrengths(type));
        stradapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinBarrageStrength.setAdapter(stradapter); 
    }        
    
    boolean getAirCloseToBase() {
        return checkBarrageAirCloseToBase.isChecked();
    }
    boolean getSpotter() {
        return checkBarrageSpotter.isChecked();
    }
    boolean getStratMoveMode() {
        return checkBarrageHedgehog.isChecked();
    }
    boolean getHedgehog() {
        return checkBarrageStratMove.isChecked();
    }
    
    String getTerrainWithin() {
        if (terrainWithin != null)
            return terrainWithin.getDescription();
        return "";
    }
}