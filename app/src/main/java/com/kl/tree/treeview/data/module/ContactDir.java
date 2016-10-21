package com.kl.tree.treeview.data.module;


import com.kl.tree.library.domain.BaseDir;

/**
 * Created by kl on 16-10-10.
 */
public class ContactDir extends BaseDir {

    private int id;
    private int psrcId;
    private String name;

    public ContactDir(int id, String name, int psrcId) {
        this.id = id;
        this.name = name;
        this.psrcId = psrcId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPsrcId() {
        return psrcId;
    }

    public void setPsrcId(int psrcId) {
        this.psrcId = psrcId;
    }
}
