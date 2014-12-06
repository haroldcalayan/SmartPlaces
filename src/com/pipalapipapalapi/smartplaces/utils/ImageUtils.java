package com.pipalapipapalapi.smartplaces.utils;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class ImageUtils {

  public static void loadImage(Context context, ImageView imageView, String url) {
    Picasso.with(context).load(url).into(imageView);
  }
}
