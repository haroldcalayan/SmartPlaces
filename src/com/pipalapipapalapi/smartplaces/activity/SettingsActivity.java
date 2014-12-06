package com.pipalapipapalapi.smartplaces.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingsActivity extends ActionBarActivity implements OnClickListener {
	
	@InjectView(R.id.activity_settings_button_download_map) Button mButtonDownloadMap;
	@InjectView(R.id.activity_settings_button_sync_calendar) Button mButtonSyncCalendar;
	@InjectView(R.id.activity_settings_button_enable_promo_alerts) Button mButtonEnablePromoAlerts;
	@InjectView(R.id.activity_settings_button_fetch_facebook_likes) Button mButtonFetchFacebookLikes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_settings);
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
		mButtonDownloadMap.setOnClickListener(this);
		mButtonSyncCalendar.setOnClickListener(this);
		mButtonEnablePromoAlerts.setOnClickListener(this);
		mButtonFetchFacebookLikes.setOnClickListener(this);
	}
	
	private void initActionBar() {
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_settings);
	    actionBar.setTitle("Settings");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_settings_button_download_map:
			openMapDownloadActivity();
			break;
		case R.id.activity_settings_button_sync_calendar:
			Utils.toastMessage("Not yet available.");
			break;
		case R.id.activity_settings_button_enable_promo_alerts:
			Utils.toastMessage("Not yet available.");
			break;
		case R.id.activity_settings_button_fetch_facebook_likes:
			Utils.toastMessage("Not yet available.");
			break;
		default:
			break;
		}
	}
	
	private void openMapDownloadActivity() {
		Intent intent = new Intent(this, MapDownloaderActivity.class);
		startActivity(intent);
	}
}
