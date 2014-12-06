package com.pipalapipapalapi.smartplaces.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.pipalapipapalapi.smartplaces.R;
import com.pipalapipapalapi.smartplaces.utils.ImageUtils;
import com.pipalapipapalapi.smartplaces.utils.Utils;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

  @InjectView(R.id.activity_main_image_view_background) ImageView mImageViewBackground;
  @InjectView(R.id.activity_main_relative_layout_top_image_view_settings) ImageView mImageViewSettings;
  @InjectView(R.id.activity_main_relative_layout_top_image_view_notifications) ImageView mImageViewNotifications;
  @InjectView(R.id.activity_main_linear_layout_content_image_view_cloud) ImageView mImageViewCloud;
  @InjectView(R.id.activity_main_linear_layout_bottom_linear_layout_reminders_image_view_reminders) ImageView mImageViewReminders;
  @InjectView(R.id.activity_main_linear_layout_bottom_linear_layout_messages_image_view_messages) ImageView mImageViewMessages;
  @InjectView(R.id.activity_main_linear_layout_bottom_linear_layout_toggles_image_view_toggles) ImageView mImageViewToggles;
  @InjectView(R.id.activity_main_linear_layout_content_text_view_temperature) TextView mTextViewTemperature;
  @InjectView(R.id.activity_main_linear_layout_content_text_view_location) TextView mTextViewLocation;
  @InjectView(R.id.activity_main_linear_layout_content_text_view_datetime) TextView mTextViewDatetime;
  @InjectView(R.id.activity_main_linear_layout_bottom_linear_layout_reminders_text_view_reminders) TextView mTextViewReminders;
  @InjectView(R.id.activity_main_linear_layout_bottom_linear_layout_messages_text_view_messages) TextView mTextViewMessages;
  @InjectView(R.id.activity_main_linear_layout_bottom_linear_layout_toggles_text_view_toggles) TextView mTextViewToggles;
  @InjectView(R.id.activity_main_linear_layout_bottom_linear_layout_reminders) LinearLayout mLinearLayoutReminders;
  @InjectView(R.id.activity_main_linear_layout_bottom_linear_layout_messages) LinearLayout mLinearLayoutMessages;
  @InjectView(R.id.activity_main_linear_layout_bottom_linear_layout_toggles) LinearLayout mLinearLayoutToggles;


  private final static String BACKGROUND_IMAGE = "http://t.wallpaperweb.org/wallpaper/buildings/1280x720/Wallpaper_1080p_36_1280x720.jpg";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    initViews();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.activity_main_relative_layout_top_image_view_settings:
        onSettingsClick();
        break;
      case R.id.activity_main_relative_layout_top_image_view_notifications:
        onNotificationsClick();
        break;
      case R.id.activity_main_linear_layout_bottom_linear_layout_reminders:
        onRemindersClick();
        break;
      case R.id.activity_main_linear_layout_bottom_linear_layout_messages:
        onMessagesClick();
        break;
      case R.id.activity_main_linear_layout_bottom_linear_layout_toggles:
        onTogglesClick();
        break;
    }
  }

  private void initViews() {
    //ImageUtils.loadImage(this, mImageViewBackground, BACKGROUND_IMAGE);
    mImageViewSettings.setOnClickListener(this);
    mImageViewNotifications.setOnClickListener(this);
    mLinearLayoutReminders.setOnClickListener(this);
    mLinearLayoutMessages.setOnClickListener(this);
    mLinearLayoutToggles.setOnClickListener(this);
  }

  private void onSettingsClick() {
    final Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    startActivity(intent);
  }

  private void onNotificationsClick() {
	  Intent notificationsIntent = new Intent(this, NotificationsActivity.class);
	  startActivity(notificationsIntent);
  }

  private void onRemindersClick() {
	  Intent remindersIntent = new Intent(this, RemindersActivity.class);
	  startActivity(remindersIntent);
  }

  private void onMessagesClick() {
	  Intent messagesIntent = new Intent(this, MessagesActivity.class);
	  startActivity(messagesIntent);
  }

  private void onTogglesClick() {
	  Intent togglesIntent = new Intent(this, TogglesActivity.class);
	  startActivity(togglesIntent);
  }
}
