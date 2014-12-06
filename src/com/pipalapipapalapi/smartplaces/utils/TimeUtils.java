package com.pipalapipapalapi.smartplaces.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class TimeUtils {

  public static DateTime getDeviceDateTime() {
    return DateTime.now();
  }

  public static DateTime convertToDate(String date) {
    return DateTime.parse(date);
  }

  public static DateTime convertUTCToLocal(String date) {
    DateTime dateTime = convertToDate(date);
    long timeMillis = DateTimeZone.UTC.convertUTCToLocal(dateTime.getMillis());
    return new DateTime(timeMillis);
  }

  public static LocalDateTime convertUTCToLocal(DateTime dateTime) {
    long timeMillis = DateTimeZone.UTC.convertUTCToLocal(dateTime.getMillis());
    return new LocalDateTime(timeMillis);
  }
}
