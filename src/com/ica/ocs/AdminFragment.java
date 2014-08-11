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


public class AdminFragment extends Fragment {

    private View rootView;
    
    private TextView txtAdminWeather;
    private ImageView imgAdminWeatherDie1;
    private ImageView imgAdminWeatherDie2;
    private ImageView imgAdminWeatherDie3;
    private ImageView imgAdminWeatherDie4;
        
    private Spinner spinAdminInitiative;
    private ImageView imgAdminInitiativeDie1;
    private ImageView imgAdminInitiativeDie2;
    private ImageView imgAdminInitiativeDie3;
    private ImageView imgAdminInitiativeDie4;

    private TextView lblAdminSupplyPlayer1;
    private TextView txtAdminSupplyPlayer1;
    private ImageView imgAdminSupplyDie1;
    private ImageView imgAdminSupplyDie2;

    private TextView lblAdminSupplyPlayer2;
    private TextView txtAdminSupplyPlayer2;
    private ImageView imgAdminSupplyDie3;
    private ImageView imgAdminSupplyDie4;
            
    private TextView lblAdminReinforcementsPlayer1;
    private TextView txtAdminReinforcementsPlayer1;
    private ImageView imgAdminReinforcementsDie1;
    private ImageView imgAdminReinforcementsDie2;

    private TextView lblAdminReinforcementsPlayer2;
    private TextView txtAdminReinforcementsPlayer2;
    private ImageView imgAdminReinforcementsDie3;
    private ImageView imgAdminReinforcementsDie4;

	private Button btnAdminDiceRoll;
	
