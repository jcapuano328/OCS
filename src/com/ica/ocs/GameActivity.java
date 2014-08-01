package com.ica.ocs;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.*;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.widget.*;

import com.ica.ocs.Adapters.TabsPagerAdapter;
import com.ica.ocs.Core.*;

public class GameActivity extends FragmentActivity implements ActionBar.TabListener {
	private TextView txtTurn;
	private Button btnTurnPrev;
	private Button btnTurnNext;
	private TextView txtPhase;
	private Button btnPhasePrev;
	private Button btnPhaseNext;
    
    private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
    
    private GestureDetector turnGestDetector;
    private GestureDetector phaseGestDetector;
    
	private Game game;
    private Saved saved;

    @Override
    public void onCreate (Bundle bundle) {
        super.onCreate(bundle);

        Intent intent = getIntent();
        game = Ocs.getGame(intent.getIntExtra ("Game", -1));
        saved = Ocs.getSaved(game);

        setContentView(R.layout.game);		

		// current turn
		txtTurn = (TextView)findViewById(R.id.textTurn);
		btnTurnPrev = (Button)findViewById(R.id.btnTurnPrev);
		btnTurnNext = (Button)findViewById(R.id.btnTurnNext);

		// current phase
		txtPhase = (TextView)findViewById(R.id.textPhase);
		btnPhasePrev = (Button)findViewById(R.id.btnPhasePrev);
		btnPhaseNext = (Button)findViewById(R.id.btnPhaseNext);

        // tabs
		viewPager = (ViewPager) findViewById(R.id.gameViews);
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(game.getName());
        actionBar.setSubtitle(game.getDesc());

        int resid = getApplicationContext().getResources().getIdentifier("drawable/" + game.getImage() + "sm", null, getApplicationContext().getPackageName());
        actionBar.setIcon(resid);

		// Adding Tabs
        String[] tabs = { "Admin", "Ground", "Barrage", "Air", "Sea", "Supply" };
		for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
		}
        
		btnTurnPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                prevTurn();
			}
		});        
        
		btnTurnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                nextTurn();
			}
		});        

		btnPhasePrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                prevPhase();
			}
		});        
        
		btnPhaseNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
                nextPhase();
			}
		});        


        // swipe to change turns
        turnGestDetector = new GestureDetector(this, new SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d("Turn Gesture", "Scroll");
                return false;
            }
            @Override
            public boolean onDown(MotionEvent e1) {
                Log.d("Turn Gesture", "Down");
                return false;
            }
            @Override
            public void onLongPress(MotionEvent arg0) {
                Log.d("Turn Gesture", "LongPress");
            }
            @Override
            public void onShowPress(MotionEvent arg0) {
                Log.d("Turn Gesture", "ShowPress");
            }
            @Override
            public boolean onSingleTapUp(MotionEvent arg0) {
                Log.d("Turn Gesture", "SingleTapUp");
                return false;
            }        
            
            @Override
            public boolean onFling(MotionEvent start, MotionEvent finish, float velocityX, float velocityY) {
                Log.d("Turn Gesture", "Fling");
                if (start.getRawX() < finish.getRawX()) {
                    Log.d("Turn Fling", "next turn");
                    nextTurn();
                }
                else {
                    Log.d("Turn Fling", "prev turn");
                    prevTurn();
                }
            
                return true;
            }
        });      
        /*
		txtTurn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return turnGestDetector.onTouchEvent(event);
            }
        });     
        */
        
        // swipe to change phases
        phaseGestDetector = new GestureDetector(this, new SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d("Phase Gesture", "Scroll");
                return false;
            }
            @Override
            public boolean onDown(MotionEvent e1) {
                Log.d("Phase Gesture", "Down");
                return false;
            }
            @Override
            public void onLongPress(MotionEvent arg0) {
                Log.d("Phase Gesture", "LongPress");
            }
            @Override
            public void onShowPress(MotionEvent arg0) {
                Log.d("Phase Gesture", "ShowPress");
            }
            @Override
            public boolean onSingleTapUp(MotionEvent arg0) {
                Log.d("Phase Gesture", "SingleTapUp");
                return false;
            }        

            @Override
            public boolean onFling(MotionEvent start, MotionEvent finish, float velocityX, float velocityY) {
                Log.d("Phase Gesture", "Fling");
                if (start.getRawX() < finish.getRawX()) {
                    Log.d("Phase Fling", "next phase");
                    nextPhase();
                }                    
                else {
                    Log.d("Phase Fling", "prev phase");
                    prevPhase();
                }                    
            
                return true;
            }
        });        
        /*
		txtPhase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return phaseGestDetector.onTouchEvent(event);
            }
        });        
        */
        
		/**
		 * on swiping the viewpager make respective tab selected
         * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
        
        update();
	}

    @Override
    public void onResume () {
        super.onResume();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.gameReset:
                saved.reset(game);
                update();
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
        
        
    @Override
    public boolean onTouchEvent(MotionEvent mev) {
        if (isPointInsideView(mev.getRawX(), mev.getRawY(), txtTurn))
            return turnGestDetector.onTouchEvent(mev);
        else if (isPointInsideView(mev.getRawX(), mev.getRawY(), txtPhase))
            return phaseGestDetector.onTouchEvent(mev);
        return false;            
    }                
        
    
    /**
     * Determines if given points are inside view
     * @param x - x coordinate of point
     * @param y - y coordinate of point
     * @param view - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    private boolean isPointInsideView(float x, float y, View view){
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if(( x > viewX && x < (viewX + view.getWidth())) &&
                ( y > viewY && y < (viewY + view.getHeight()))){
            return true;
        } else {
            return false;
        }
    }                
    
    private void prevTurn() {
		Ocs.prevTurn(game, saved);
		update();
		save(); 
	}
    private void nextTurn() {
		Ocs.nextTurn(game, saved);
		update();
		save(); 
	}

    private void prevPhase() {
    	Ocs.prevPhase(game, saved);
		update();
		save(); 
	}
    private void nextPhase() {
    	Ocs.nextPhase(game, saved);
		update();
		save(); 
	}
    
    private void update() {
		txtTurn.setText(Ocs.getCurrentTurn(game, saved));
		txtPhase.setText(Ocs.getCurrentPhase(game, saved));
	}
	
    private void save() {
        Ocs.saveSaved();
	}
}