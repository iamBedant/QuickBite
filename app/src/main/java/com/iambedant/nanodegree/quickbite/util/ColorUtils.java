
package com.iambedant.nanodegree.quickbite.util;
import android.graphics.Color;
public class ColorUtils {

    private ColorUtils() { }

    public static int getDarkerColor(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, (int) (red * 0.9), (int) (green * 0.9), (int) (blue * 0.9));
    }

}