    private Game game;
    private Saved saved;
    private Dice dice;
	private PlayAudio audio;
    private Initiative initiative;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            final ViewParent parent = rootView.getParent();
            if (parent != null && parent instanceof ViewGroup)
                ((ViewGroup) parent).removeView(rootView);  
        }
        else {
		    dice = new Dice(16, 1, 6);
		    audio = new PlayAudio (getActivity());
        
            Intent intent = getActivity().getIntent();
            game = Ocs.getGame(intent.getIntExtra ("Game", -1));
            saved = Ocs.getSaved(game);
        
            String[] players = game.getPlayerList();
            initiative = new Initiative(players);
        
            rootView = inflater.inflate(R.layout.admin, container, false);
        
            txtAdminWeather = (TextView)rootView.findViewById(R.id.txtAdminWeather);
            imgAdminWeatherDie1 = (ImageView)rootView.findViewById(R.id.imgAdminWeatherDie1);
            imgAdminWeatherDie2 = (ImageView)rootView.findViewById(R.id.imgAdminWeatherDie2);
            imgAdminWeatherDie3 = (ImageView)rootView.findViewById(R.id.imgAdminWeatherDie3);
            imgAdminWeatherDie4 = (ImageView)rootView.findViewById(R.id.imgAdminWeatherDie4);
        
            spinAdminInitiative = (Spinner)rootView.findViewById(R.id.spinAdminInitiative);
            imgAdminInitiativeDie1 = (ImageView)rootView.findViewById(R.id.imgAdminInitiativeDie1);
            imgAdminInitiativeDie2 = (ImageView)rootView.findViewById(R.id.imgAdminInitiativeDie2);
            imgAdminInitiativeDie3 = (ImageView)rootView.findViewById(R.id.imgAdminInitiativeDie3);
            imgAdminInitiativeDie4 = (ImageView)rootView.findViewById(R.id.imgAdminInitiativeDie4);

            lblAdminSupplyPlayer1 = (TextView)rootView.findViewById(R.id.lblAdminSupplyPlayer1);
            txtAdminSupplyPlayer1 = (TextView)rootView.findViewById(R.id.txtAdminSupplyPlayer1);
            imgAdminSupplyDie1 = (ImageView)rootView.findViewById(R.id.imgAdminSupplyDie1);
            imgAdminSupplyDie2 = (ImageView)rootView.findViewById(R.id.imgAdminSupplyDie2);

            lblAdminSupplyPlayer2 = (TextView)rootView.findViewById(R.id.lblAdminSupplyPlayer2);
            txtAdminSupplyPlayer2 = (TextView)rootView.findViewById(R.id.txtAdminSupplyPlayer2);
            imgAdminSupplyDie3 = (ImageView)rootView.findViewById(R.id.imgAdminSupplyDie3);
            imgAdminSupplyDie4 = (ImageView)rootView.findViewById(R.id.imgAdminSupplyDie4);
            
            lblAdminReinforcementsPlayer1 = (TextView)rootView.findViewById(R.id.lblAdminReinforcementsPlayer1);
            txtAdminReinforcementsPlayer1 = (TextView)rootView.findViewById(R.id.txtAdminReinforcementsPlayer1);
            imgAdminReinforcementsDie1 = (ImageView)rootView.findViewById(R.id.imgAdminReinforcementsDie1);
            imgAdminReinforcementsDie2 = (ImageView)rootView.findViewById(R.id.imgAdminReinforcementsDie2);

            lblAdminReinforcementsPlayer2 = (TextView)rootView.findViewById(R.id.lblAdminReinforcementsPlayer2);
            txtAdminReinforcementsPlayer2 = (TextView)rootView.findViewById(R.id.txtAdminReinforcementsPlayer2);
            imgAdminReinforcementsDie3 = (ImageView)rootView.findViewById(R.id.imgAdminReinforcementsDie3);
            imgAdminReinforcementsDie4 = (ImageView)rootView.findViewById(R.id.imgAdminReinforcementsDie4);

            btnAdminDiceRoll = (Button)rootView.findViewById(R.id.btnAdminDiceRoll);

            Weather weather = game.getWeather();
            int wxdice = weather.getDice().getNumber();
            if (wxdice < 4) {
                imgAdminWeatherDie4.setVisibility(View.INVISIBLE);
            }
            if (wxdice < 3) {
                imgAdminWeatherDie3.setVisibility(View.INVISIBLE);
            }
            if (wxdice < 2) {
                imgAdminWeatherDie2.setVisibility(View.INVISIBLE);
            }
        
		    ArrayAdapter<String> adapter1 = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item, players);
		    adapter1.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		    spinAdminInitiative.setAdapter(adapter1);
		

		    imgAdminWeatherDie1.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(0);
			        displayDice();
			        updateWeather();
			    }
		    });
		    imgAdminWeatherDie2.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(1);
			        displayDice();
			        updateWeather();
			    }
		    });
		    imgAdminWeatherDie3.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(2);
			        displayDice();
			        updateWeather();
			    }
		    });
		    imgAdminWeatherDie4.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(3);
			        displayDice();
			        updateWeather();
			    }
		    });


            spinAdminInitiative.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    saved.setInitiative(game.getPlayers().get(pos).getName());
                    GameActivity activity = (GameActivity)getActivity();
                    activity.updateInitiativePlayer();
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

            imgAdminInitiativeDie1.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(4);
			        displayDice();
			        updateInitiative();
			    }
		    });
		    imgAdminInitiativeDie2.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(5);
			        displayDice();
			        updateInitiative();
			    }
		    });
		    imgAdminInitiativeDie3.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(6);
			        displayDice();
			        updateInitiative();
			    }
		    });
		    imgAdminInitiativeDie4.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(7);
			        displayDice();
			        updateInitiative();
			    }
		    });
            
            
		    imgAdminSupplyDie1.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(8);
			        displayDice();
			        updateSupply();
			    }
		    });
		    imgAdminSupplyDie2.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(9);
			        displayDice();
			        updateSupply();
			    }
		    });
		    imgAdminSupplyDie3.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(10);
			        displayDice();
			        updateSupply();
			    }
		    });
		    imgAdminSupplyDie4.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(11);
			        displayDice();
			        updateSupply();
			    }
		    });
            
            
		    imgAdminReinforcementsDie1.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(12);
			        displayDice();
			        updateReinforcements();
			    }
		    });
		    imgAdminReinforcementsDie2.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(13);
			        displayDice();
			        updateReinforcements();
			    }
		    });
		    imgAdminReinforcementsDie3.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(14);
			        displayDice();
			        updateReinforcements();
			    }
		    });
		    imgAdminReinforcementsDie4.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
                    dice.increment(15);
			        displayDice();
			        updateReinforcements();
			    }
		    });
            
            
		    btnAdminDiceRoll.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			        audio.play();
			        dice.roll();
			        displayDice();
			        updateResults();
			    }
		    });
		
		    displayPlayers();
            displayWeather();
            displayInitiative();
            displaySupply();
            displayReinforcements();
		    displayDice();
        }
        return rootView;
    }            
    
	void updateResults() {
		updateWeather();
		updateInitiative();
		updateSupply();
		updateReinforcements();
        save();
	}

    void updateWeather() {
        int turn = saved.getTurn()+1;
        Weather weather = game.getWeather();
		String result = weather.resolve(turn, dice.getDie(0), dice.getDie(1), dice.getDie(2), dice.getDie(3));
        saved.setWeather(result);
        displayWeather();
    }
    
    void updateInitiative() {
        String result = initiative.resolve(dice.getDie(4) + dice.getDie(5), dice.getDie(6) + dice.getDie(7));
        saved.setInitiative(result);
	    displayInitiative();
    }
    
    void updateSupply() {
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        int turn = saved.getTurn()+1;
    
        String result = player1.findSupply(dice.getDie(8) + dice.getDie(9), turn);
        saved.setPlayer1Supply(result);
        
        result = player2.findSupply(dice.getDie(10) + dice.getDie(11), turn);
        saved.setPlayer2Supply(result);
        
        displaySupply();
    }
    
    void updateReinforcements() {
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        int turn = saved.getTurn()+1;
    
        String result = player1.findReinforcements(dice.getDie(12) + dice.getDie(13), turn);
        saved.setPlayer1Reinforcements(result);
        
        result = player2.findReinforcements(dice.getDie(14) + dice.getDie(15), turn);
        saved.setPlayer2Reinforcements(result);
        
        displayReinforcements();
    }
    
    void displayPlayers() {
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        
        lblAdminSupplyPlayer1.setText(player1.getName(), TextView.BufferType.NORMAL);
        lblAdminSupplyPlayer2.setText(player2.getName(), TextView.BufferType.NORMAL);
        lblAdminReinforcementsPlayer1.setText(player1.getName(), TextView.BufferType.NORMAL);
        lblAdminReinforcementsPlayer2.setText(player2.getName(), TextView.BufferType.NORMAL);
    }
    
    void displayWeather() {
        txtAdminWeather.setText(saved.getWeather(), TextView.BufferType.NORMAL);
    }
    
    void displayInitiative() {
		spinAdminInitiative.setSelection(game.getPlayerIndex(saved.getInitiative()));
    }
    
    void displaySupply() {
        txtAdminSupplyPlayer1.setText(saved.getPlayer1Supply(), TextView.BufferType.NORMAL);
        txtAdminSupplyPlayer2.setText(saved.getPlayer2Supply(), TextView.BufferType.NORMAL);
    }
    
    void displayReinforcements() {
        txtAdminReinforcementsPlayer1.setText(saved.getPlayer1Reinforcements(), TextView.BufferType.NORMAL);
        txtAdminReinforcementsPlayer2.setText(saved.getPlayer2Reinforcements(), TextView.BufferType.NORMAL);
    }
    
	void displayDice() {
        dice.set(0, DieColor.WHITE_BLACK, imgAdminWeatherDie1);
		dice.set(1, DieColor.RED_WHITE,   imgAdminWeatherDie2);
        dice.set(2, DieColor.BLUE_WHITE,  imgAdminWeatherDie3);
		dice.set(3, DieColor.GREEN_WHITE, imgAdminWeatherDie4);
    
        dice.set(4, DieColor.WHITE_BLACK, imgAdminInitiativeDie1);
		dice.set(5, DieColor.RED_WHITE,   imgAdminInitiativeDie2);
        dice.set(6, DieColor.BLACK_RED,  imgAdminInitiativeDie3);
		dice.set(7, DieColor.BLACK_WHITE, imgAdminInitiativeDie4);
 
        dice.set(8, DieColor.WHITE_BLACK, imgAdminSupplyDie1);
		dice.set(9, DieColor.RED_WHITE,   imgAdminSupplyDie2);
        dice.set(10, DieColor.BLACK_RED,   imgAdminSupplyDie3);
		dice.set(11, DieColor.BLACK_WHITE, imgAdminSupplyDie4);
    
        dice.set(12, DieColor.WHITE_BLACK, imgAdminReinforcementsDie1);
		dice.set(13, DieColor.RED_WHITE,   imgAdminReinforcementsDie2);
        dice.set(14, DieColor.BLACK_RED,   imgAdminReinforcementsDie3);
		dice.set(15, DieColor.BLACK_WHITE, imgAdminReinforcementsDie4);
	}
    
    private void save() {
        Ocs.saveSaved();
	}
}