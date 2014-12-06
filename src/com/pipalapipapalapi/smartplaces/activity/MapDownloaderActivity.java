package com.pipalapipapalapi.smartplaces.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.here.android.mpa.common.MapEngine;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.odml.MapLoader;
import com.here.android.mpa.odml.MapPackage;
import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.adapters.MapPackageAdapter;

public class MapDownloaderActivity extends ActionBarActivity implements OnClickListener {
	
	@InjectView(R.id.continent_map_packages_spinner) Spinner mContinentsSpinner;
	@InjectView(R.id.countries_map_packages_spinner) Spinner mCountriesSpinner;
	@InjectView(R.id.download_button) Button mDownloadButton;

	private static final String TAG = MapDownloaderActivity.class.getSimpleName();

	private Context activityContext;
	private MapEngine mMapEngine;
	private MapLoader.Listener mMapLoaderListener;
	
	private Map<Integer, MapPackage> mMapPackageLookup;
	private Map<Integer, List<MapPackage>> mCountriesMapPackagesMapping;
	
	private ProgressDialog mProgressDialog;
	private MapPackageAdapter mContinentAdapter;
	private MapPackageAdapter mCountriesAdapter;
	
	private int selectedContinentId;
	private int selectedCountryId;
	private String selectedCountryTitle;

