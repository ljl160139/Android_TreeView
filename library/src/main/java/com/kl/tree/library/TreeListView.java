package com.kl.tree.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kl.tree.library.adapter.BaseTreeAdapter;
import com.kl.tree.library.domain.BaseDir;
import com.kl.tree.library.domain.BaseLeaf;
import com.kl.tree.library.domain.Node;


/**
 * Listview tree
 * Created by kl on 16-10-10.
 */
public class TreeListView extends ListView {

    private ItemClickListener mItemClickListener;
    public TreeListView(Context context) {
        super(context);
    }

    public TreeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TreeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * Override setOnItemClickListener
     * @param listener
     */
    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        OnItemClickListener baseListener=new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(getAdapter() instanceof BaseTreeAdapter &&null!=mItemClickListener){
                    BaseTreeAdapter adapter= (BaseTreeAdapter) getAdapter();
                    Node node= (Node) adapter.getItem(i);
                    if(!node.isLeaf()){
                        mItemClickListener.onDirClick(i,node);
                    }else{
                        mItemClickListener.onLeafClick(i,node);
                    }
                }
            }
        };
        super.setOnItemClickListener(baseListener);
    }


    /**
     * regist lestener
     * @param listener
     */
   public void registListener(ItemClickListener listener){
       mItemClickListener=listener;
       setOnItemClickListener(null);
   }


    /**
     * interface listener
     * @param <T>
     * @param <K>
     */
    public interface ItemClickListener<T extends BaseDir,K extends BaseLeaf>{
        public void onDirClick(int position, Node<T, K> node);

        public void onLeafClick(int position, Node<T, K> node);
    }

}
