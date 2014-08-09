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


public class GroundFragment extends Fragment {

    private View rootView;
    
    private EditText edtGroundAttackArmor;
    private EditText edtGroundDefendArmor;
    private Spinner spinGroundTerrain; 

    private EditText edtGroundAttackMech;
    private EditText edtGroundDefendMech;
    private Spinner spinGroundTerrainBtween; 

    private EditText edtGroundAttackOther;
    private EditText edtGroundDefendOther;

    private Spinner spinGroundAttackAR; 
    private Spinner spinGroundDefendAR; 

    private TextView txtGroundOdds; 

    private CheckBox checkGroundAttackCombat; 
    private CheckBox checkGroundDefendCombat; 
    private CheckBox checkGroundAttackTrace; 
    private CheckBox checkGroundDefendTrace; 

    private Spinner spinGroundDefendHedgehog; 

    private RadioButton radioGroundRegular; 
    private RadioButton radioGroundOverrun; 
    
    private ImageView imgGroundDie1;
    private ImageView imgGroundDie2;
    private ImageView imgGroundDie3;
    private ImageView imgGroundDie4;
    private ImageView imgGroundDie5;

    private TextView txtGroundSurpise; 
    private TextView txtGroundResults; 
    
	private Button btnGroundDiceRoll;
	
    private Dice dice;
	private PlayAudio audio;
    private Terrain terrain;
    private Ground ground;
    private String[] terrainWithinList;
    private String[] terrainBetweenList;
    private TerrainEffect terrainWithin;
    private TerrainEffect terrainBetween;
    private int attackAR = 1;
    private int defendAR = 1;
    private int hedgehog;

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
            ground = new Ground(terrain);
            terrainWithinList = terrain.getList(false);
            terrainBetweenList = terrain.getList(true);

            rootView = inflater.inflate(R.layout.ground, container, false);
        
            edtGroundAttackArmor = (EditText   )rootView.findViewById(R.id.edtGroundAttackArmor);
            edtGroundDefendArmor = (EditText   )rootView.findViewById(R.id.edtGroundDefendArmor);
            spinGroundTerrain  = (Spinner    )rootView.findViewById(R.id.spinGroundTerrain );
            
            edtGroundAttackMech = (EditText   )rootView.findViewById(R.id.edtGroundAttackMech);
            edtGroundDefendMech = (EditText   )rootView.findViewById(R.id.edtGroundDefendMech);
            spinGroundTerrainBtween  = (Spinner    )rootView.findViewById(R.id.spinGroundTerrainBtween );
            
            edtGroundAttackOther = (EditText   )rootView.findViewById(R.id.edtGroundAttackOther);
            edtGroundDefendOther = (EditText   )rootView.findViewById(R.id.edtGroundDefendOther);
            
            spinGroundAttackAR  = (Spinner    )rootView.findViewById(R.id.spinGroundAttackAR );
            spinGroundDefendAR  = (Spinner    )rootView.findViewById(R.id.spinGroundDefendAR );
            
            txtGroundOdds  = (TextView)rootView.findViewById(R.id.txtGroundOdds );
            
            checkGroundAttackCombat  = (CheckBox   )rootView.findViewById(R.id.checkGroundAttackCombat );
            checkGroundDefendCombat  = (CheckBox   )rootView.findViewById(R.id.checkGroundDefendCombat );
            checkGroundAttackTrace  = (CheckBox   )rootView.findViewById(R.id.checkGroundAttackTrace );
            checkGroundDefendTrace  = (CheckBox   )rootView.findViewById(R.id.checkGroundDefendTrace );
            
            spinGroundDefendHedgehog = (Spinner    )rootView.findViewById(R.id.spinGroundDefendHedgehog);
            
            radioGroundRegular  = (RadioButton)rootView.findViewById(R.id.radioGroundRegular );
            radioGroundOverrun  = (RadioButton)rootView.findViewById(R.id.radioGroundOverrun );
            
