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

public class SupplyDestructionFragment extends SupplyBaseTabFragment {

    private EditText edtSupplyDestructionStartSupply;
    private EditText edtSupplyDestructionStartTokens;
            
    private TextView txtSupplyDestructionRemainingSupply;
    private TextView txtSupplyDestructionRemainingTokens;

    private TextView txtSupplyDestructionResults;
    
    private ImageView imgSupplyDestructionDie1;
	private Button btnSupplyDestructionDiceRoll;
    
    public SupplyDestructionFragment(Activity activity, View rootView) {
        super(activity, rootView);
    
		dice = new Dice(1, 1, 6);
    }
	
    @Override
    public void create() {
        
        edtSupplyDestructionStartSupply  = (EditText)rootView.findViewById(R.id.edtSupplyDestructionStartSupply);
        edtSupplyDestructionStartTokens  = (EditText)rootView.findViewById(R.id.edtSupplyDestructionStartTokens);

        txtSupplyDestructionRemainingSupply  = (TextView)rootView.findViewById(R.id.txtSupplyDestructionRemainingSupply);
        txtSupplyDestructionRemainingTokens  = (TextView)rootView.findViewById(R.id.txtSupplyDestructionRemainingTokens);
        
        txtSupplyDestructionResults  = (TextView)rootView.findViewById(R.id.txtSupplyDestructionResults);
    
        imgSupplyDestructionDie1 = (ImageView  )rootView.findViewById(R.id.imgSupplyDestructionDie1);
        btnSupplyDestructionDiceRoll = (Button)rootView.findViewById(R.id.btnSupplyDestructionDiceRoll);
    
        // event handlers
		edtSupplyDestructionStartSupply.addTextChangedListener(new TextWatcher() {
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
		edtSupplyDestructionStartTokens.addTextChangedListener(new TextWatcher() {
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
    
        imgSupplyDestructionDie1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(0);
			    displayDice();
                updateResults();
			}
		});

		btnSupplyDestructionDiceRoll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    audio.play();
			    dice.roll();
			    displayDice();
			    updateResults();
			}
		});
	
        txtSupplyDestructionResults.setText("", TextView.BufferType.NORMAL);
		displayDice();
    }            
    
	void updateResults() {
        int die = dice.getDie(0);
				
        supply.destruction(die, getSupply(), getTokens());
        
        txtSupplyDestructionResults.setText(supply.getResults(), TextView.BufferType.NORMAL);
        
        txtSupplyDestructionRemainingSupply.setText(supply.getRemainingSupply(), TextView.BufferType.NORMAL);
        txtSupplyDestructionRemainingTokens.setText(supply.getRemainingTokens(), TextView.BufferType.NORMAL);
	}

	void displayDice() {
        dice.set(0, DieColor.GREEN_WHITE, imgSupplyDestructionDie1);
	}
    
	int getSupply() {
        String v = edtSupplyDestructionStartSupply.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
	int getTokens() {
        String v = edtSupplyDestructionStartTokens.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
}