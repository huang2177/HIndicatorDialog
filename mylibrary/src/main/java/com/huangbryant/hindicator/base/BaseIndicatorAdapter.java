package com.huangbryant.hindicator.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者:huangshuang
 * 事件 16/8/29.
 * 邮箱： 15378412400@163.com
 */
public abstract class BaseIndicatorAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, position);
            }
        });

        onBindView(holder, holder.getLayoutPosition());

    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutID(position);
    }

    public abstract int getLayoutID(int position);

    public abstract void onItemClick(View v, int position);

    public abstract void onBindView(BaseViewHolder holder, int position);
}