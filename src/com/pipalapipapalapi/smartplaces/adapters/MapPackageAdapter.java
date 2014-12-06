package com.pipalapipapalapi.smartplaces.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.here.android.mpa.odml.MapPackage;
import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.utils.Utils;

public class MapPackageAdapter extends ArrayAdapter<MapPackage> {

	public MapPackageAdapter(Context context, List<MapPackage> continentMapPackageList) {
		super(context, 0, continentMapPackageList);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	  MapPackage mapPackage = getItem(position);
	  
	  ViewHolder viewHolder;
	  if (convertView == null) {
	  	viewHolder = new ViewHolder();
	  	LayoutInflater layoutInflater = LayoutInflater.from(getContext());
	  	convertView = layoutInflater.inflate(R.layout.item_map_package, parent, false);
	  	viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.map_package_title);
	  	viewHolder.sizeTextView = (TextView) convertView.findViewById(R.id.map_package_size);
	  	convertView.setTag(viewHolder);
	  }
	  else {
	  	viewHolder = (ViewHolder) convertView.getTag();
	  }
	  
	  viewHolder.titleTextView.setText(mapPackage.getTitle());
	  viewHolder.sizeTextView.setText(Utils.getReadableFileSize(mapPackage.getSize()));
		
	  return convertView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		MapPackage mapPackage = getItem(position);
	  
	  ViewHolder viewHolder;
	  if (convertView == null) {
	  	viewHolder = new ViewHolder();
	  	LayoutInflater layoutInflater = LayoutInflater.from(getContext());
	  	convertView = layoutInflater.inflate(R.layout.item_map_package, parent, false);
	  	viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.map_package_title);
	  	viewHolder.sizeTextView = (TextView) convertView.findViewById(R.id.map_package_size);
	  	convertView.setTag(viewHolder);
	  }
	  else {
	  	viewHolder = (ViewHolder) convertView.getTag();
	  }
	  
	  viewHolder.titleTextView.setText(mapPackage.getTitle());
	  viewHolder.sizeTextView.setText(Utils.getReadableFileSize(mapPackage.getSize()));
		
	  return convertView;
	}
	
	static class ViewHolder {
		TextView titleTextView;
		TextView sizeTextView;
	}
	
}
