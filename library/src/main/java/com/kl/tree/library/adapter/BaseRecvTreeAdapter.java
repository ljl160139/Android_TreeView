package com.kl.tree.library.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.kl.tree.library.RecvHolder;
import com.kl.tree.library.model.BaseDir;
import com.kl.tree.library.model.BaseLeaf;
import com.kl.tree.library.model.Node;

import java.util.List;

/**
 * recyclerview tree  base acapter
 * Created by kl on 16-10-10.
 */
public abstract class BaseRecvTreeAdapter<T extends BaseDir, K extends BaseLeaf> extends RecyclerView.Adapter<RecvHolder> {

    private final String TAG = BaseTreeAdapter.class.getSimpleName();
    protected List<Node<T, K>> mNodes;

    private final int LEAF = 0;
    private final int DIR = 1;
    private int mNodeDeviation = 10;
    private ItemClickListener mItemClickListener;

    public BaseRecvTreeAdapter(List<Node<T, K>> nodes) {
        mNodes = nodes;
    }

    @Override
    public int getItemCount() {
        return mNodes.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (mNodes.get(position).isLeaf()) {
            return LEAF;
        }
        return DIR;
    }


    @Override
    public RecvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (DIR == viewType) {
            return getDirHolder(parent, viewType);
        } else if (LEAF == viewType) {
            return getLeafHolder(parent, viewType);
        } else {
            throw new RuntimeException("recyclerview incorrect itemtype_" + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecvHolder holder, final int position) {
        final Node node = mNodes.get(position);
        holder.itemView.setPadding(node.getLevel() * DpHelper.dp2px(holder.itemView.getContext(), mNodeDeviation), holder.itemView.getPaddingTop(), holder.itemView.getPaddingRight(), holder.itemView.getPaddingBottom());
        if (DIR == getItemViewType(position)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mItemClickListener) {
                        mItemClickListener.onDirClick(position, node);
                    }
                }
            });
        } else if (LEAF == getItemViewType(position)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mItemClickListener) {
                        mItemClickListener.onLeafClick(position, node);
                    }
                }
            });
        } else {
            throw new RuntimeException("recyclerview incorrect itemtype_" + getItemViewType(position));
        }
        holder.bindItem(holder, position);
    }


    /**
     * add child newNode to pNode
     *
     * @param newNode
     * @param pNode
     */
    public void addNode(Node newNode, Node<T, K> pNode) {
        if (pNode.isLeaf()) {
            Log.d(TAG, "parent node connot be leaf");
            return;
        }
        for (int i = 0; i < mNodes.size(); i++) {
            if (mNodes.get(i) == pNode) {
                mNodes.add(i + 1, newNode);
                break;
            }
        }
        pNode.getDir().open();
        notifyDataSetChanged();
    }

    /**
     * add child newNodes to pNode
     *
     * @param nodes
     * @param pNode
     */
    public void addNodes(List<Node<T, K>> nodes, Node<T, K> pNode) {
        if (pNode.isLeaf()) {
            Log.d(TAG, "parent node connot be leaf");
            return;
        }
        for (int i = 0; i < mNodes.size(); i++) {
            if (mNodes.get(i) == pNode) {
                mNodes.addAll(i + 1, nodes);
                break;
            }
        }
        pNode.getDir().open();
        notifyDataSetChanged();
    }

    /**
     * close node
     *
     * @param node
     */
    public void close(Node<T, K> node) {
        closeDir(node);
        node.getDir().close();
        notifyDataSetChanged();

    }


    /**
     * Recursive closed node
     *
     * @param node
     */
    private void closeDir(Node<T, K> node) {
        if (node.isLeaf()) {
            Log.d(TAG, "close node connot be leaf");
            return;
        }
        if (node.getDir().isExpand()) {
            for (int i = 0; i < mNodes.size(); i++) {
                if (mNodes.get(i).getParentNode() == node) {
                    if (mNodes.get(i).isLeaf()) {
                        mNodes.remove(i);
                        i--;
                    } else {
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

    public abstract RecvHolder getLeafHolder(ViewGroup parent, int viewType);

    public abstract RecvHolder getDirHolder(ViewGroup parent, int viewType);


    public void registListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface ItemClickListener<T extends BaseDir, K extends BaseLeaf> {
        public void onDirClick(int position, Node<T, K> node);

        public void onLeafClick(int position, Node<T, K> node);
    }


}

