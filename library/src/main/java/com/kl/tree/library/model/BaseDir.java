package com.kl.tree.library.model;

/**
 * Created by kl on 16-10-10.
 */
public abstract class BaseDir{
    private boolean isExpand = false;      //dir is expand

    public BaseDir(){
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void close(){
        isExpand=false;
    }

    public void open(){
        isExpand=true;
    }

}
