package com.pipalapipapalapi.smartplaces.activity;

import butterknife.ButterKnife;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.pipalapipapalapi.smartplaces.R;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class HereMapActivity extends ActionBarActivity {
	
    private Map map = null;
    private MapFragment mapFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_here_map);
        ButterKnife.inject(this);
	  	initValues();
	  	initViews();    
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
    	initMap();
    }
    
    private void initActionBar() {
    	ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_map);
	    actionBar.setTitle("Your Map");
    }
    
    private void initMap() {
    	mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    map = mapFragment.getMap();
                    map.setCenter(new GeoCoordinate(14.558313799999999, 121.01802219999999, 0.0), Map.Animation.NONE);
                    map.setZoomLevel(((map.getMaxZoomLevel() + map.getMinZoomLevel())) * 0.8);
                } else {
                    System.out.println("ERROR: Cannot initialize Map Fragment");
                }
            }
        });
    }
}
