package com.huangbryant.hindicator;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.huangbryant.hindicator.base.BaseIndicatorAdapter;
import com.huangbryant.hindicator.base.BaseViewHolder;
import com.huangbryant.hindicator.listener.OnItemClickListener;

import java.util.List;

/**
 * @author Huangshuang  2018/4/8 0008
 * @describtion 提供默认的数据类型，若是需要其他的数据类型，则需要实现BaseIndicatorAdapter自己实现
 */

public class HIndicatorAdapter extends BaseIndicatorAdapter {

    private List<String> list;
    private Context context;
    private OnItemClickListener listener;

    public HIndicatorAdapter(List<String> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getLayoutID(int position) {
        return R.layout.hindicator_item_layout;
    }

    @Override
    public void onItemClick(View v, int position) {
        if (listener != null) {
            listener.OnItemClick(position);
        }
    }

    @Override
    public void onBindView(BaseViewHolder holder, int position) {
        TextView tv = holder.getView(R.id.item_tv);
        tv.setText(list.get(position));

        //使用该方法来实现item分割线
        if (position == list.size() - 1) {
            holder.setVisibility(R.id.item_line, BaseViewHolder.GONE);
        } else {
            holder.setVisibility(R.id.item_line, BaseViewHolder.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}
