package com.huangbryant.hindicatordialog;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.huangbryant.mylibrary.BaseHIndicatorAdapter;
import com.huangbryant.mylibrary.HIndicatorBuilder;
import com.huangbryant.mylibrary.HIndicatorDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HIndicatorDialog dialog = new HIndicatorBuilder(this)
                .width(200)   // the dialog width in px
                .height(-1)  // the dialog max height in px or -1 (means auto fit)
                .ArrowDirection(HIndicatorBuilder.TOP)  // the  position of dialog's arrow indicator  (TOP or BOTTOM)
                .bgColor(Color.parseColor("#999898"))  // the bg color of the dialog
                .gravity(HIndicatorBuilder.GRAVITY_LEFT)   // dialog' sgravity (GRAVITY_LEFT or GRAVITY_RIGHT or GRAVITY_CENTER)
                .radius(8) // the radius in dialog
                .ArrowRectage(0.2f)  // the arrow's offset Relative to the dialog's width
                .layoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                .dimEnabled(false)
                .adapter(new MyAdapter())
                .create();
        dialog.setCanceledOnTouchOutside(true); // outside cancelable
        dialog.show(spinner); // or use dialog.show(x,y); to determine the location of dialog
    }


    public class MyAdapter extends BaseHIndicatorAdapter {
        @Override
        public void onBindView(BaseViewHolder holder, int position) {
            TextView tv = holder.getView(R.id.item_tv);
            tv.setText(mList.get(position));
            tv.setCompoundDrawablesWithIntrinsicBounds(mICons.get(position), 0, 0, 0);

            if (position == mList.size() - 1) {
                holder.setVisibility(R.id.item_line, BaseViewHolder.GONE);
            } else {
                holder.setVisibility(R.id.item_line, BaseViewHolder.VISIBLE);
            }
        }

        @Override
        public int getLayoutID(int position) {
            return R.layout.hindicator_item_layout;
        }

        @Override
        public boolean clickable() {
            return true;
        }

        @Override
        public void onItemClick(View v, int position) {

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }
}
