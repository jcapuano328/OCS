package com.ica.ocs;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.ica.dice.*;

public class AirCombatFragment extends AirBaseTabFragment {

	private Button btnAirCombatAttackerPrev;
	private Button btnAirCombatAttackerNext;
	private EditText editAirCombatAttacker;
	
	private Button btnAirCombatDefenderPrev;
	private Button btnAirCombatDefenderNext;
	private EditText editAirCombatDefender;

    private TextView txtAirCombatResults;
    
    private ImageView imgAirCombatDie1;
    private ImageView imgAirCombatDie2;
    private ImageView imgAirCombatDie3;
	private Button btnAirCombatDiceRoll;
    
    public AirCombatFragment(Activity activity, View rootView) {
        super(activity, rootView);
    
		dice = new Dice(3, 1, 6);
    }
	
    @Override
    public void create() {
        
		// attacker
		btnAirCombatAttackerPrev = (Button)rootView.findViewById(R.id.btnAirCombatAttackerPrev);
		btnAirCombatAttackerNext = (Button)rootView.findViewById(R.id.btnAirCombatAttackerNext);
		editAirCombatAttacker = (EditText)rootView.findViewById(R.id.editAirCombatAttacker);
		editAirCombatAttacker.setText("0");
	
		// defender
		btnAirCombatDefenderPrev = (Button)rootView.findViewById(R.id.btnAirCombatDefenderPrev);
		btnAirCombatDefenderNext = (Button)rootView.findViewById(R.id.btnAirCombatDefenderNext);
		editAirCombatDefender = (EditText)rootView.findViewById(R.id.editAirCombatDefender);
		editAirCombatDefender.setText("0");
    
        txtAirCombatResults  = (TextView)rootView.findViewById(R.id.txtAirCombatResults );
    
        imgAirCombatDie1 = (ImageView  )rootView.findViewById(R.id.imgAirCombatDie1);
        imgAirCombatDie2 = (ImageView  )rootView.findViewById(R.id.imgAirCombatDie2);
        imgAirCombatDie3 = (ImageView  )rootView.findViewById(R.id.imgAirCombatDie3);
        btnAirCombatDiceRoll = (Button)rootView.findViewById(R.id.btnAirCombatDiceRoll);
        
        // event handlers
		// attacker
		btnAirCombatAttackerPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getAttacker();
			    if (--value < 1) value = 1;
			    editAirCombatAttacker.setText(Integer.toString(value));
			    //updateResults();
			}
		});
		btnAirCombatAttackerNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getAttacker();
			    editAirCombatAttacker.setText(Integer.toString(++value));
			    //updateResults();
			}
		});
		editAirCombatAttacker.addTextChangedListener(new TextWatcher() {
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
    
		// defender
		btnAirCombatDefenderPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getDefender();
			    if (--value < 1) value = 1;
			    editAirCombatDefender.setText(Integer.toString(value));
			    //updateResults();
			}
		});
		btnAirCombatDefenderNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getDefender();
			    editAirCombatDefender.setText(Integer.toString(++value));
			    //updateResults();
			}
		});
		editAirCombatDefender.addTextChangedListener(new TextWatcher() {
			@Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
			@Override
            public void onTextChanged(CharSequence s, int start, int before, int end) {
            }
            public void afterTextChanged(Editable s) {
			    updateResults();
            }
        });
        
        
        imgAirCombatDie1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(0);
			    displayDice();
                updateResults();
			}
		});
        imgAirCombatDie2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(1);
			    displayDice();
                updateResults();
			}
		});
        imgAirCombatDie3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(2);
			    displayDice();
                updateResults();
			}
		});

		btnAirCombatDiceRoll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    audio.play();
			    dice.roll();
			    displayDice();
			    updateResults();
			}
		});
	
        txtAirCombatResults.setText("", TextView.BufferType.NORMAL);
		displayDice();
    }            
    
	void updateResults() {
        int combatdice = dice.getDie(0) + dice.getDie(1);
        int die = dice.getDie(2);
				
        String result = air.combat(combatdice, die, getAttacker(), getDefender());
						
        txtAirCombatResults.setText(result, TextView.BufferType.NORMAL);
	}

	void displayDice() {
        dice.set(0, DieColor.WHITE_BLACK, imgAirCombatDie1);
		dice.set(1, DieColor.RED_WHITE,   imgAirCombatDie2);
        dice.set(2, DieColor.YELLOW_BLACK, imgAirCombatDie3);
	}

	int getAttacker() {
        String v = editAirCombatAttacker.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
	int getDefender() {
        String v = editAirCombatDefender.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
    
}