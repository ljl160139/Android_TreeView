package com.kl.tree.library.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kl.tree.library.model.BaseDir;
import com.kl.tree.library.model.BaseLeaf;
import com.kl.tree.library.model.Node;

import java.util.List;

/**
 * listview tree base adapter
 * Created by kl on 16-10-10.
 */
public abstract class BaseTreeAdapter<T extends BaseDir,K extends BaseLeaf> extends BaseAdapter {

    private final String TAG=BaseTreeAdapter.class.getSimpleName();
    protected List<Node<T,K>> mNodes;

    private final int LEAF = 0;
    private final int DIR = 1;
    private int mNodeDeviation =10;

    public BaseTreeAdapter(List<Node<T,K>> nodes) {
        mNodes = nodes;
    }


    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Object getItem(int i) {
        return mNodes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (mNodes.get(position).isLeaf()) {
            return LEAF;
        }
        return DIR;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Node node=mNodes.get(i);
        if(getItemViewType(i)==LEAF){
            view=getLeafView(i,view);
        }else {
            view=getDirView(i,view);
        }
        view.setPadding(node.getLevel()*DpHelper.dp2px(view.getContext(),mNodeDeviation),  view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        return view;
    }


    /**
     * add newNode to parent Node
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
     *add child newNodes to parent Node
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

    public abstract View getLeafView(int position,View view);

    public abstract View getDirView(int position,View view);
}
