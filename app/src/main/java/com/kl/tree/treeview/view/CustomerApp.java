package com.kl.tree.treeview.view;

import android.app.Application;

/**
 * Created by kl on 16-10-12.
 */

public class CustomerApp extends Application {

    private static Application mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
    }

    public static Application getApplication(){
        return mApp;
    }
}
