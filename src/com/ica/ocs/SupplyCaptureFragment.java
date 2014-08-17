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

public class SupplyCaptureFragment extends SupplyBaseTabFragment {

    private RadioButton radioSupplyCaptureDump;
    private RadioButton radioSupplyCaptureTrucks;
    private RadioButton radioSupplyCaptureWagons;
    
    private EditText edtSupplyCaptureStartUnit;
    private Button btnSupplyCaptureStartUnitPrev;
    private Button btnSupplyCaptureStartUnitNext;
    
    private EditText edtSupplyCaptureStartSupply;
    private Button btnSupplyCaptureStartSupplyPrev;
    private Button btnSupplyCaptureStartSupplyNext;
    
    private EditText edtSupplyCaptureStartTokens;
    private Button btnSupplyCaptureStartTokensPrev;
    private Button btnSupplyCaptureStartTokensNext;
            
    private TextView txtSupplyCaptureCapturedUnit;
    private TextView txtSupplyCaptureCapturedSupply;
    private TextView txtSupplyCaptureCapturedTokens;
            
    private TextView txtSupplyCaptureRemainingUnit;
    private TextView txtSupplyCaptureRemainingSupply;
    private TextView txtSupplyCaptureRemainingTokens;

    private TextView txtSupplyCaptureResults;
    
    private ImageView imgSupplyCaptureDie1;
	private Button btnSupplyCaptureDiceRoll;
    
    public SupplyCaptureFragment(Activity activity, View rootView) {
        super(activity, rootView);
    
		dice = new Dice(1, 1, 6);
    }
	
