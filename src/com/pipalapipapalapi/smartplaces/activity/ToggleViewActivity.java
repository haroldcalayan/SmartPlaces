package com.pipalapipapalapi.smartplaces.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.MapEngine;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.search.DiscoveryLink;
import com.here.android.mpa.search.DiscoveryRequest;
import com.here.android.mpa.search.DiscoveryResult;
import com.here.android.mpa.search.DiscoveryResult.ResultType;
import com.here.android.mpa.search.DiscoveryResultPage;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.PlaceLink;
import com.here.android.mpa.search.ResultListener;
import com.here.android.mpa.search.SearchRequest;
import com.pipalapipapalapi.smartplaces.Constants;
import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.adapters.PlacesAdapter;
import com.pipalapipapalapi.smartplaces.model.Place;
import com.pipalapipapalapi.smartplaces.widgets.PlaceAutoCompleteTextView;

public class ToggleViewActivity extends ActionBarActivity implements OnClickListener {
	
	@InjectView(R.id.location_autocomplete_text_view) PlaceAutoCompleteTextView mLocationACTV;
	@InjectView(R.id.wifi_imageview) ImageView mWifiImageView;
	@InjectView(R.id.data_imageview) ImageView mDataImageView;
	@InjectView(R.id.bluetooth_imageview) ImageView mBluetoothImageView;
	@InjectView(R.id.airplane_imageview) ImageView mAirplaneImageView;
	@InjectView(R.id.ringer_imageview) ImageView mRingerImageView;
	
	private static final String TAG = ToggleViewActivity.class.getSimpleName();
	
	private MapEngine mMapEngine;
	private DiscoveryResultPage mResultPage = null;
	
	private Context activityContext;
	private ArrayList<Place> placesList;
	private PlacesAdapter mPlacesAdapter;
	
	private Place selectedPlace;
	
