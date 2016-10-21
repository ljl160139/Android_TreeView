package com.kl.tree.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kl on 16/10/14.
 */

public abstract class RecvHolder extends RecyclerView.ViewHolder {
    public RecvHolder(View itemView, int viewType) {
        super(itemView);
    }
    abstract public void bindItem(RecvHolder holder, int position);
}