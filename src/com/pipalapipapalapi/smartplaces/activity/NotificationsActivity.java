package com.pipalapipapalapi.smartplaces.activity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.adapters.MessagesAdapter;
import com.pipalapipapalapi.smartplaces.adapters.NotificationsAdapter;
import com.pipalapipapalapi.smartplaces.model.Message;
import com.pipalapipapalapi.smartplaces.model.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class NotificationsActivity extends ActionBarActivity {
	
	@InjectView(R.id.activity_notifications_list_view) ListView mListViewNotifications;
	private NotificationsAdapter mNotificationsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_notifications);
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
		mNotificationsAdapter = new NotificationsAdapter(getApplicationContext(), getNotificationsList());
	}

	private void initViews() {
		initActionBar();
		mListViewNotifications.setAdapter(mNotificationsAdapter);
	}
	
	private void initActionBar() {
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_messages);
	    actionBar.setTitle("Messages");
	}
	
	private List<Notification> getNotificationsList() {
		List<Notification> list = new ArrayList<Notification>();
		Notification message = new Notification();
		message.setTitle("Buy Milk");
		message.setMessage("Nearby grocery");
		message.setType(1);
		list.add(message);
		Notification message2 = new Notification();
		message2.setTitle("Pipalapipapalapi Smart Places");
		message2.setMessage("8:00 PM - 12F Smart Tower 6799 Ayala");
		message2.setType(2);
		list.add(message2);
		Notification message3 = new Notification();
		message3.setTitle("Toggles Changes.");
		message3.setMessage("10:00 PM - Nearby restaurant");
		message3.setType(3);
		list.add(message3);
		Notification message4 = new Notification();
		message4.setTitle("SMS Sent to Kia and 3 others");
		message4.setMessage("10:00 PM - Muzon, Taytay, Rizal");
		message4.setType(4);
		list.add(message4);
		return list;
	}
}
