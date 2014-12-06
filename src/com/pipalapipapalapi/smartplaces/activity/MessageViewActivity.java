package com.pipalapipapalapi.smartplaces.activity;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView;
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

public class MessageViewActivity extends ActionBarActivity {
	
	@InjectView(R.id.location_autocomplete_text_view) PlaceAutoCompleteTextView mLocationACTV;
	
	private static final String TAG = MessageViewActivity.class.getSimpleName();
	
	private MapEngine mMapEngine;
	private DiscoveryResultPage mResultPage = null;
	
	private Context activityContext;
	private ArrayList<Place> placesList;
	private PlacesAdapter mPlacesAdapter;
	
	private Place selectedPlace;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_message_view);
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
		getMenuInflater().inflate(R.menu.menu_message_view_activity, menu);
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
		
	}

	private void initViews() {
		initActionBar();
	}
	
	private void initActionBar() {
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_messages);
	    actionBar.setTitle("Message");
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

}
