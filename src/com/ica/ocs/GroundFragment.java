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
    
    private Button btnGroundReset;
    
    private EditText edtGroundAttackArmor;
	private Button btnGroundAttackArmorPrev;
	private Button btnGroundAttackArmorNext;
    
    private EditText edtGroundDefendArmor;
	private Button btnGroundDefendArmorPrev;
	private Button btnGroundDefendArmorNext;
    
    private Spinner spinGroundTerrain; 

    private EditText edtGroundAttackMech;
	private Button btnGroundAttackMechPrev;
	private Button btnGroundAttackMechNext;
    
    private EditText edtGroundDefendMech;
	private Button btnGroundDefendMechPrev;
	private Button btnGroundDefendMechNext;
    
    private Spinner spinGroundTerrainBtween; 

    private EditText edtGroundAttackOther;
	private Button btnGroundAttackOtherPrev;
	private Button btnGroundAttackOtherNext;
    
    private EditText edtGroundDefendOther;
	private Button btnGroundDefendOtherPrev;
	private Button btnGroundDefendOtherNext;

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
        
            btnGroundReset = (Button)rootView.findViewById(R.id.btnGroundReset);
        
            edtGroundAttackArmor = (EditText   )rootView.findViewById(R.id.edtGroundAttackArmor);
            btnGroundAttackArmorPrev = (Button)rootView.findViewById(R.id.btnGroundAttackArmorPrev);
            btnGroundAttackArmorNext = (Button)rootView.findViewById(R.id.btnGroundAttackArmorNext);
            
            edtGroundDefendArmor = (EditText   )rootView.findViewById(R.id.edtGroundDefendArmor);
            btnGroundDefendArmorPrev = (Button)rootView.findViewById(R.id.btnGroundDefendArmorPrev);
            btnGroundDefendArmorNext = (Button)rootView.findViewById(R.id.btnGroundDefendArmorNext);

            spinGroundTerrain  = (Spinner    )rootView.findViewById(R.id.spinGroundTerrain );
            
            edtGroundAttackMech = (EditText   )rootView.findViewById(R.id.edtGroundAttackMech);
            btnGroundAttackMechPrev = (Button)rootView.findViewById(R.id.btnGroundAttackMechPrev);
            btnGroundAttackMechNext = (Button)rootView.findViewById(R.id.btnGroundAttackMechNext);

            edtGroundDefendMech = (EditText   )rootView.findViewById(R.id.edtGroundDefendMech);
            btnGroundDefendMechPrev = (Button)rootView.findViewById(R.id.btnGroundDefendMechPrev);
            btnGroundDefendMechNext = (Button)rootView.findViewById(R.id.btnGroundDefendMechNext);

            spinGroundTerrainBtween  = (Spinner    )rootView.findViewById(R.id.spinGroundTerrainBtween );
            
            edtGroundAttackOther = (EditText   )rootView.findViewById(R.id.edtGroundAttackOther);
            btnGroundAttackOtherPrev = (Button)rootView.findViewById(R.id.btnGroundAttackOtherPrev);
            btnGroundAttackOtherNext = (Button)rootView.findViewById(R.id.btnGroundAttackOtherNext);
             
            edtGroundDefendOther = (EditText   )rootView.findViewById(R.id.edtGroundDefendOther);
            btnGroundDefendOtherPrev = (Button)rootView.findViewById(R.id.btnGroundDefendOtherPrev);
            btnGroundDefendOtherNext = (Button)rootView.findViewById(R.id.btnGroundDefendOtherNext);
            
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
             
            ArrayAdapter<String> terrainadapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, terrainWithinList);
		    terrainadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinGroundTerrain.setAdapter(terrainadapter);

            
		    ArrayAdapter<String> terrainadapter2 = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, terrainBetweenList);
		    terrainadapter2.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinGroundTerrainBtween.setAdapter(terrainadapter2);
    
		    ArrayAdapter<String> aradapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, ground.getUnitAR());
		    aradapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinGroundAttackAR.setAdapter(aradapter); 
            spinGroundDefendAR.setAdapter(aradapter); 
            
		    ArrayAdapter<String> hhadapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, ground.getHedgehog());
		    hhadapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
            spinGroundDefendHedgehog.setAdapter(hhadapter); 
            
            
            // event handlers
		    btnGroundReset.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    reset();
			    }
		    });        
            
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
		    btnGroundAttackArmorPrev.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getAttackerArmor();
			        if (--value < 0) value = 0;
			        edtGroundAttackArmor.setText(Double.toString(value));
			        //updateResults();
                
			    }
		    });        
		    btnGroundAttackArmorNext.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getAttackerArmor();
			        edtGroundAttackArmor.setText(Double.toString(++value));
			        //updateResults();
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
		    btnGroundDefendArmorPrev.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getDefenderArmor();
			        if (--value < 0) value = 0;
			        edtGroundDefendArmor.setText(Double.toString(value));
			        //updateResults();
                
			    }
		    });        
		    btnGroundDefendArmorNext.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getDefenderArmor();
			        edtGroundDefendArmor.setText(Double.toString(++value));
			        //updateResults();
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
		    btnGroundAttackMechPrev.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getAttackerMech();
			        if (--value < 0) value = 0;
			        edtGroundAttackMech.setText(Double.toString(value));
			        //updateResults();
                
			    }
		    });        
		    btnGroundAttackMechNext.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getAttackerMech();
			        edtGroundAttackMech.setText(Double.toString(++value));
			        //updateResults();
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
		    btnGroundDefendMechPrev.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getDefenderMech();
			        if (--value < 0) value = 0;
			        edtGroundDefendMech.setText(Double.toString(value));
			        //updateResults();
                
			    }
		    });        
		    btnGroundDefendMechNext.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getDefenderMech();
			        edtGroundDefendMech.setText(Double.toString(++value));
			        //updateResults();
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
		    btnGroundAttackOtherPrev.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getAttackerOther();
			        if (--value < 0) value = 0;
			        edtGroundAttackOther.setText(Double.toString(value));
			        //updateResults();
                
			    }
		    });        
		    btnGroundAttackOtherNext.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getAttackerOther();
			        edtGroundAttackOther.setText(Double.toString(++value));
			        //updateResults();
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
		    btnGroundDefendOtherPrev.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getDefenderOther();
			        if (--value < 0) value = 0;
			        edtGroundDefendOther.setText(Double.toString(value));
			        //updateResults();
                
			    }
		    });        
		    btnGroundDefendOtherNext.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        double value = getDefenderOther();
			        edtGroundDefendOther.setText(Double.toString(++value));
			        //updateResults();
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
		
            reset();
            
		    displayDice();
        }
        return rootView;
    }            
    
    void reset() {
        edtGroundAttackArmor.setText("0");
        edtGroundDefendArmor.setText("0");
        edtGroundAttackMech.setText("0");
        edtGroundDefendMech.setText("0");
        edtGroundAttackOther.setText("0");
        edtGroundDefendOther.setText("0");
        
		spinGroundAttackAR.setSelection(1);
		spinGroundDefendAR.setSelection(1);

        checkGroundAttackCombat.setChecked(true);
        checkGroundDefendCombat.setChecked(true);
        checkGroundAttackTrace.setChecked(true);
        checkGroundDefendTrace.setChecked(true);

        radioGroundRegular.setChecked(true); 
        radioGroundOverrun.setChecked(false); 
        
        txtGroundSurpise.setText("", TextView.BufferType.NORMAL);
        txtGroundResults.setText("", TextView.BufferType.NORMAL);
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