	/*
	 * static { System.loadLibrary("CertResourcesPkg");
	 * System.loadLibrary("MapsEngineResourcePkg"); System.loadLibrary("MAPSJNI");
	 * System.loadLibrary("NanumGothicFontPkg");
	 * System.loadLibrary("PureArabicFontPkg");
	 * System.loadLibrary("PureChineseFontPkg");
	 * System.loadLibrary("PureIndicSouthFontPkg");
	 * System.loadLibrary("PureThaiFontPkg");
	 * System.loadLibrary("SdkResourcePkg"); }
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_downloader);
		ButterKnife.inject(this);
		
		activityContext = this;
		selectedCountryTitle = "";
		initViews();
		showSpinnerProgress("Getting Map Packages", "This will only take a short while", false);
		
		mMapEngine = MapEngine.getInstance(this);
		mMapEngine.init(new OnEngineInitListener() {

			@Override
			public void onEngineInitializationCompleted(Error error) {
				Log.i(TAG, "onEngineInitializationCompleted");
				Log.i(TAG, "error : " + error);

				if (error == Error.NONE) {
					mMapPackageLookup = new HashMap<Integer, MapPackage>();
					mCountriesMapPackagesMapping = new HashMap<Integer, List<MapPackage>>();
					getMapPackages();
				}
			}
		});
	}
	
	private void showSpinnerProgress(final String title, final String message, 
			final boolean isCancellable) {
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(isCancellable);
		mProgressDialog.setTitle(title);
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
	}
	
	private void showHorizontalProgress(final String title, final String message, final boolean isCancellable) {
//		mProgressDialog = new ProgressDialog(activityContext);
		mProgressDialog.setCancelable(isCancellable);
		mProgressDialog.setTitle(title);
		mProgressDialog.setMessage(message);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setProgress(0);
		mProgressDialog.setMax(100);
		mProgressDialog.show();
	}
	
	private void dismissProgressDialog() {
		if (null != mProgressDialog && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	private void initViews() {
		initActionBar();
		mProgressDialog = new ProgressDialog(activityContext);
		mDownloadButton.setOnClickListener(this);
	}
	
	private void getMapPackages() {
		mMapLoaderListener = new MapLoader.Listener() {

			public void onUninstallMapPackagesComplete(MapPackage rootMapPackage,
			    MapLoader.ResultCode mapLoaderResultCode) {
				Log.d(TAG, "onUninstallMapPackagesComplete");
				Log.i(TAG, "rootMapPackage : " + rootMapPackage);
				Log.i(TAG, "mapLoaderResultCode : " + mapLoaderResultCode);
			}

			public void onProgress(int progressPercentage) {
				Log.i(TAG, "progressPercentage : " + progressPercentage);
				if ( null == mProgressDialog && mProgressDialog.isShowing() ) {
					mProgressDialog.setProgress(progressPercentage);
				}
			}

			public void onPerformMapDataUpdateComplete(MapPackage rootMapPackage,
			    MapLoader.ResultCode mapLoaderResultCode) {
				Log.d(TAG, "onPerformMapDataUpdateComplete");
				Log.i(TAG, "rootMapPackage : " + rootMapPackage.toString());
				Log.i(TAG, "mapLoaderResultCode : " + mapLoaderResultCode);
			}

			public void onInstallationSize(long diskSize, long networkSize) {
				Log.d(TAG, "onInstallationSize");
				Log.i(TAG, "diskSize : " + diskSize);
				Log.i(TAG, "networkSize : " + networkSize);
			}

			public void onInstallMapPackagesComplete(MapPackage rootMapPackage,
			    MapLoader.ResultCode mapLoaderResultCode) {
				Log.d(TAG, "onInstallMapPackagesComplete");
				Log.i(TAG, "rootMapPackage : " + rootMapPackage.toString());
				Log.i(TAG, "mapLoaderResultCode : " + mapLoaderResultCode);
				
				dismissProgressDialog();
				if ( MapLoader.ResultCode.OPERATION_SUCCESSFUL == mapLoaderResultCode ) {
					Toast.makeText(activityContext, selectedCountryTitle + " map downloaded.", Toast.LENGTH_SHORT)
						.show();
				}
				else {
					Toast.makeText(activityContext, mapLoaderResultCode.toString(), Toast.LENGTH_SHORT)
						.show();
				}
			}

			public void onGetMapPackagesComplete(MapPackage rootMapPackage,
			    MapLoader.ResultCode mapLoaderResultCode) {
				Log.d(TAG, "onGetMapPackagesComplete");
				Log.i(TAG, "rootMapPackage : " + rootMapPackage);
				Log.i(TAG, "mapLoaderResultCode : " + mapLoaderResultCode);
				
				if ( MapLoader.ResultCode.OPERATION_SUCCESSFUL == mapLoaderResultCode ) {
					processWorldMapPackage(rootMapPackage);					
				}
				else {
					dismissProgressDialog();
					Toast.makeText(activityContext, mapLoaderResultCode.toString(), Toast.LENGTH_SHORT)
						.show();
				}
			}

			public void onCheckForUpdateComplete(boolean updateAvailable,
			    String currentMapVersion, String newestMapVersion,
			    MapLoader.ResultCode mapLoaderResultCode) {
				Log.d(TAG, "onCheckForUpdateComplete");
				Log.i(TAG, "currentMapVersion : " + currentMapVersion);
			}

		};
		
		MapLoader mapLoader = MapLoader.getInstance();
		mapLoader.addListener(mMapLoaderListener);
		boolean result = mapLoader.getMapPackages();
		Log.i(TAG, "result : " + result);
	}
	
	private void processWorldMapPackage(MapPackage worldMapPackage) {
		List<MapPackage> continentMapPackageList = worldMapPackage.getChildren();
		mContinentAdapter = new MapPackageAdapter(activityContext, continentMapPackageList);
		mContinentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		boolean isFirst = true;
		for (MapPackage continentMapPackage : continentMapPackageList) {
	    int mapPackageId = continentMapPackage.getId();
	    mMapPackageLookup.put(mapPackageId, continentMapPackage);
	    
	    List<MapPackage> childMapPackagesList = continentMapPackage.getChildren();
	    mCountriesMapPackagesMapping.put(mapPackageId, childMapPackagesList);
	    
	    if (isFirst) {
	    	refreshCountries(childMapPackagesList);
	    	isFirst = false;
	    }
	    
	    for (MapPackage childMapPackage : childMapPackagesList) {
	    	mMapPackageLookup.put(childMapPackage.getId(), childMapPackage);
      }
    }
		
		initAdapters();
		dismissProgressDialog();
	}

	private void initAdapters() {
		mContinentsSpinner.setAdapter(mContinentAdapter);
		mCountriesSpinner.setAdapter(mCountriesAdapter);
		
		mContinentsSpinner.setOnItemSelectedListener(new ContinentsAdapterItemSelectedListener());
		mCountriesSpinner.setOnItemSelectedListener(new CountriesAdapterItemSelectedListener());
	}
	
	private void refreshCountries(List<MapPackage> countriesList) {
		mCountriesAdapter = new MapPackageAdapter(activityContext, countriesList);
  	mCountriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  	mCountriesSpinner.setAdapter(mCountriesAdapter);
  	mCountriesAdapter.notifyDataSetChanged();
  	
  	MapPackage selectedCountryMapPackage = countriesList.get(0);
  	if ( null != selectedCountryMapPackage ) {
  		selectedCountryId = selectedCountryMapPackage.getId();
  		Log.d(TAG, "selectedCountryTitle : " + selectedCountryMapPackage.getTitle());
  		Log.d(TAG, "selectedCountryId : " + selectedCountryId);
  	}
	}
	
	private class ContinentsAdapterItemSelectedListener implements OnItemSelectedListener {

		@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
        long id) {
	    MapPackage mapPackage = (MapPackage) parent.getItemAtPosition(position);
	    if ( null == mapPackage ) {
	    	return;
	    }
	    
	    Log.i(TAG, "selected continent : " + mapPackage.getTitle());
	    
	    selectedContinentId = mapPackage.getId();
	    List<MapPackage> countriesUnderContinentList = mCountriesMapPackagesMapping.get(selectedContinentId);
	    refreshCountries(countriesUnderContinentList);
    }

		@Override
    public void onNothingSelected(AdapterView<?> arg0) {
	    
    }
		
	}
	
	private class CountriesAdapterItemSelectedListener implements OnItemSelectedListener {

		@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
        long id) {
	    MapPackage mapPackage = (MapPackage) parent.getItemAtPosition(position);
	    if ( null == mapPackage ) {
	    	return;
	    }
	    
	    selectedCountryId = mapPackage.getId();
	    selectedCountryTitle = mapPackage.getTitle();
	    Log.i(TAG, "selected country : " + selectedCountryTitle);
	    Log.i(TAG, "selectedCountryId : " + selectedCountryId);
    }

		@Override
    public void onNothingSelected(AdapterView<?> arg0) {
	    
    }
		
	}
	
	private void installMapPackage() {
		List<Integer> downloadList = new ArrayList<Integer>();
		downloadList.add(selectedCountryId);
		
		MapLoader mapLoader = MapLoader.getInstance();
		mapLoader.addListener(mMapLoaderListener);
		mapLoader.installMapPackages(downloadList);
//		mapLoader.uninstallMapPackages(downloadList);
	}

	@Override
  public void onClick(View view) {
	  switch (view.getId()) {
	  	case R.id.download_button : {
	  		showHorizontalProgress("Downloading", "Downloading Map for " + selectedCountryTitle, false);
	  		installMapPackage();
	  	}
	  	break;
	  }
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
	
	private void initActionBar() {
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_settings);
	    actionBar.setTitle("Map Download");
	}

}