package com.pipalapipapalapi.smartplaces.adapters;

import java.util.ArrayList;
import java.util.List;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.activity.ToggleViewActivity;
import com.pipalapipapalapi.smartplaces.model.Toggle;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TogglesAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<Toggle> mTogglesList;
	
	public TogglesAdapter(Context context, List<Toggle> togglesList) {
		this.mContext = context;
		this.mTogglesList = togglesList;
	}
	
	public void setList(ArrayList<Toggle> togglesList) {
		this.mTogglesList = togglesList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTogglesList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTogglesList.get(position);
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
	    	convertView = inflater.inflate(R.layout.adapter_toggles_list_item, null);
	    	viewHolder = new ViewHolder();
	    	viewHolder.textViewLocation = (TextView) convertView.findViewById(R.id.adapter_toggles_list_item_text_view_location);
	    	viewHolder.textViewConnection = (TextView) convertView.findViewById(R.id.adapter_toggles_list_item_text_view_connection);
	    	convertView.setTag(viewHolder);
	    } else {
	    	convertView.getTag();
	    }

	    Toggle toggle = mTogglesList.get(position);
	    viewHolder.textViewLocation.setText("lat: " + toggle.getLatitude() + " lng: " + toggle.getLongitude());
	    viewHolder.textViewConnection.setText("airplane state: " + toggle.getAirplaneModeState() + " wifi state: " + toggle.getWifiState());
	    
	    convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openToggleViewActivity();
			}
		});
	    return convertView;
	}
	
	private void openToggleViewActivity() {
		Intent intent = new Intent(mContext, ToggleViewActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
	
	private static class ViewHolder {
		TextView textViewLocation;
		TextView textViewConnection;
	}

}
