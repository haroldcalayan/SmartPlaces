package com.pipalapipapalapi.smartplaces.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.model.Place;

public class PlacesAdapter extends ArrayAdapter<Place> implements Filterable {
	
	private static final String TAG = PlacesAdapter.class.getSimpleName();

	private ArrayList<Place> placesList;
	private ArrayList<Place> allPlacesList;
	private ArrayList<Place> suggestions;
	private ArrayFilter mFilter;
	
	private Context context;
	private Place selectedPlace;
	
  public PlacesAdapter(Context context, ArrayList<Place> placesList) {
		super(context, 0, placesList);
		allPlacesList = (ArrayList<Place>) placesList;
		this.placesList = new ArrayList<Place>(allPlacesList);
		this.suggestions = new ArrayList<Place>();
		
		Log.i(TAG, "placesList.size() : " + placesList.size());
	}
	
	
	@Override
	public int getCount() {
	  return allPlacesList.size();
	}
	
	@Override
	public Place getItem(int position) {
	  return allPlacesList.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Place place = getItem(position);
		
		ViewHolder viewHolder;
	  if (convertView == null) {
	  	viewHolder = new ViewHolder();
	  	LayoutInflater layoutInflater = LayoutInflater.from(getContext());
	  	convertView = layoutInflater.inflate(R.layout.item_place, parent, false);
	  	viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.place_title);
	  	convertView.setTag(viewHolder);
	  }
	  else {
	  	viewHolder = (ViewHolder) convertView.getTag();
	  }
	  
	  if (place != null) {
	  	viewHolder.titleTextView.setText(place.getTitle());
	  }
		
	  return convertView;
	}
	
	static class ViewHolder {
		TextView titleTextView;
	}
	
	@Override
	public Filter getFilter() {
		Log.i(TAG, "mFilter : " + mFilter);
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		
		Log.i(TAG, "mFilter : " + mFilter);
		return mFilter;
	}
	
	private class ArrayFilter extends Filter {
		private Object lock;

		@Override
    protected FilterResults performFiltering(CharSequence constraint) {
			Log.d(TAG, "performFiltering");
			FilterResults filterResults = new FilterResults();
			
			if (placesList == null) {
				synchronized (lock) {
					placesList = new ArrayList<Place>(allPlacesList);
				}
			}
			
			if (constraint == null || constraint.length() == 0) {
				synchronized (lock) {
					ArrayList<Place> list = new ArrayList<Place>(placesList);
					filterResults.values = list;
					filterResults.count = list.size();
				}
			}
			else {
				final String prefixString = constraint.toString().toLowerCase();
				
				ArrayList<Place> values = placesList;
				int count = values.size();
				
				ArrayList<Place> newValues = new ArrayList<Place>(count);
				for (int i = 0; i < count; i++) {
					Place item = values.get(i);
					if (item.getTitle().toLowerCase().contains(prefixString)) {
						newValues.add(item);
					}
				}
				
				filterResults.values = newValues;
				filterResults.count = newValues.size();
			}
			
			return filterResults;
    }

		@Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
			if (results.values != null) {
				allPlacesList = (ArrayList<Place>) results.values;
			}
			else {
				allPlacesList = new ArrayList<Place>();
			}
			
			if (results.count > 0) {
				notifyDataSetChanged();
			}
			else {
				notifyDataSetInvalidated();
			}
    }
	}
	
}
