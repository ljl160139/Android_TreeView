package com.kl.tree.treeview.helper;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by kl on 16-10-10.
 */
public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();


    public static void saveFileByIn(InputStream ins, String dir, String name) {
        String path;
        if (dir.endsWith("/")) {
            path = dir + name;
        } else {
            path = dir + "/" + name;
        }
        saveFileByIn(ins, path, 100 * 1024);
    }


    public static void saveFileByIn(InputStream ins, String path, int byteLength) {
        createDirIfNotExist(new File(path).getParentFile().getAbsolutePath());
        try {
            OutputStream os = null;
            os = new FileOutputStream(new File(path));
            int bytesRead = 0;
            byte[] buffer = new byte[byteLength];
            while ((bytesRead = ins.read(buffer, 0, byteLength)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            os.close();
            ins.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

    }


    public static void createDirIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}
