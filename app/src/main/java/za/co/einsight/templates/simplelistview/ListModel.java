package za.co.einsight.templates.simplelistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface ListModel {
    String getMainText();
    String getSubText();
    Drawable getImage(Context context);
}
