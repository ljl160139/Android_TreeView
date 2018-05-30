package com.kl.tree.library.model;

/**
 * node contain leaf and dir,leaf is below dir
 * Created by kl on 16-10-10.
 */
public class Node<T extends BaseDir,K extends BaseLeaf> {

    private T dir;        //dir node
    private K leaf;      //leaf node
    private int level=0;        //tree level
    private Node parentNode;      //parent node


    /**
     * root dir object
     * @param dir
     */
    public Node(T dir){
        this.dir=dir;
        level=0;
    }


    /**
     * leaf node
     * @param leaf
     * @param parentNode
     */
    public Node(K leaf,Node parentNode){
        this.leaf=leaf;
        this.parentNode=parentNode;
        if(null==parentNode){
            level=0;
        }else{
            level=parentNode.getLevel()+1;
        }
    }

    /**
     * dir node
     * @param dir
     * @param parentNode
     */
    public Node(T dir,Node parentNode){
        this.dir=dir;
        this.parentNode=parentNode;

        if(null==parentNode){
            level=0;
        }else{
            level=parentNode.getLevel()+1;
        }
    }




    public T getDir() {
        return dir;
    }

    public void setDir(T dir) {
        this.dir = dir;
    }

    public K getLeaf() {
        return leaf;
    }

    public void setLeaf(K leaf) {
        this.leaf = leaf;
    }


    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }




    /**
     * get level
     * @return
     */
    public int getLevel() {
        return level;
    }


    /**
     * is leaf
     * @return
     */
    public boolean isLeaf() {
        return null!=leaf;
    }


    /**
     * is root node
     * @return
     */
    public boolean isRoot() {
        return null==parentNode;
    }
}
