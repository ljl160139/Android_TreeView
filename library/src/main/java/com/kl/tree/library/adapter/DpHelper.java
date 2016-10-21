package com.kl.tree.library.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by kl on 2016/10/19.
 */

public class DpHelper {

    /**
     * dp to px
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return (int) (dp * dm.density + 0.5f);
    }

}
