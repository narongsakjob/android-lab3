package com.example.loginfirebase;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mTitleView;
    ImageView mImageView;
    ConstraintLayout root;

    public ViewHolder(View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.list_root);
        mTitleView = itemView.findViewById(R.id.rTitleTv);
        mImageView = itemView.findViewById(R.id.rImageView);
    }
}