            imgGroundDie1 = (ImageView  )rootView.findViewById(R.id.imgGroundDie1);
            imgGroundDie2 = (ImageView  )rootView.findViewById(R.id.imgGroundDie2);
            imgGroundDie3 = (ImageView  )rootView.findViewById(R.id.imgGroundDie3);
            imgGroundDie4 = (ImageView  )rootView.findViewById(R.id.imgGroundDie4);
            imgGroundDie5 = (ImageView  )rootView.findViewById(R.id.imgGroundDie5);
            btnGroundDiceRoll = (Button)rootView.findViewById(R.id.btnGroundDiceRoll);
            
            txtGroundSurpise  = (TextView   )rootView.findViewById(R.id.txtGroundSurpise );
            txtGroundResults  = (TextView   )rootView.findViewById(R.id.txtGroundResults );
             
            edtGroundAttackArmor.setText("0");
            edtGroundDefendArmor.setText("0");

            ArrayAdapter<String> terrainadapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, terrainWithinList);
		    terrainadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinGroundTerrain.setAdapter(terrainadapter);

            edtGroundAttackMech.setText("0");
            edtGroundDefendMech.setText("0");
		    ArrayAdapter<String> terrainadapter2 = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, terrainBetweenList);
		    terrainadapter2.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinGroundTerrainBtween.setAdapter(terrainadapter2);
    
            edtGroundAttackOther.setText("0");
            edtGroundDefendOther.setText("0");

		    ArrayAdapter<String> aradapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, ground.getUnitAR());
		    aradapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinGroundAttackAR.setAdapter(aradapter); 
            spinGroundDefendAR.setAdapter(aradapter); 
            
		    spinGroundAttackAR.setSelection(1);
		    spinGroundDefendAR.setSelection(1);

            checkGroundAttackCombat.setChecked(true);
            checkGroundDefendCombat.setChecked(true);
            checkGroundAttackTrace.setChecked(true);
            checkGroundDefendTrace.setChecked(true);

		    ArrayAdapter<String> hhadapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, ground.getHedgehog());
		    hhadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinGroundDefendHedgehog.setAdapter(hhadapter); 
            
            radioGroundRegular.setChecked(true); 
            radioGroundOverrun.setChecked(false); 
            
            // event handlers
		    edtGroundAttackArmor.addTextChangedListener(new TextWatcher() {
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
    
		    edtGroundDefendArmor.addTextChangedListener(new TextWatcher() {
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
    
		    spinGroundTerrain.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    terrainWithin = terrain.get(terrainWithinList[pos]);
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
		
		    edtGroundAttackMech.addTextChangedListener(new TextWatcher() {
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
    
		    edtGroundDefendMech.addTextChangedListener(new TextWatcher() {
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
        
		    spinGroundTerrainBtween.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    terrainBetween = terrain.get(terrainBetweenList[pos]);
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
        
		    edtGroundAttackOther.addTextChangedListener(new TextWatcher() {
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
    
		    edtGroundDefendOther.addTextChangedListener(new TextWatcher() {
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

        
		    spinGroundAttackAR.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    attackAR = pos;
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
        
		    spinGroundDefendAR.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    defendAR = pos;
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });            
        
            checkGroundAttackCombat.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            checkGroundDefendCombat.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            checkGroundAttackTrace.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            checkGroundDefendTrace.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    updateResults();
			    }
		    });        
            
            spinGroundDefendHedgehog.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    hedgehog = pos;
                    updateResults();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

            radioGroundRegular.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    radioGroundOverrun.setChecked(false);
                    updateResults();
			    }
		    });        
            radioGroundOverrun.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    radioGroundRegular.setChecked(false);
                    updateResults();
			    }
		    });        
    
            imgGroundDie1.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(0);
			        displayDice();
                    updateResults();
			    }
		    });
            imgGroundDie2.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(1);
			        displayDice();
                    updateResults();
			    }
		    });
            imgGroundDie3.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(2);
			        displayDice();
                    updateResults();
			    }
		    });
            imgGroundDie4.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(3);
			        displayDice();
                    updateResults();
			    }
		    });
            imgGroundDie5.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(4);
			        displayDice();
                    updateResults();
			    }
		    });

		    btnGroundDiceRoll.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        audio.play();
			        dice.roll();
			        displayDice();
			        updateResults();
			    }
		    });
		
            txtGroundSurpise.setText("", TextView.BufferType.NORMAL);
            txtGroundResults.setText("", TextView.BufferType.NORMAL);
		    displayDice();
        }
        return rootView;
    }            
    
	void calcOdds() {
        ground.calculate(getAttackerArmor(), getAttackerMech(), getAttackerOther(), getAttackerCombatSupply(), getAttackerTraceSupply(),
                         getDefenderArmor(), getDefenderMech(), getDefenderOther(), getDefenderCombatSupply(), getDefenderTraceSupply(),  
						getTerrainWithin(), getTerrainBetween());
        displayOdds();                        
	}
    
	void updateResults() {
        int combatdice = dice.getDie(0) + dice.getDie(1);
        int surprisedice = dice.getDie(3) + dice.getDie(4);
        int surprisedie = dice.getDie(2);
					
        ground.resolve(getAttackerArmor(), getAttackerMech(), getAttackerOther(), attackAR, getAttackerCombatSupply(), getAttackerTraceSupply(),
                        getDefenderArmor(), getDefenderMech(), getDefenderOther(), defendAR, getDefenderCombatSupply(), getDefenderTraceSupply(), hedgehog,
						getTerrainWithin(), getTerrainBetween(), getRegularCombat(), combatdice, surprisedice, surprisedie);
						
        displayOdds();
        displaySurprise();                        
        displayResults();                        
	}

    void displayOdds() {
        txtGroundOdds.setText(ground.getCombatOdds(), TextView.BufferType.NORMAL);
    }
    
    void displaySurprise() {
        txtGroundSurpise.setText(ground.getSurpriseResults(), TextView.BufferType.NORMAL);
    }
    
    void displayResults() {
        txtGroundResults.setText(ground.getCombatResults(), TextView.BufferType.NORMAL);
    }
    
	void displayDice() {
        dice.set(0, DieColor.WHITE_BLACK, imgGroundDie1);
		dice.set(1, DieColor.RED_WHITE,   imgGroundDie2);
        dice.set(2, DieColor.YELLOW_BLACK, imgGroundDie3);
        dice.set(3, DieColor.BLACK_RED,  imgGroundDie4);
		dice.set(4, DieColor.BLACK_WHITE, imgGroundDie5);
	}
    
    double getAttackerArmor() {
        String v = edtGroundAttackArmor.getText().toString();
        if (!v.isEmpty())
            return Double.parseDouble(v);
        return 1;           

    }
    double getAttackerMech() {
        String v = edtGroundAttackMech.getText().toString();
        if (!v.isEmpty())
            return Double.parseDouble(v);
        return 1;           

    }
    double getAttackerOther() {
        String v = edtGroundAttackOther.getText().toString();
        if (!v.isEmpty())
            return Double.parseDouble(v);
        return 1;           

    }
    boolean getAttackerCombatSupply() {
        return checkGroundAttackCombat.isChecked();
    }
    boolean getAttackerTraceSupply() {
        return checkGroundAttackTrace.isChecked();
    }
    
    double getDefenderArmor() {
        String v = edtGroundDefendArmor.getText().toString();
        if (!v.isEmpty())
            return Double.parseDouble(v);
        return 1;           

    }
    double getDefenderMech() {
        String v = edtGroundDefendMech.getText().toString();
        if (!v.isEmpty())
            return Double.parseDouble(v);
        return 1;           

    }
    double getDefenderOther() {
        String v = edtGroundDefendOther.getText().toString();
        if (!v.isEmpty())
            return Double.parseDouble(v);
        return 1;           

    }
    boolean getDefenderCombatSupply() {
        return checkGroundDefendCombat.isChecked();
    }
    boolean getDefenderTraceSupply() {
        return checkGroundDefendTrace.isChecked();
    }
    
    boolean getRegularCombat() {
        return radioGroundRegular.isChecked();
    }
    
    String getTerrainWithin() {
        if (terrainWithin != null)
            return terrainWithin.getDescription();
        return "";
    }
    
    String getTerrainBetween() {
        if (terrainBetween != null)
            return terrainBetween.getDescription();
        return "";
    }
}