    @Override
    public void create() {
        
        radioSupplyCaptureDump  = (RadioButton)rootView.findViewById(R.id.radioSupplyCaptureDump);
        radioSupplyCaptureTrucks  = (RadioButton)rootView.findViewById(R.id.radioSupplyCaptureTrucks);
        radioSupplyCaptureWagons  = (RadioButton)rootView.findViewById(R.id.radioSupplyCaptureWagons);

        edtSupplyCaptureStartUnit  = (EditText)rootView.findViewById(R.id.edtSupplyCaptureStartUnit);
        btnSupplyCaptureStartUnitPrev = (Button)rootView.findViewById(R.id.btnSupplyCaptureStartUnitPrev);
        btnSupplyCaptureStartUnitNext = (Button)rootView.findViewById(R.id.btnSupplyCaptureStartUnitNext);
        
        edtSupplyCaptureStartSupply  = (EditText)rootView.findViewById(R.id.edtSupplyCaptureStartSupply);
        btnSupplyCaptureStartSupplyPrev = (Button)rootView.findViewById(R.id.btnSupplyCaptureStartSupplyPrev);
        btnSupplyCaptureStartSupplyNext = (Button)rootView.findViewById(R.id.btnSupplyCaptureStartSupplyNext);
        
        edtSupplyCaptureStartTokens  = (EditText)rootView.findViewById(R.id.edtSupplyCaptureStartTokens);
        btnSupplyCaptureStartTokensPrev = (Button)rootView.findViewById(R.id.btnSupplyCaptureStartTokensPrev);
        btnSupplyCaptureStartTokensNext = (Button)rootView.findViewById(R.id.btnSupplyCaptureStartTokensNext);

        txtSupplyCaptureCapturedUnit  = (TextView)rootView.findViewById(R.id.txtSupplyCaptureCapturedUnit);
        txtSupplyCaptureCapturedSupply  = (TextView)rootView.findViewById(R.id.txtSupplyCaptureCapturedSupply);
        txtSupplyCaptureCapturedTokens  = (TextView)rootView.findViewById(R.id.txtSupplyCaptureCapturedTokens);

        txtSupplyCaptureRemainingUnit  = (TextView)rootView.findViewById(R.id.txtSupplyCaptureRemainingUnit);
        txtSupplyCaptureRemainingSupply  = (TextView)rootView.findViewById(R.id.txtSupplyCaptureRemainingSupply);
        txtSupplyCaptureRemainingTokens  = (TextView)rootView.findViewById(R.id.txtSupplyCaptureRemainingTokens);
        
        txtSupplyCaptureResults  = (TextView)rootView.findViewById(R.id.txtSupplyCaptureResults);
    
        imgSupplyCaptureDie1 = (ImageView  )rootView.findViewById(R.id.imgSupplyCaptureDie1);
        btnSupplyCaptureDiceRoll = (Button)rootView.findViewById(R.id.btnSupplyCaptureDiceRoll);

        edtSupplyCaptureStartUnit.setText("0");
        edtSupplyCaptureStartSupply.setText("0");
        edtSupplyCaptureStartTokens.setText("0");

        radioSupplyCaptureDump.setChecked(true);

        // event handlers
        radioSupplyCaptureDump.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                radioSupplyCaptureTrucks.setChecked(false);
                radioSupplyCaptureWagons.setChecked(false);
                updateResults();
			}
		});        
        radioSupplyCaptureTrucks.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                radioSupplyCaptureDump.setChecked(false);
                radioSupplyCaptureWagons.setChecked(false);
                updateResults();
			}
		});        
        radioSupplyCaptureWagons.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                radioSupplyCaptureDump.setChecked(false);
                radioSupplyCaptureTrucks.setChecked(false);
                updateResults();
			}
		});        
        
		edtSupplyCaptureStartUnit.addTextChangedListener(new TextWatcher() {
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
		btnSupplyCaptureStartUnitPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getUnit();
			    if (--value < 0) value = 0;
			    edtSupplyCaptureStartUnit.setText(Integer.toString(value));
			    //updateResults();
            
			}
		});        
		btnSupplyCaptureStartUnitNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getUnit();
                edtSupplyCaptureStartUnit.setText(Integer.toString(++value));
			    //updateResults();
			}
		});        
        
		edtSupplyCaptureStartSupply.addTextChangedListener(new TextWatcher() {
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
		btnSupplyCaptureStartSupplyPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getSupply();
			    if (--value < 0) value = 0;
			    edtSupplyCaptureStartSupply.setText(Integer.toString(value));
			    //updateResults();
            
			}
		});        
		btnSupplyCaptureStartSupplyNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getSupply();
			    edtSupplyCaptureStartSupply.setText(Integer.toString(++value));
			    //updateResults();
			}
		});        
        
		edtSupplyCaptureStartTokens.addTextChangedListener(new TextWatcher() {
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
		btnSupplyCaptureStartTokensPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getTokens();
			    if (--value < 0) value = 0;
			    edtSupplyCaptureStartTokens.setText(Integer.toString(value));
			    //updateResults();
            
			}
		});        
		btnSupplyCaptureStartTokensNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    int value = getTokens();
                if (++value > 3) value = 3;
			    edtSupplyCaptureStartTokens.setText(Integer.toString(value));
			    //updateResults();
			}
		});        
    
    
        imgSupplyCaptureDie1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                dice.increment(0);
			    displayDice();
                updateResults();
			}
		});

		btnSupplyCaptureDiceRoll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    audio.play();
			    dice.roll();
			    displayDice();
			    updateResults();
			}
		});
	
        txtSupplyCaptureResults.setText("", TextView.BufferType.NORMAL);
		displayDice();
    }            
    
	void updateResults() {
        int die = dice.getDie(0);
				
        supply.capture(die, getDump(), getTrucks(), getWagons(), getUnit(), getSupply(), getTokens());
        
        txtSupplyCaptureResults.setText(supply.getResults(), TextView.BufferType.NORMAL);
        
        txtSupplyCaptureCapturedUnit.setText(supply.getCapturedUnits(), TextView.BufferType.NORMAL);
        txtSupplyCaptureCapturedSupply.setText(supply.getCapturedSupply(), TextView.BufferType.NORMAL);
        txtSupplyCaptureCapturedTokens.setText(supply.getCapturedTokens(), TextView.BufferType.NORMAL);
        
        txtSupplyCaptureRemainingUnit.setText(supply.getRemainingUnits(), TextView.BufferType.NORMAL);
        txtSupplyCaptureRemainingSupply.setText(supply.getRemainingSupply(), TextView.BufferType.NORMAL);
        txtSupplyCaptureRemainingTokens.setText(supply.getRemainingTokens(), TextView.BufferType.NORMAL);
	}

	void displayDice() {
        dice.set(0, DieColor.BLUE_WHITE, imgSupplyCaptureDie1);
	}
    
	int getUnit() {
        String v = edtSupplyCaptureStartUnit.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
	int getSupply() {
        String v = edtSupplyCaptureStartSupply.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
	int getTokens() {
        String v = edtSupplyCaptureStartTokens.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
     
    boolean getDump() {
        return radioSupplyCaptureDump.isChecked();
    }
    boolean getTrucks() {
        return radioSupplyCaptureTrucks.isChecked();
    }
    boolean getWagons() {
        return radioSupplyCaptureWagons.isChecked();
    }
    
}