	private Map<String, Integer> togglesStateMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_toggle_view);
	  ButterKnife.inject(this);
	  
	  activityContext = this;
	  
	  initValues();
	  initViews();
	  
	  mMapEngine = MapEngine.getInstance(this);
		mMapEngine.init(new OnEngineInitListener() {

			@Override
			public void onEngineInitializationCompleted(Error error) {
				Log.i(TAG, "onEngineInitializationCompleted");
				Log.i(TAG, "error : " + error);

				if (error == Error.NONE) {
					placesList = new ArrayList<Place>();
					mLocationACTV.addTextChangedListener(mTextWatcher);
					mLocationACTV.setOnItemClickListener(new PlaceAdapterItemClickedListener());
				}
			}
		});
	  
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_toggle_view_activity, menu);
		return true;
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
		togglesStateMap = new HashMap<String, Integer>();
		togglesStateMap.put("wifi", 0);
		togglesStateMap.put("data", 0);
		togglesStateMap.put("bluetooth", 0);
		togglesStateMap.put("airplane", 0);
		togglesStateMap.put("ringer", 0);
	}

	private void initViews() {
		initActionBar();
		
		mWifiImageView.setOnClickListener(this);
		mDataImageView.setOnClickListener(this);
		mBluetoothImageView.setOnClickListener(this);
		mAirplaneImageView.setOnClickListener(this);
		mRingerImageView.setOnClickListener(this);
	}
	
	private void initActionBar() {
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_toggles);
	    actionBar.setTitle("Toggle");
	}
	
	private TextWatcher mTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable editable) {
			String searchKeyword = editable.toString();
			try {
				GeoCoordinate geoCoordinate = new GeoCoordinate(Constants.SMART_TOWER_LAT, Constants.SMART_TOWER_LNG);
				
				DiscoveryRequest request = new SearchRequest(searchKeyword).setSearchCenter(geoCoordinate);
				request.setCollectionSize(Constants.SEARCH_COLLECTION_SIZE);
				
				ErrorCode errorCode = request.execute(new SearchRequestListener());
				if (ErrorCode.NONE != errorCode) {
					
				}
			}
			catch (IllegalArgumentException e) {
				Log.e(TAG, "error : " + e.getMessage());
			}
		}
	};
	
	private void refreshPlaces(ArrayList<Place> placesList) {
		Log.d(TAG, "refreshPlaces");
		Log.i(TAG, "placesList.size() : " + placesList.size());
		mPlacesAdapter = new PlacesAdapter(activityContext, placesList);
		mLocationACTV.setAdapter(mPlacesAdapter);
		mPlacesAdapter.notifyDataSetChanged();
	}
	
	private class SearchRequestListener implements ResultListener<DiscoveryResultPage> {
		
		@Override
	  public void onCompleted(DiscoveryResultPage data, ErrorCode error) {
			Log.d(TAG, "onCompleted");
			placesList.clear();
			
		  if (ErrorCode.NONE == error) {
		  	List<DiscoveryResult> discoveryItems = data.getItems();
		  	Log.i(TAG, "discoveryItems.size() : " + discoveryItems.size());
		  	
		  	for (DiscoveryResult discoveryResult : discoveryItems) {
		      Log.i(TAG, "discoveryResult.getTitle() : " + discoveryResult.getTitle());
		      
		      if (ResultType.PLACE == discoveryResult.getResultType()) {
		      	Log.i(TAG, "Place");
		      	PlaceLink placeLink = (PlaceLink) discoveryResult;
		      	
		      	GeoCoordinate coordinates = placeLink.getPosition();
		      	Log.i(TAG, "coordinates.getLatitude() : " + coordinates.getLatitude());
		      	Log.i(TAG, "coordinates.getLongitude() : " + coordinates.getLongitude());
		      	
		      	Place.Builder placeBuilder = new Place.Builder();
		      	placeBuilder
		      		.setTitle(placeLink.getTitle())
		      		.setLatitude(coordinates.getLatitude())
		      		.setLongitude(coordinates.getLongitude())
		      		.setIconUrl(placeLink.getIconUrl())
		      		.setDistance(placeLink.getDistance());
		      	Place place = placeBuilder.build();
		      	
		      	placesList.add(place);
		      }
		      else if (ResultType.DISCOVERY == discoveryResult.getResultType()) {
		      	Log.i(TAG, "Discovery");
		      	DiscoveryLink discoveryLink = (DiscoveryLink) discoveryResult;
		      	Log.i(TAG, "discoveryLink.getUrl() : " + discoveryLink.getUrl()); 
		      }
	      }
		  	
		  	refreshPlaces(placesList);
		  	mResultPage = data;
		  }
		  else {
		  	
		  }
	  }

	}
	
	private class PlaceAdapterItemClickedListener implements android.widget.AdapterView.OnItemClickListener {

		@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.d(TAG, "onItemClick");
			
	    Place place = (Place) parent.getItemAtPosition(position);
	    if (null == place) {
	    	return;
	    }
	    
	    selectedPlace = place;
	    Log.w(TAG, "selectedPlace.getTitle() : " + selectedPlace.getTitle());
    }

	}

	@Override
  public void onClick(View view) {
		switch (view.getId()) {
			case R.id.wifi_imageview : {
				int state = togglesStateMap.get("wifi");
				state = ((state + 1) % 2);
				
				Log.i(TAG, "state : " + state);
				
				if (state == 0) {
					mWifiImageView.setImageResource(R.drawable.toggle_wifi_off);
				}
				else {
					mWifiImageView.setImageResource(R.drawable.toggle_wifi_on);
				}
				
				togglesStateMap.put("wifi", state);
			}
			break;
			
			case R.id.data_imageview : {
				int state = togglesStateMap.get("data");
				state = ((state + 1) % 2);
				
				Log.i(TAG, "state : " + state);
				
				if (state == 0) {
					mDataImageView.setImageResource(R.drawable.toggle_data_off);
				}
				else {
					mDataImageView.setImageResource(R.drawable.toggle_data_on);
				}
				
				togglesStateMap.put("data", state);
			}
			break;
			
			case R.id.bluetooth_imageview : {
				int state = togglesStateMap.get("bluetooth");
				state = ((state + 1) % 2);
				
				Log.i(TAG, "state : " + state);
				
				if (state == 0) {
					mBluetoothImageView.setImageResource(R.drawable.toggle_bluetooth_off);
				}
				else {
					mBluetoothImageView.setImageResource(R.drawable.toggle_bluetooth_on);
				}
				
				togglesStateMap.put("bluetooth", state);
			}
			break;
			
			case R.id.airplane_imageview : {
				int state = togglesStateMap.get("airplane");
				state = ((state + 1) % 2);
				
				Log.i(TAG, "state : " + state);
				
				if (state == 0) {
					mAirplaneImageView.setImageResource(R.drawable.toggle_airplane_off);
				}
				else {
					mAirplaneImageView.setImageResource(R.drawable.toggle_airplane_on);
				}
				
				togglesStateMap.put("airplane", state);
			}
			break;
			
			case R.id.ringer_imageview : {
				int state = togglesStateMap.get("ringer");
				state = ((state + 1) % 2);
				
				Log.i(TAG, "state : " + state);
				
				if (state == 0) {
					mRingerImageView.setImageResource(R.drawable.toggle_ringer_off);
				}
				else {
					mRingerImageView.setImageResource(R.drawable.toggle_ringer_on);
				}
				
				togglesStateMap.put("ringer", state);
			}
			break;
		}
  }
	
}
