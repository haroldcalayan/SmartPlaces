package com.pipalapipapalapi.smartplaces.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.adapters.TogglesAdapter;
import com.pipalapipapalapi.smartplaces.model.Toggle;

public class TogglesActivity extends ActionBarActivity {
	
	@InjectView(R.id.activity_toggles_list_view) ListView mListViewToggles;
	
	private TogglesAdapter mTogglesAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_toggles);
	  ButterKnife.inject(this);
	  initValues();
	  initViews();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_toggles_activity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.menu_toggles_activity_add:
			openToggleViewActivity();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initValues() {
		mTogglesAdapter = new TogglesAdapter(getApplicationContext(), getToggleList());
	}

	private void initViews() {
		initActionBar();
		mListViewToggles.setAdapter(mTogglesAdapter);
	}
	
	private void initActionBar() {
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_toggles);
	    actionBar.setTitle("Toggles");
	}
	
	private void openToggleViewActivity() {
		Intent intent = new Intent(this, ToggleViewActivity.class);
		startActivity(intent);
	}

	private List<Toggle> getToggleList() {
		List<Toggle> list = new ArrayList<Toggle>();
		Toggle toggle = new Toggle();
		toggle.setAirplaneModeState(0);
		toggle.setBluetoothState(0);
		toggle.setMobileDataState(1);
		toggle.setRingerState(1);
		toggle.setWifiState(1);
		toggle.setTrigger(1);
		list.add(toggle);
		Toggle toggle2 = new Toggle();
		toggle2.setAirplaneModeState(1);
		toggle2.setBluetoothState(0);
		toggle2.setMobileDataState(0);
		toggle2.setRingerState(0);
		toggle2.setWifiState(0);
		toggle2.setTrigger(2);
		list.add(toggle2);
		Toggle toggle3 = new Toggle();
		toggle3.setAirplaneModeState(0);
		toggle3.setBluetoothState(1);
		toggle3.setMobileDataState(1);
		toggle3.setRingerState(1);
		toggle3.setWifiState(1);
		toggle3.setTrigger(3);
		list.add(toggle3);
		return list;
	}
}
