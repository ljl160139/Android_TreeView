package com.kl.tree.library.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.kl.tree.library.model.BaseDir;
import com.kl.tree.library.model.BaseLeaf;
import com.kl.tree.library.model.Node;

import java.util.List;

/**
 * ExpandableListView tree base adapter
 * Created by kl on 16-10-10.
 */
public abstract class BaseTreeExpandableAdapter<T extends BaseDir,K extends BaseLeaf> extends BaseExpandableListAdapter {

    private final int LEAF = 0;
    private final int DIR = 1;

    private final String TAG=BaseTreeAdapter.class.getSimpleName();
    protected List<Node<T,K>> mNodes;
    private int mNodeDeviation =10;

    public BaseTreeExpandableAdapter(List<Node<T, K>> nodes){
        mNodes = nodes;
    }


    @Override
    public int getGroupType(int groupPosition) {
        if (mNodes.get(groupPosition).isLeaf()) {
            return LEAF;
        }
        return DIR;
    }

    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    @Override
    public int getGroupCount() {
        return mNodes.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(getGroupType(i)==LEAF){
            return 1;
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return mNodes.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return mNodes.get(i).getLeaf();
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Node node=mNodes.get(i);
        if(getGroupType(i)==LEAF){
            view=getLeafView(i, view);
        }else {
            view=getDirView(i, view);
        }
        view.setPadding(node.getLevel() *DpHelper.dp2px(view.getContext(),mNodeDeviation),  view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        return view;
    }

    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        Node node=mNodes.get(i);
        if(getGroupType(i)==LEAF){
            view=getLeafChildView(i, view);
        }
        view.setPadding(node.getLevel() *DpHelper.dp2px(view.getContext(),mNodeDeviation),  view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        return view;
    }



    /**
     * add child newNode to pNode
     * @param newNode
     * @param pNode
     */
    public void addNode(Node newNode,Node<T,K> pNode){
        if(pNode.isLeaf()){
            Log.d(TAG,"parent node connot be leaf");
            return;
        }
        for(int i=0;i<mNodes.size();i++){
            if(mNodes.get(i)==pNode){
                mNodes.add(i+1,newNode);
                break;
            }
        }
        pNode.getDir().open();
        notifyDataSetChanged();
    }

    /**
     *add child newNodes to pNode
     * @param nodes
     * @param pNode
     */
    public void addNodes(List<Node<T,K>> nodes,Node<T,K> pNode){
        if(pNode.isLeaf()){
            Log.d(TAG,"parent node connot be leaf");
            return;
        }
        for(int i=0;i<mNodes.size();i++){
            if(mNodes.get(i)==pNode){
                mNodes.addAll(i + 1, nodes);
                break;
            }
        }
        pNode.getDir().open();
        notifyDataSetChanged();
    }

    /**
     * close node
     * @param node
     */
    public void close(Node<T,K> node){
        closeDir(node);
        node.getDir().close();
        notifyDataSetChanged();

    }


    /**
     * Recursive closed node
     * @param node
     */
    private void closeDir(Node<T,K> node){
        if(node.isLeaf()){
            Log.d(TAG,"close node connot be leaf");
            return;
        }
        if(node.getDir().isExpand()){
            for(int i=0;i<mNodes.size();i++){
                if(mNodes.get(i).getParentNode()==node){
                    if(mNodes.get(i).isLeaf()){
                        mNodes.remove(i);
                        i--;
                    }
                    else{
                        closeDir(mNodes.get(i));
                        mNodes.remove(i);
                        i--;
                    }
                }
            }
        }
    }

    public void setNodeDeviation(int nodeDeviation) {
        this.mNodeDeviation = nodeDeviation;
    }

    public abstract View getLeafChildView(int groupPosition,View view);

    public abstract View getLeafView(int groupPosition,View view);

    public abstract View getDirView(int groupPosition,View view);

}
