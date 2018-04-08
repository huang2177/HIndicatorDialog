package com.huangbryant.hindicatordialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.huangbryant.hindicator.HIndicatorBuilder;
import com.huangbryant.hindicator.HIndicatorDialog;
import com.huangbryant.hindicator.listener.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private List<String> mList;
    private HIndicatorDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = Arrays.asList("Java", "C", "C++");
    }

    public void showDialog(View view) {
        dialog = new HIndicatorBuilder(this)
                .width(200)   // the dialog width in px
                .height(-1)  // the dialog max height in px or -1 (means auto fit)
                .arrowDirection(HIndicatorBuilder.TOP)  // the  position of dialog's arrow indicator  (TOP or BOTTOM)
                .bgColor(Color.parseColor("#999898"))  // the bg color of the dialog
                .gravity(HIndicatorBuilder.GRAVITY_LEFT)   // dialog' sgravity (GRAVITY_LEFT or GRAVITY_RIGHT or GRAVITY_CENTER)
                .radius(8) // the radius in dialog
                .arrowRectage(0.2f)  // the arrow's offset Relative to the dialog's width
                .data(mList)
                .clickListener(this)
                .cardElevation(0.5f)
                .enableTouchOutside(false)
                .create();
        dialog.show(view); // or use dialog.show(x,y); to determine the location of dialog
    }

    @Override
    public void OnItemClick(int position) {
        dialog.dismiss();
        Log.e("----", mList.get(position));
    }
}
