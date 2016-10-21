package com.kl.tree.treeview.data;

import android.database.Cursor;

import com.kl.tree.treeview.data.module.ContactDir;
import com.kl.tree.treeview.data.module.ContactLeaf;
import com.kl.tree.treeview.view.CustomerApp;
import com.kl.tree.treeview.data.provider.TestContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kl on 16-10-12.
 */
public class ContactDao {


    private static ContactDao mContactDao;

    public static ContactDao getInstance(){
        if(null==mContactDao){
            mContactDao=new ContactDao();
        }
        return mContactDao;
    }


    public List<ContactLeaf> getContactsByDeptId(int deptId){
        List<ContactLeaf> contactLeafList=new ArrayList<ContactLeaf>();

        Cursor cursor= CustomerApp.getApplication().getContentResolver().query(TestContent.leaf.CONTENT_URI,
                TestContent.leaf.PROJECTION, TestContent.leaf.Columns.SRC_ID.getName()+"=? " ,
                new String[]{deptId+""},"id asc");


        if(null!=cursor){
            int columIndex=cursor.getColumnIndex(TestContent.leaf.Columns.NAME.getName());
            int idIndex=cursor.getColumnIndex(TestContent.leaf.Columns.ID.getName());
            int phoneIndex=cursor.getColumnIndex(TestContent.leaf.Columns.PHONE.getName());
            int srcIdIndex=cursor.getColumnIndex(TestContent.leaf.Columns.SRC_ID.getName());
            int sexIndex=cursor.getColumnIndex(TestContent.leaf.Columns.SEX.getName());

            while (cursor.moveToNext()){
                ContactLeaf contactLeaf=new ContactLeaf(
                        cursor.getInt(idIndex),
                        cursor.getString(columIndex),
                        cursor.getInt(srcIdIndex),
                        cursor.getString(phoneIndex),
                        cursor.getString(sexIndex)
                        );
                contactLeafList.add(contactLeaf);
            }
        }

        return contactLeafList;

    }



    public List<ContactDir> getDirByDeptId(int deptId){
        List<ContactDir> contactDirList=new ArrayList<ContactDir>();

        Cursor cursor=CustomerApp.getApplication().getContentResolver().query(TestContent.src.CONTENT_URI,
                TestContent.src.PROJECTION, TestContent.src.Columns.PSRC_ID.getName()+"=?  " ,
                new String[]{deptId+""},"id asc");


        if(null!=cursor){
            int columIndex=cursor.getColumnIndex(TestContent.src.Columns.NAME.getName());
            int idIndex=cursor.getColumnIndex(TestContent.src.Columns.ID.getName());
            int pSrcIdIndex=cursor.getColumnIndex(TestContent.src.Columns.PSRC_ID.getName());

            while (cursor.moveToNext()){
                ContactDir contactLeaf=new ContactDir(cursor.getInt(idIndex),
                        cursor.getString(columIndex),
                        cursor.getInt(pSrcIdIndex));
                contactDirList.add(contactLeaf);
            }
        }
        return contactDirList;
    }

    public List<ContactDir> getRootDir(){
        List<ContactDir> contactDirList=new ArrayList<ContactDir>();
        Cursor cursor=CustomerApp.getApplication().getContentResolver().query(TestContent.src.CONTENT_URI,
                TestContent.src.PROJECTION, TestContent.src.Columns.PSRC_ID.getName()+"=''"+"  ",
                new String[]{},"id asc");


        if(null!=cursor){
            int columIndex=cursor.getColumnIndex(TestContent.src.Columns.NAME.getName());
            int idIndex=cursor.getColumnIndex(TestContent.src.Columns.ID.getName());
            int pSrcIdIndex=cursor.getColumnIndex(TestContent.src.Columns.PSRC_ID.getName());

            while (cursor.moveToNext()){
                ContactDir contactLeaf=new ContactDir(cursor.getInt(idIndex),
                        cursor.getString(columIndex),
                        cursor.getInt(pSrcIdIndex));
                contactDirList.add(contactLeaf);
            }
        }
        return contactDirList;
    }




}
