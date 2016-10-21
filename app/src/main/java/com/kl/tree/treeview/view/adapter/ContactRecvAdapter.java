package com.kl.tree.treeview.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kl.tree.library.RecvHolder;
import com.kl.tree.library.adapter.BaseRecvTreeAdapter;
import com.kl.tree.library.domain.Node;
import com.kl.tree.treeview.R;
import com.kl.tree.treeview.data.module.ContactDir;
import com.kl.tree.treeview.data.module.ContactLeaf;

import java.util.List;

/**
 * Created by kl on 16-10-12.
 */
public class ContactRecvAdapter extends BaseRecvTreeAdapter<ContactDir, ContactLeaf> {

    private Context mContext;

    public ContactRecvAdapter(Context context, List<Node<ContactDir, ContactLeaf>> nodes) {
        super(nodes);
        mContext = context;
        setNodeDeviation(20);
    }


    @Override
    public RecvHolder getLeafHolder(ViewGroup parent, int viewType) {
        return new LeafHolder(LayoutInflater.from(mContext).inflate(R.layout.tree_list_item_leaf, parent,false), viewType);
    }

    @Override
    public RecvHolder getDirHolder(ViewGroup parent, int viewType) {
        return new DirHolder(LayoutInflater.from(mContext).inflate(R.layout.tree_list_item_dir, parent,false), viewType);
    }

    class LeafHolder extends RecvHolder {
        public ImageView ivPhoto;
        public TextView tvName;

        public LeafHolder(View itemView, int viewType) {
            super(itemView, viewType);
            ivPhoto = (ImageView) itemView.findViewById(R.id.id_treenode_icon);
            tvName = (TextView) itemView.findViewById(R.id.id_treenode_label);
        }

        @Override
        public void bindItem(RecvHolder holder, int position) {
            tvName.setText("leaf_"+mNodes.get(position).getLeaf().getName());
        }
    }

    class DirHolder extends RecvHolder {
        public ImageView ivPhoto;
        public TextView tvName;

        public DirHolder(View itemView, int viewType) {
            super(itemView, viewType);
            ivPhoto = (ImageView) itemView.findViewById(R.id.id_treenode_icon);
            tvName = (TextView) itemView.findViewById(R.id.id_treenode_label);
        }

        @Override
        public void bindItem(RecvHolder holder, int position) {
            if (mNodes.get(position).getDir().isExpand()) {
                ivPhoto.setImageResource(R.mipmap.tree_ex);
            } else {
                ivPhoto.setImageResource(R.mipmap.tree_ec);
            }
            tvName.setText("dir_"+mNodes.get(position).getDir().getName());
        }
    }

}
