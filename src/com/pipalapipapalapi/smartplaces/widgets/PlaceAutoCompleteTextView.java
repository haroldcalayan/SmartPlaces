package com.pipalapipapalapi.smartplaces.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import com.pipalapipapalapi.smartplaces.model.Place;

public class PlaceAutoCompleteTextView extends AutoCompleteTextView {

	public PlaceAutoCompleteTextView(Context context, AttributeSet attrs) {
	  super(context, attrs);
  }
	
	@Override
	protected CharSequence convertSelectionToString(Object selectedItem) {
	  Place place = (Place) selectedItem;
	  
	  return place.getTitle();
	}

}
