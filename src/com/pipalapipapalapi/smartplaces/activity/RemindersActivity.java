package com.pipalapipapalapi.smartplaces.activity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.adapters.RemindersAdapter;
import com.pipalapipapalapi.smartplaces.model.Reminder;
import com.pipalapipapalapi.smartplaces.model.Toggle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class RemindersActivity extends ActionBarActivity {
	
	@InjectView(R.id.activity_reminders_list_view) ListView mListViewReminders;
	
	private RemindersAdapter mRemindersAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_reminders);
	  ButterKnife.inject(this);
	  initValues();
	  initViews();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_reminders_activity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.menu_reminders_activity_add:
			openReminderViewActivity();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initValues() {
		mRemindersAdapter = new RemindersAdapter(getApplicationContext(), getRemindersList());
	}

	private void initViews() {
		initActionBar();
		mListViewReminders.setAdapter(mRemindersAdapter);
	}
	
	private void initActionBar() {
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_reminders);
	    actionBar.setTitle("Reminders");
	}
	
	private void openReminderViewActivity() {
		Intent intent = new Intent(this, ReminderViewActivity.class);
		startActivity(intent);
	}
	
	private List<Reminder> getRemindersList() {
		List<Reminder> list = new ArrayList<Reminder>();
		Reminder reminder = new Reminder();
		reminder.setMessage("Sample Message");
		list.add(reminder);
		return list;
	}
}
