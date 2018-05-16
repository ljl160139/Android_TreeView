package com.kl.tree.treeview.data;

import android.util.Log;

import com.kl.tree.treeview.view.CustomerApp;
import com.kl.tree.treeview.helper.FileUtils;

import java.io.IOException;

/**
 * Created by kl on 16-10-10.
 */
public class DBHelper {
    public static final String DB_NAME= "test.db";


    private final String TAG=DBHelper.class.getSimpleName();
    private static DBHelper mDbHelper;


    public synchronized static DBHelper getInstance(){
        if(null==mDbHelper){
            mDbHelper=new DBHelper();
        }
        return mDbHelper;
    }


    /**
     * copy db diles
     */
    public void formeDb(){
        try {
            Log.d(TAG,"formeDb");
            FileUtils.saveFileByIn(CustomerApp.getApplication().getAssets().open(DB_NAME), getDbPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getDbPath(){
        /*String databasePath = "/data/data/";
        databasePath = databasePath + CustomerApp.getApplication().getPackageName() + "/databases/";*/
        String databasePath=CustomerApp.getApplication().getDatabasePath(DB_NAME).getAbsolutePath();
        Log.d(TAG,databasePath);
        return databasePath;
    }
}
