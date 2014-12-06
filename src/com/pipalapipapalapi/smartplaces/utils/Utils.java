package com.pipalapipapalapi.smartplaces.utils;

import java.text.DecimalFormat;

import android.widget.Toast;

import com.pipalapipapalapi.smartplaces.SmartPlacesApplication;

public class Utils {

  public static void toastMessage(String message) {
    Toast.makeText(SmartPlacesApplication.getContext(), message, Toast.LENGTH_SHORT).show();
  }
  
  /**
   * @see http://stackoverflow.com/a/5599842
   * 
   * @param size
   * @return String
   */
  public static String getReadableFileSize(long size) {
  	if(size <= 0) return "0";
    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
  }
  
}
