package com.pipalapipapalapi.smartplaces.activity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.adapters.MessagesAdapter;
import com.pipalapipapalapi.smartplaces.model.Message;
import com.pipalapipapalapi.smartplaces.model.Reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MessagesActivity extends ActionBarActivity {
	
	@InjectView(R.id.activity_messages_list_view) ListView mListViewMessages;
	
	private MessagesAdapter mMessagesAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_messages);
	  ButterKnife.inject(this);
	  initValues();
	  initViews();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_messages_activity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.menu_messages_activity_add:
			openMessageViewActivity();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initValues() {
		mMessagesAdapter = new MessagesAdapter(getApplicationContext(), getMessagesList());
	}

	private void initViews() {
		initActionBar();
		mListViewMessages.setAdapter(mMessagesAdapter);
	}
	
	private void initActionBar() {
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayUseLogoEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setDisplayShowHomeEnabled(true);
	    actionBar.setIcon(R.drawable.icon_messages);
	    actionBar.setTitle("Messages");
	}
	
	private void openMessageViewActivity() {
		Intent intent = new Intent(this, MessageViewActivity.class);
		startActivity(intent);
	}
	
	private List<Message> getMessagesList() {
		List<Message> list = new ArrayList<Message>();
		Message message = new Message();
		message.setMessage("Sample Message");
		message.setRecipientNumber("09123456789");
		list.add(message);
		return list;
	}
}
