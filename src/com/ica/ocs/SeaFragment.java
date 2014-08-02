package com.ica.ocs;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
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

import com.ica.ocs.Core.*;
import com.ica.dice.*;


public class SeaFragment extends Fragment {

    private View rootView;
    
    private Spinner spinSeaTerrain; 
    private CheckBox checkSeaNonDGUnitAdjacent; 
    private CheckBox checkSeaDGUnitAdjacent; 
    
	private Button btnSeaArtyPrev;
	private Button btnSeaArtyNext;
	private EditText editSeaArty;

    private TextView txtSeaResults;

    private ImageView imgSeaDie1;
    private ImageView imgSeaDie2;
	private Button btnSeaDiceRoll;
	
    private Dice dice;
	private PlayAudio audio;
    private Sea sea;
    private int terrain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            final ViewParent parent = rootView.getParent();
            if (parent != null && parent instanceof ViewGroup)
                ((ViewGroup) parent).removeView(rootView);  
        }
        else {
		    dice = new Dice(2, 1, 6);
		    audio = new PlayAudio (getActivity());
            sea = new Sea();
        
            rootView = inflater.inflate(R.layout.sea, container, false);

            spinSeaTerrain = (Spinner )rootView.findViewById(R.id.spinSeaTerrain );
            checkSeaNonDGUnitAdjacent = (CheckBox)rootView.findViewById(R.id.checkSeaNonDGUnitAdjacent);
            checkSeaDGUnitAdjacent = (CheckBox)rootView.findViewById(R.id.checkSeaDGUnitAdjacent);
            
            btnSeaArtyPrev = (Button)rootView.findViewById(R.id.btnSeaArtyPrev);
            btnSeaArtyNext = (Button)rootView.findViewById(R.id.btnSeaArtyNext);
            editSeaArty = (EditText)rootView.findViewById(R.id.editSeaArty);
            
            txtSeaResults = (TextView)rootView.findViewById(R.id.txtSeaResults);
            
            imgSeaDie1 = (ImageView  )rootView.findViewById(R.id.imgSeaDie1);
            imgSeaDie2 = (ImageView  )rootView.findViewById(R.id.imgSeaDie2);
            btnSeaDiceRoll = (Button)rootView.findViewById(R.id.btnSeaDiceRoll);
            
		    ArrayAdapter<String> terrainadapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, sea.getTerrains());
		    terrainadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinSeaTerrain.setAdapter(terrainadapter);
    
            editSeaArty.setText("0");
    
            // event handlers
		    spinSeaTerrain.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    terrain = pos;
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
        
            checkSeaNonDGUnitAdjacent.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            checkSeaDGUnitAdjacent.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            
            
		    btnSeaArtyPrev.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        int value = getArty();
			        if (--value < 0) value = 0;
			        editSeaArty.setText(Integer.toString(value));
			        //updateResults();
			    }
		    });
		    btnSeaArtyNext.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        int value = getArty();
			        editSeaArty.setText(Integer.toString(++value));
			        //updateResults();
			    }
		    });
		    editSeaArty.addTextChangedListener(new TextWatcher() {
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
            
            imgSeaDie1.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(0);
			        displayDice();
                    updateResults();
			    }
		    });
            imgSeaDie2.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(1);
			        displayDice();
                    updateResults();
			    }
		    });

		    btnSeaDiceRoll.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        audio.play();
			        dice.roll();
			        displayDice();
			        updateResults();
			    }
		    });
		
            txtSeaResults.setText("", TextView.BufferType.NORMAL);
		    displayDice();
        }
        return rootView;
    }            
    
	void updateResults() {
        int seadice = dice.getDie(0) + dice.getDie(1);
				
        String result = sea.landing(seadice, terrain, getNonDGUnitAdjacent(), getDGUnitAdjacent(), getArty());
        
        txtSeaResults.setText(result, TextView.BufferType.NORMAL);
	}

	void displayDice() {
        dice.set(0, DieColor.WHITE_BLACK, imgSeaDie1);
		dice.set(1, DieColor.RED_WHITE,   imgSeaDie2);
	}
 
    boolean getNonDGUnitAdjacent() {
        return checkSeaNonDGUnitAdjacent.isChecked();
    }
    boolean getDGUnitAdjacent() {
        return checkSeaDGUnitAdjacent.isChecked();
    }
   
	int getArty() {
        String v = editSeaArty.getText().toString();
        if (!v.isEmpty())
            return Integer.parseInt(v);
        return 1;           
	}
}