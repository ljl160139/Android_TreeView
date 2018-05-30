package com.kl.tree.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

import com.kl.tree.library.adapter.BaseTreeExpandableAdapter;
import com.kl.tree.library.model.BaseDir;
import com.kl.tree.library.model.BaseLeaf;
import com.kl.tree.library.model.Node;


/**
 * ExpandableListView  tree
 * Created by kl on 16-10-10.
 */
public class TreeExpandableListView extends ExpandableListView {

    private ItemClickListener mItemClickListener;
    public TreeExpandableListView(Context context) {
        super(context);
    }

    public TreeExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TreeExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        OnChildClickListener childClickListener=new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long l) {
                if(getExpandableListAdapter() instanceof BaseTreeExpandableAdapter &&null!=mItemClickListener){
                    BaseTreeExpandableAdapter adapter= (BaseTreeExpandableAdapter) getExpandableListAdapter();
                    Node node= (Node) adapter.getGroup(i);
                    if(node.isLeaf()){
                        mItemClickListener.onLeafChildClick(i, node);
                        return true;
                    }
                }
                return true;
            }
        };
        super.setOnChildClickListener(childClickListener);
    }

    @Override
    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        OnGroupClickListener groupClickListener=new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if(getExpandableListAdapter() instanceof BaseTreeExpandableAdapter&&null!=mItemClickListener){
                    BaseTreeExpandableAdapter adapter= (BaseTreeExpandableAdapter) getExpandableListAdapter();
                    Node node= (Node) adapter.getGroup(i);
                    if(!node.isLeaf()){
                        mItemClickListener.onDirClick(i, node);
                        for (int m = 0; m < adapter.getGroupCount(); m++) {
                            collapseGroup(m);
                        }
                        return true;
                    }else{
                        mItemClickListener.onLeafClick(i, node);
                        return false;
                    }
                }
                return false;
            }
        };
        super.setOnGroupClickListener(groupClickListener);
    }

    /**
     * regist lestener
     * @param listener
     */
   public void registListener(ItemClickListener listener){
       mItemClickListener=listener;
       setOnChildClickListener(null);
       setOnGroupClickListener(null);
   }


    /**
     * interface listener
     * @param <T>
     * @param <K>
     */
    public interface ItemClickListener<T extends BaseDir,K extends BaseLeaf>{
        public void onDirClick(int position, Node<T, K> node);

        public void onLeafClick(int position, Node<T, K> node);

        public void onLeafChildClick(int position, Node<T, K> node);
    }

}
