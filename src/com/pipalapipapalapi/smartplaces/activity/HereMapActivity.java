package com.pipalapipapalapi.smartplaces.activity;

import java.io.IOException;

import butterknife.ButterKnife;

import com.here.android.mpa.ar.ARController;
import com.here.android.mpa.ar.ARIconObject;
import com.here.android.mpa.ar.CompositeFragment;
import com.here.android.mpa.ar.ARController.Error;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.pipalapipapalapi.smartplaces.R;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HereMapActivity extends ActionBarActivity {
	
    private Map map;
    private CompositeFragment compositeFragment;
    private ARController arController;
    private Button startButton;
    private Button stopButton;
    private Button toggleObjectButton;
    private Image image;
    private ARIconObject arIconObject;
    private boolean objectAdded;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_here_map);
        ButterKnife.inject(this);
	  	initValues();
	  	initViews();    
        compositeFragment = (CompositeFragment) getFragmentManager().findFragmentById(R.id.compositefragment);
        compositeFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    map = compositeFragment.getMap();
                    map.setCenter(new GeoCoordinate(14.558313799999999, 121.01802219999999, 0.0), Map.Animation.NONE);
                    map.setZoomLevel(((map.getMaxZoomLevel() + map.getMinZoomLevel())) * 80);
                    setupLiveSight();
                } else {
                    System.out.println("ERROR: Cannot initialize Composite Fragment");
                }
            }
        });

        startButton = (Button) findViewById(R.id.startLiveSight);
        stopButton = (Button) findViewById(R.id.stopLiveSight);
        toggleObjectButton = (Button) findViewById(R.id.toggleObject);
        
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
    
    private void initValues() {
    	
    }
    
    private void initViews() {
    	initActionBar();
    }
    
    private void initActionBar() {
    	ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_map);
	    actionBar.setTitle("Your Map");
    }
    
    private void setupLiveSight() {
        arController = compositeFragment.getARController();
        arController.setUseDownIconsOnMap(true);
        arController.setAlternativeCenter(new GeoCoordinate(49.279483, -123.116906, 0.0));
    }

    public void startLiveSight(View view) {
        if (arController != null) {
            Error error = arController.start();

            if (error == Error.NONE) {
                startButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Error starting LiveSight: " + error.toString(), Toast.LENGTH_LONG);
            }
        }
    }

    public void stopLiveSight(View view) {
        if (arController != null) {
            Error error = arController.stop(true);

            if (error == Error.NONE) {
                startButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.GONE);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Error stopping LiveSight: " + error.toString(), Toast.LENGTH_LONG);
            }
        }
    }

    public void toggleObject(View view) {
        if (arController != null) {
            if (!objectAdded) {
                if (arIconObject == null) {

                    image = new Image();
                    try {
                        image.setImageResource(R.drawable.icon_main);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // creates a new icon object which uses the same image in up and down views
                    arIconObject = new ARIconObject(new GeoCoordinate(49.276744, -123.112049, 2.0),
                            (View) null, image);
                }

                // adds the icon object to LiveSight to be rendered 
                arController.addARObject(arIconObject);
                objectAdded = true;
                toggleObjectButton.setText("Remove Object");
            } else {
                
                // removes the icon object from LiveSight, it will no longer be rendered
                arController.removeARObject(arIconObject);
                objectAdded = false;
                toggleObjectButton.setText("Add Object");
            }
        }
    }
}
