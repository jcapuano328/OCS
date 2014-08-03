package com.ica.ocs;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import com.ica.dice.*;

public class AirTransportFragment extends AirBaseTabFragment {

    private Spinner spinAirTransportType;
    private Spinner spinAirTransportStatus;
    private Spinner spinAirTransportTerrain;

    private CheckBox checkAirTransportSoviet;
	
    private TextView txtAirTransportResults;
    
    private ImageView imgAirTransportDie1;
    private ImageView imgAirTransportDie2;
	private Button btnAirTransportDiceRoll;
    
    private int type = 0;
    private int status = 0;
    private int terrain = 0;
    
    public AirTransportFragment(Activity activity, View rootView) {
        super(activity, rootView);
    
		dice = new Dice(2, 1, 6);
    }
	
    @Override
    public void create() {
        
        spinAirTransportType = (Spinner)rootView.findViewById(R.id.spinAirTransportType);
        spinAirTransportStatus = (Spinner)rootView.findViewById(R.id.spinAirTransportStatus);
        spinAirTransportTerrain = (Spinner)rootView.findViewById(R.id.spinAirTransportTerrain);
        
        checkAirTransportSoviet = (CheckBox)rootView.findViewById(R.id.checkAirTransportSoviet);
    
        txtAirTransportResults  = (TextView)rootView.findViewById(R.id.txtAirTransportResults );
    
        imgAirTransportDie1 = (ImageView  )rootView.findViewById(R.id.imgAirTransportDie1);
        imgAirTransportDie2 = (ImageView  )rootView.findViewById(R.id.imgAirTransportDie2);
        btnAirTransportDiceRoll = (Button)rootView.findViewById(R.id.btnAirTransportDiceRoll);
    
        ArrayAdapter<String> typeadapter = new ArrayAdapter<String> (activity, android.R.layout.simple_spinner_dropdown_item, air.getTransportTypes());
        typeadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinAirTransportType.setAdapter(typeadapter);
        
        ArrayAdapter<String> statusadapter = new ArrayAdapter<String> (activity, android.R.layout.simple_spinner_dropdown_item, air.getTransportStatuses());
        statusadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinAirTransportStatus.setAdapter(statusadapter);
        
        ArrayAdapter<String> terrainadapter = new ArrayAdapter<String> (activity, android.R.layout.simple_spinner_dropdown_item, air.getTransportTerrains());
        terrainadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinAirTransportTerrain.setAdapter(terrainadapter);
        
        // event handlers
        spinAirTransportType.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                type = pos;
                updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
        spinAirTransportStatus.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                status = pos;
                updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
		spinAirTransportTerrain.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                terrain = pos;
                updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
        
        checkAirTransportSoviet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                updateResults();
			}
		});        
    
        imgAirTransportDie1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(0);
			    displayDice();
                updateResults();
			}
		});
        imgAirTransportDie2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(1);
			    displayDice();
                updateResults();
			}
		});

		btnAirTransportDiceRoll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    audio.play();
			    dice.roll();
			    displayDice();
			    updateResults();
			}
		});
	
        txtAirTransportResults.setText("", TextView.BufferType.NORMAL);
		displayDice();
    }            
    
	void updateResults() {
        int airdice = dice.getDie(0) + dice.getDie(1);
				
        String result = air.transport(airdice, type, status, terrain, getHandicap());
        txtAirTransportResults.setText(result, TextView.BufferType.NORMAL);
	}

	void displayDice() {
        dice.set(0, DieColor.WHITE_BLACK, imgAirTransportDie1);
		dice.set(1, DieColor.RED_WHITE,   imgAirTransportDie2);
	}

    boolean getHandicap() {
        return checkAirTransportSoviet.isChecked();
    }
}