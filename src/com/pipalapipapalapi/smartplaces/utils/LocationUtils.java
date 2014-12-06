package com.pipalapipapalapi.smartplaces.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.pipalapipapalapi.smartplaces.SmartPlacesApplication;

public class LocationUtils {

  public static boolean isLocationEnabled(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      int locationMode = Settings.Secure.LOCATION_MODE_OFF;
      try {
        locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
      } catch (Settings.SettingNotFoundException ignored) {
      }
      return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }
    String locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
    return !TextUtils.isEmpty(locationProviders);
  }
}
