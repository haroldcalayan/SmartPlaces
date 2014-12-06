package com.pipalapipapalapi.smartplaces.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.pipalapipapalapi.smartplaces.R;
import com.here.android.mpa.common.MapEngine;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.odml.MapLoader;
import com.here.android.mpa.odml.MapPackage;
import com.pipalapipapalapi.smartplaces.adapters.MapPackageAdapter;

public class MapDownloaderActivity extends ActionBarActivity {
	
	@InjectView(R.id.continent_map_packages_spinner) Spinner mContinentsSpinner;
	@InjectView(R.id.countries_map_packages_spinner) Spinner mCountriesSpinner;
	@InjectView(R.id.download_button) Button mDownloadButton;

	private static final String TAG = MapDownloaderActivity.class.getSimpleName();

	private Context activityContext;
	private MapEngine mMapEngine;
	
	private Map<Integer, MapPackage> mMapPackageLookup;
	private Map<Integer, List<MapPackage>> mCountriesMapPackagesMapping;
	
	private ProgressDialog mProgressDialog;
	private MapPackageAdapter mContinentAdapter;
	private MapPackageAdapter mCountriesAdapter;

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
		initViews();
		showIndeterminateProgress("Getting Map Packages", "This will only take a short while", false);
		
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
	
	private void showIndeterminateProgress(final String title, final String message, 
			final boolean isCancellable) {
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(isCancellable);
		mProgressDialog.setTitle(title);
		mProgressDialog.setMessage(message);
		mProgressDialog.show();
	}
	
	private void dismissProgressDialog() {
		if (null != mProgressDialog && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	private void initViews() {
		mProgressDialog = new ProgressDialog(activityContext);
	}
	
	private void getMapPackages() {
		MapLoader.Listener mapLoaderListener = new MapLoader.Listener() {

			public void onUninstallMapPackagesComplete(MapPackage rootMapPackage,
			    MapLoader.ResultCode mapLoaderResultCode) {
				Log.d(TAG, "onUninstallMapPackagesComplete");
				Log.i(TAG, "rootMapPackage : " + rootMapPackage);
				Log.i(TAG, "mapLoaderResultCode : " + mapLoaderResultCode);
			}

			public void onProgress(int progressPercentage) {
				Log.i(TAG, "progressPercentage : " + progressPercentage);
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
		mapLoader.addListener(mapLoaderListener);
		boolean result = mapLoader.getMapPackages();
		Log.i(TAG, "result : " + result);
	}
	
	private void processWorldMapPackage(MapPackage worldMapPackage) {
		List<MapPackage> continentMapPackageList = worldMapPackage.getChildren();
		mContinentAdapter = new MapPackageAdapter(activityContext, continentMapPackageList);
		mContinentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		for (MapPackage continentMapPackage : continentMapPackageList) {
	    int mapPackageId = continentMapPackage.getId();
	    mMapPackageLookup.put(mapPackageId, continentMapPackage);
	    
	    List<MapPackage> childMapPackagesList = continentMapPackage.getChildren();
	    mCountriesMapPackagesMapping.put(mapPackageId, childMapPackagesList);
	    
	    for (MapPackage childMapPackage : childMapPackagesList) {
	    	mMapPackageLookup.put(childMapPackage.getId(), childMapPackage);
      }
    }
		
		dismissProgressDialog();
		mContinentsSpinner.setAdapter(mContinentAdapter);
	}

}