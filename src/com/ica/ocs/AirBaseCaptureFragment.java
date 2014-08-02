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

public class AirBaseCaptureFragment extends BaseTabFragment {

    private Spinner spinAirBaseCaptureState;

    private CheckBox checkAirBaseCaptureAdvance;
	
    private TextView txtAirBaseCaptureResults;
    
    private ImageView imgAirBaseCaptureDie1;
    private ImageView imgAirBaseCaptureDie2;
	private Button btnAirBaseCaptureDiceRoll;
    
    private int state = 0;
    
    public AirBaseCaptureFragment(Activity activity, View rootView) {
        super(activity, rootView);
    
		dice = new Dice(2, 1, 6);
    }
	
    @Override
    public void create() {
        
        spinAirBaseCaptureState = (Spinner)rootView.findViewById(R.id.spinAirBaseCaptureState);
        checkAirBaseCaptureAdvance = (CheckBox)rootView.findViewById(R.id.checkAirBaseCaptureAdvance);
    
        txtAirBaseCaptureResults  = (TextView)rootView.findViewById(R.id.txtAirBaseCaptureResults );
    
        imgAirBaseCaptureDie1 = (ImageView  )rootView.findViewById(R.id.imgAirBaseCaptureDie1);
        imgAirBaseCaptureDie2 = (ImageView  )rootView.findViewById(R.id.imgAirBaseCaptureDie2);
        btnAirBaseCaptureDiceRoll = (Button)rootView.findViewById(R.id.btnAirBaseCaptureDiceRoll);
    
        ArrayAdapter<String> stateadapter = new ArrayAdapter<String> (activity, android.R.layout.simple_spinner_dropdown_item, air.getReductionStates());
        stateadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinAirBaseCaptureState.setAdapter(stateadapter);
        
        // event handlers
        spinAirBaseCaptureState.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                state = pos;
                updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
        
        checkAirBaseCaptureAdvance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                updateResults();
			}
		});        
    
        imgAirBaseCaptureDie1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(0);
			    displayDice();
                updateResults();
			}
		});
        imgAirBaseCaptureDie2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(1);
			    displayDice();
                updateResults();
			}
		});

		btnAirBaseCaptureDiceRoll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    audio.play();
			    dice.roll();
			    displayDice();
			    updateResults();
			}
		});
	
        txtAirBaseCaptureResults.setText("", TextView.BufferType.NORMAL);
		displayDice();
    }            
    
	void updateResults() {
        int airdice = dice.getDie(0) + dice.getDie(1);
				
        String result = air.baseCapture(airdice, state, getAdvanceAfterCombat());
        txtAirBaseCaptureResults.setText(result, TextView.BufferType.NORMAL);
	}

	void displayDice() {
        dice.set(0, DieColor.WHITE_BLACK, imgAirBaseCaptureDie1);
		dice.set(1, DieColor.RED_WHITE,   imgAirBaseCaptureDie2);
	}

    boolean getAdvanceAfterCombat() {
        return checkAirBaseCaptureAdvance.isChecked();
    }
}