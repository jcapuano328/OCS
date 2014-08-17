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

public class AirFlakFragment extends AirBaseTabFragment {

	private Button btnAirFlakSizePrev;
	private Button btnAirFlakSizeNext;
	private EditText editAirFlakSize;

    private Spinner spinAirFlakBase;

	private Button btnAirFlakShipPrev;
	private Button btnAirFlakShipNext;
	private EditText editAirFlakShip;
    
    private CheckBox checkAirFlakHQ;
            
    private Spinner spinAirFlakPatrolZone;
            
    private CheckBox checkAirFlakTrainbusting;
	
    private TextView txtAirFlakResults;
    
    private ImageView imgAirFlakDie1;
    private ImageView imgAirFlakDie2;
    private ImageView imgAirFlakDie3;
	private Button btnAirFlakDiceRoll;
    
    private int airbase = 0;
    private int patrolzone = 0;
    
    public AirFlakFragment(Activity activity, View rootView) {
        super(activity, rootView);
    
		dice = new Dice(3, 1, 6);
    }
	
    @Override
    public void create() {
        
		btnAirFlakSizePrev = (Button)rootView.findViewById(R.id.btnAirFlakSizePrev);
		btnAirFlakSizeNext = (Button)rootView.findViewById(R.id.btnAirFlakSizeNext);
		editAirFlakSize = (EditText)rootView.findViewById(R.id.editAirFlakSize);
        
        spinAirFlakBase = (Spinner )rootView.findViewById(R.id.spinAirFlakBase);
        
		btnAirFlakShipPrev = (Button)rootView.findViewById(R.id.btnAirFlakShipPrev);
		btnAirFlakShipNext = (Button)rootView.findViewById(R.id.btnAirFlakShipNext);
		editAirFlakShip = (EditText)rootView.findViewById(R.id.editAirFlakShip);
	
        checkAirFlakHQ = (CheckBox)rootView.findViewById(R.id.checkAirFlakHQ);
        spinAirFlakPatrolZone = (Spinner )rootView.findViewById(R.id.spinAirFlakPatrolZone);
        checkAirFlakTrainbusting = (CheckBox)rootView.findViewById(R.id.checkAirFlakTrainbusting);
    
        txtAirFlakResults  = (TextView)rootView.findViewById(R.id.txtAirFlakResults );
    
        imgAirFlakDie1 = (ImageView  )rootView.findViewById(R.id.imgAirFlakDie1);
        imgAirFlakDie2 = (ImageView  )rootView.findViewById(R.id.imgAirFlakDie2);
        imgAirFlakDie3 = (ImageView  )rootView.findViewById(R.id.imgAirFlakDie3);
        btnAirFlakDiceRoll = (Button)rootView.findViewById(R.id.btnAirFlakDiceRoll);

        editAirFlakSize.setText("1");
		editAirFlakShip.setText("0");
    
        ArrayAdapter<String> baseadapter = new ArrayAdapter<String> (activity, android.R.layout.simple_spinner_dropdown_item, air.getAirBaseLevels());
        baseadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinAirFlakBase.setAdapter(baseadapter);
        
        ArrayAdapter<String> pzadapter = new ArrayAdapter<String> (activity, android.R.layout.simple_spinner_dropdown_item, air.getPatrolZones());
        pzadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinAirFlakPatrolZone.setAdapter(pzadapter);
        
        // event handlers
		btnAirFlakSizePrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getSize();
			    if (--value < 0) value = 0;
			    editAirFlakSize.setText(Integer.toString(value));
			    //updateResults();
			}
		});
		btnAirFlakSizeNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getSize();
			    editAirFlakSize.setText(Integer.toString(++value));
			    //updateResults();
			}
		});
		editAirFlakSize.addTextChangedListener(new TextWatcher() {
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
			@Override
            public void onTextChanged(CharSequence s, int start, int before, int end) {
            }
			@Override
            public void afterTextChanged(Editable s) {
			    updateResults();
            }
        });
        
		spinAirFlakBase.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                airbase = pos;
                updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
        
		btnAirFlakShipPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getShip();
			    if (--value < 0) value = 0;
			    editAirFlakShip.setText(Integer.toString(value));
			    //updateResults();
			}
		});
		btnAirFlakShipNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getShip();
			    editAirFlakShip.setText(Integer.toString(++value));
			    //updateResults();
			}
		});
		editAirFlakShip.addTextChangedListener(new TextWatcher() {
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
			@Override
            public void onTextChanged(CharSequence s, int start, int before, int end) {
            }
			@Override
            public void afterTextChanged(Editable s) {
			    updateResults();
            }
        });
    
		spinAirFlakPatrolZone.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                patrolzone = pos;
                updateResults();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });            
    
        checkAirFlakTrainbusting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                updateResults();
			}
		});        
    
        imgAirFlakDie1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(0);
			    displayDice();
                updateResults();
			}
		});
        imgAirFlakDie2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(1);
			    displayDice();
                updateResults();
			}
		});
        imgAirFlakDie3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dice.increment(2);
                displayDice();
                updateResults();
            }
        });

		btnAirFlakDiceRoll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    audio.play();
			    dice.roll();
			    displayDice();
			    updateResults();
			}
		});
	
        txtAirFlakResults.setText("", TextView.BufferType.NORMAL);
		displayDice();
    }            
    
	void updateResults() {
        int flakdice = dice.getDie(0) + dice.getDie(1);
        int lossdie = dice.getDie(2);
				
        String result = air.flak(flakdice, lossdie, getSize(), airbase, getShip(), getManyAircraft(), getHQ(), patrolzone, getTrainbusting());
        txtAirFlakResults.setText(result, TextView.BufferType.NORMAL);
	}

	void displayDice() {
        dice.set(0, DieColor.WHITE_BLACK, imgAirFlakDie1);
		dice.set(1, DieColor.RED_WHITE,   imgAirFlakDie2);
	}

	int getSize() {
        String v = editAirFlakSize.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
    
	int getShip() {
        String v = editAirFlakShip.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
    
    boolean getHQ() {
        return checkAirFlakHQ.isChecked();
    }
    boolean getManyAircraft() {
        return getSize() >= 3;
    }
    boolean getTrainbusting() {
        return checkAirFlakTrainbusting.isChecked();
    }
    
}