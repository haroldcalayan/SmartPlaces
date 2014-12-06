package com.pipalapipapalapi.smartplaces.adapters;

import java.util.ArrayList;
import java.util.List;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.activity.ReminderViewActivity;
import com.pipalapipapalapi.smartplaces.model.Reminder;
import com.pipalapipapalapi.smartplaces.model.Toggle;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RemindersAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<Reminder> mRemindersList;
	
	public RemindersAdapter(Context context, List<Reminder> remindersList) {
		this.mContext = context;
		this.mRemindersList = remindersList;
	}
	
	public void setList(ArrayList<Reminder> remindersList) {
		this.mRemindersList = remindersList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mRemindersList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mRemindersList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
	    if(convertView == null) {
	    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	convertView = inflater.inflate(R.layout.adapter_reminders_list_item, null);
	    	viewHolder = new ViewHolder();
	    	viewHolder.textViewMessage = (TextView) convertView.findViewById(R.id.adapter_reminders_list_item_text_view_message);
	    	viewHolder.textViewLocationAndTime = (TextView) convertView.findViewById(R.id.adapter_reminders_list_item_text_view_location_and_time);
	    	convertView.setTag(viewHolder);
	    } else {
	    	convertView.getTag();
	    }
	    
	    Reminder reminder = mRemindersList.get(position);
	    String message[] = reminder.getMessage().split("DELIMITER");
	    viewHolder.textViewMessage.setText(message[0]);
	    viewHolder.textViewLocationAndTime.setText(message[1]);

	    convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openReminderViewActivity();
			}
		});
	    return convertView;
	}
	
	private void openReminderViewActivity() {
		Intent intent = new Intent(mContext, ReminderViewActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
	
	private static class ViewHolder {
		TextView textViewMessage;
		TextView textViewLocationAndTime;
	}

}
