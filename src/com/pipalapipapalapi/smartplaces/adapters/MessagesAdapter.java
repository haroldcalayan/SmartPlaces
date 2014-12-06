package com.pipalapipapalapi.smartplaces.adapters;

import java.util.ArrayList;
import java.util.List;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.model.Message;
import com.pipalapipapalapi.smartplaces.model.Toggle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessagesAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<Message> mMessagesList;
	
	public MessagesAdapter(Context context, List<Message> messagesList) {
		this.mContext = context;
		this.mMessagesList = messagesList;
	}
	
	public void setList(ArrayList<Message> messagesList) {
		this.mMessagesList = messagesList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMessagesList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mMessagesList.get(position);
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
	    	convertView = inflater.inflate(R.layout.adapter_messages_list_item, null);
	    	viewHolder = new ViewHolder();
	    	viewHolder.textViewMessage = (TextView) convertView.findViewById(R.id.adapter_messages_list_item_text_view_message);
	    	viewHolder.textViewRecipient = (TextView) convertView.findViewById(R.id.adapter_messages_list_item_text_view_recipient);
	    	convertView.setTag(viewHolder);
	    } else {
	    	convertView.getTag();
	    }
	    
	    Message message = mMessagesList.get(position);
	    viewHolder.textViewMessage.setText(message.getMessage());
	    viewHolder.textViewRecipient.setText(message.getRecipientNumber());

	    return convertView;
	}
	
	private static class ViewHolder {
		TextView textViewMessage;
		TextView textViewRecipient;
	}

}
