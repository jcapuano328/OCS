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

public class SupplyAttritionFragment extends SupplyBaseTabFragment {

    private Spinner spinSupplyAttritionAR;
    private Spinner spinSupplyAttritionSteps;

    private TextView txtSupplyAttritionResults;
    
    private ImageView imgSupplyAttritionDie1;
    private ImageView imgSupplyAttritionDie2;
	private Button btnSupplyAttritionDiceRoll;
    
    private int ars = 0;
    private int steps = 0;
    
    public SupplyAttritionFragment(Activity activity, View rootView) {
        super(activity, rootView);
    
		dice = new Dice(2, 1, 6);
    }
	
    @Override
    public void create() {
        
        spinSupplyAttritionAR = (Spinner)rootView.findViewById(R.id.spinSupplyAttritionAR);
        spinSupplyAttritionSteps = (Spinner)rootView.findViewById(R.id.spinSupplyAttritionSteps);
        
        txtSupplyAttritionResults  = (TextView)rootView.findViewById(R.id.txtSupplyAttritionResults);
    
        imgSupplyAttritionDie1 = (ImageView  )rootView.findViewById(R.id.imgSupplyAttritionDie1);
        imgSupplyAttritionDie2 = (ImageView  )rootView.findViewById(R.id.imgSupplyAttritionDie2);
        btnSupplyAttritionDiceRoll = (Button)rootView.findViewById(R.id.btnSupplyAttritionDiceRoll);
    
        ArrayAdapter<String> aradapter = new ArrayAdapter<String> (activity, android.R.layout.simple_spinner_dropdown_item, supply.getAttritionARs());
        aradapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinSupplyAttritionAR.setAdapter(aradapter);
        
        ArrayAdapter<String> stepadapter = new ArrayAdapter<String> (activity, android.R.layout.simple_spinner_dropdown_item, supply.getAttritionDensities());
        stepadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinSupplyAttritionSteps.setAdapter(stepadapter);

        spinSupplyAttritionAR.setSelection(1);

        // event handlers
		spinSupplyAttritionAR.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ars = pos;
                updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
        
		spinSupplyAttritionSteps.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                steps = pos;
                updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
    
        imgSupplyAttritionDie1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(0);
			    displayDice();
                updateResults();
			}
		});
        imgSupplyAttritionDie2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(1);
			    displayDice();
                updateResults();
			}
		});

		btnSupplyAttritionDiceRoll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    audio.play();
			    dice.roll();
			    displayDice();
			    updateResults();
			}
		});
	
        txtSupplyAttritionResults.setText("", TextView.BufferType.NORMAL);
		displayDice();
    }            
    
	void updateResults() {
        int supplydice = dice.getDie(0) + dice.getDie(1);
				
        supply.attrition(supplydice, ars, steps);
        txtSupplyAttritionResults.setText(supply.getResults(), TextView.BufferType.NORMAL);
	}

	void displayDice() {
        dice.set(0, DieColor.WHITE_BLACK, imgSupplyAttritionDie1);
		dice.set(1, DieColor.RED_WHITE,   imgSupplyAttritionDie2);
	}
}