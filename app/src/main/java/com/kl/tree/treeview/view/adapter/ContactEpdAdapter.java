package com.kl.tree.treeview.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kl.tree.library.adapter.BaseTreeExpandableAdapter;
import com.kl.tree.library.domain.Node;
import com.kl.tree.treeview.data.module.ContactDir;
import com.kl.tree.treeview.data.module.ContactLeaf;
import com.kl.tree.treeview.R;

import java.util.List;

/**
 * Created by kl on 16-10-12.
 */
public class ContactEpdAdapter extends BaseTreeExpandableAdapter<ContactDir,ContactLeaf> {

    private Context mContext;

    public ContactEpdAdapter(Context context, List<Node<ContactDir, ContactLeaf>> nodes) {
        super(nodes);
        mContext=context;
        setNodeDeviation(20);
    }


    @Override
    public View getLeafChildView(int position, View view) {
        Node<ContactDir,ContactLeaf> node=mNodes.get(position);
        ViewHolder holder;
        if(null==view){
            view = LayoutInflater.from(mContext).inflate(R.layout.tree_list_item_leaf_child, null);
            holder = new ViewHolder();
            holder.tvPhone = (TextView) view
                    .findViewById(R.id.tv_phone);
            holder.tvSex = (TextView) view
                    .findViewById(R.id.tv_sex);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tvPhone.setText(node.getLeaf().getPhone());
        holder.tvSex.setText(node.getLeaf().getSex());
        return view;
    }

    @Override
    public View getLeafView(int position, View view) {
        Node<ContactDir,ContactLeaf> node=mNodes.get(position);
        ViewHolder holder;
        if(null==view){
            view = LayoutInflater.from(mContext).inflate(R.layout.tree_list_item_leaf, null);
            holder = new ViewHolder();
            holder.ivPhoto = (ImageView) view
                    .findViewById(R.id.id_treenode_icon);
            holder.tvName = (TextView) view
                    .findViewById(R.id.id_treenode_label);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tvName.setText("leaf_"+node.getLeaf().getName());

        return view;
    }

    @Override
    public View getDirView(int position, View view) {
        Node<ContactDir,ContactLeaf> node=mNodes.get(position);
        ViewHolder holder;
        if(null==view){
            view = LayoutInflater.from(mContext).inflate(R.layout.tree_list_item_dir, null);
            holder = new ViewHolder();
            holder.ivPhoto = (ImageView) view
                    .findViewById(R.id.id_treenode_icon);
            holder.tvName = (TextView) view
                    .findViewById(R.id.id_treenode_label);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }

        if(node.getDir().isExpand()){
            holder.ivPhoto.setImageResource(R.mipmap.tree_ex);
        }else{
            holder.ivPhoto.setImageResource(R.mipmap.tree_ec);
        }

        holder.tvName.setText("dir_"+node.getDir().getName());
        return view;
    }


    private final class ViewHolder
    {
        ImageView ivPhoto;
        TextView tvName;
        TextView tvPhone;
        TextView tvSex;
    }
}
