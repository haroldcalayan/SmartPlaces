package com.pipalapipapalapi.smartplaces.adapters;

import java.util.ArrayList;
import java.util.List;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.activity.MessageViewActivity;
import com.pipalapipapalapi.smartplaces.activity.ReminderViewActivity;
import com.pipalapipapalapi.smartplaces.activity.ToggleViewActivity;
import com.pipalapipapalapi.smartplaces.model.Message;
import com.pipalapipapalapi.smartplaces.model.Notification;
import com.pipalapipapalapi.smartplaces.model.Toggle;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationsAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<Notification> mNotificationsList;
	
	public NotificationsAdapter(Context context, List<Notification> notificationsList) {
		this.mContext = context;
		this.mNotificationsList = notificationsList;
	}
	
	public void setList(ArrayList<Notification> messagesList) {
		this.mNotificationsList = messagesList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mNotificationsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mNotificationsList.get(position);
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
	    	convertView = inflater.inflate(R.layout.adapter_notifications_list_item, null);
	    	viewHolder = new ViewHolder();
	    	viewHolder.imageViewNotificationImage = (ImageView) convertView.findViewById(R.id.adapter_notifications_list_item_image_view_notification_image);
	    	viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.adapter_notifications_list_item_text_view_title);
	    	viewHolder.textViewMessage = (TextView) convertView.findViewById(R.id.adapter_notifications_list_item_text_view_message);
	    	convertView.setTag(viewHolder);
	    } else {
	    	convertView.getTag();
	    }
	    
	    Notification notification = mNotificationsList.get(position);
	    final int type = notification.getType();
	    if(type == 1) {
	    	viewHolder.imageViewNotificationImage.setBackgroundResource(R.drawable.icon_reminder_black);
	    } else if(type == 2) {
	    	viewHolder.imageViewNotificationImage.setBackgroundResource(R.drawable.icon_reminder_black);
	    } else if(type == 3) {
	    	viewHolder.imageViewNotificationImage.setBackgroundResource(R.drawable.icon_toggle_black);
	    } else if(type == 4) {
	    	viewHolder.imageViewNotificationImage.setBackgroundResource(R.drawable.icon_message_black);
	    }
	    viewHolder.textViewTitle.setText(notification.getTitle());
	    viewHolder.textViewMessage.setText(notification.getMessage());
	    
	    convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(type == 1) {
			    	openReminderViewActivity();
			    } else if(type == 2) {
			    	openReminderViewActivity();
			    } else if(type == 3) {
			    	openToggleViewActivity();
			    } else if(type == 4) {
			    	openMessageViewActivity();
			    }
			}
		});
	    return convertView;
	}
	
	private void openMessageViewActivity() {
		Intent intent = new Intent(mContext, MessageViewActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
	
	private void openReminderViewActivity() {
		Intent intent = new Intent(mContext, ReminderViewActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
	
	private void openToggleViewActivity() {
		Intent intent = new Intent(mContext, ToggleViewActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
	
	private static class ViewHolder {
		ImageView imageViewNotificationImage;
		TextView textViewTitle;
		TextView textViewMessage;
	}

}
