package com.kl.tree.treeview.data.module;


import com.kl.tree.library.model.BaseLeaf;

/**
 * Created by kl on 16-10-10.
 */
public class ContactLeaf extends BaseLeaf {

    private int id;
    private String name;
    private int srcId;
    private String phone;
    private String sex;

    public ContactLeaf(int id, String name, int srcId, String phone, String sex) {
        this.id = id;
        this.name = name;
        this.srcId = srcId;
        this.phone = phone;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }
}
