package com.huangbryant.hindicator;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.huangbryant.hindicator.drawable.BaseDrawable;
import com.huangbryant.hindicator.drawable.TriangleDrawable;
import com.huangbryant.hindicator.listener.OnDismissListener;
import com.huangbryant.hindicator.utils.Utils;

/**
 * 作者:huangshuang
 * 事件 2017/5/18.
 * 邮箱： 15378412400@163.com
 */

public class HIndicatorDialog {

    private static final String TAG = "HIndicatorDialog";
    public static float ARROW_RECTAGE = 0.1f;
    int gravity = Gravity.TOP | Gravity.LEFT;
    /**
     * 控件
     */
    private Activity mContext;
    private Dialog mDialog;
    private HIndicatorBuilder mBuilder;
    private RecyclerView recyclerView;
    private LinearLayout rootLayout;
    private LinearLayout childLayout;
    private View mArrow;
    private CardView mCardView;
    private View mShowView;
    /**
     * 变量
     */
    private int mArrowWidth;
    private int mWidth;
    private int mResultHeight;


    public HIndicatorDialog(Activity context, HIndicatorBuilder builder) {
        this.mContext = context;
        this.mBuilder = builder;
        if (mBuilder.mArrowWidth <= 0) {
            this.mArrowWidth = (int) (mBuilder.width * ARROW_RECTAGE);
        } else {
            this.mArrowWidth = mBuilder.mArrowWidth;
        }
        initDialog();
    }

    private void initDialog() {
        mDialog = new Dialog(mContext, R.style.H_Dialog_Style_Dim_enable);

        if (mDialog.getWindow() != null) {
            mDialog.getWindow().setDimAmount(mBuilder.alpha);
        }

        rootLayout = new LinearLayout(mContext);
        if (mBuilder.arrowdirection == HIndicatorBuilder.TOP || mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM) {
            rootLayout.setOrientation(LinearLayout.VERTICAL);
            this.mWidth = mBuilder.width;
        } else {
            rootLayout.setOrientation(LinearLayout.HORIZONTAL);
            this.mWidth = mBuilder.width + mArrowWidth;
        }
        ViewGroup.LayoutParams rootParam = new ViewGroup.LayoutParams(mWidth,
                mBuilder.height <= 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mBuilder.height);
        rootLayout.setLayoutParams(rootParam);
        if (mBuilder.arrowdirection == HIndicatorBuilder.TOP || mBuilder.arrowdirection == HIndicatorBuilder.LEFT) {
            addArrow2LinearLayout();
        }

        addRecyclerView2Layout();

        if (mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM || mBuilder.arrowdirection == HIndicatorBuilder.RIGHT) {
            addArrow2LinearLayout();
        }

        mDialog.setContentView(rootLayout);

        setSize2Dialog(mBuilder.height);

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                if (mBuilder.mOnDismissListener != null) {
                    mBuilder.mOnDismissListener.onDismiss(mDialog);
                }
            }
        });

        mDialog.setCanceledOnTouchOutside(mBuilder.enableTouchOutside);
    }

    private void addArrow2LinearLayout() {
        mArrow = new View(mContext);
        rootLayout.addView(mArrow);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mArrow.getLayoutParams();
        layoutParams.width = mArrowWidth;
        layoutParams.height = mArrowWidth;
        mArrow.setLayoutParams(layoutParams);
    }

    /**
     * modify recyclerview state
     */
    private void addRecyclerView2Layout() {
        childLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.hindicator_dialog_layout, rootLayout, true);
        mCardView = (CardView) childLayout.findViewById(R.id.h_dialog_cv);

        mCardView.setCardBackgroundColor(mBuilder.bgColor);
        mCardView.setRadius(mBuilder.radius);
        mCardView.setCardElevation(mBuilder.cardElevation);
        recyclerView = (RecyclerView) childLayout.findViewById(R.id.h_dialog_rv);
        CardView.LayoutParams layoutParams = (CardView.LayoutParams) recyclerView.getLayoutParams();
        int width = mBuilder.width;
        if (mBuilder.arrowdirection == HIndicatorBuilder.RIGHT) {
            width -= mArrowWidth;
        }
        layoutParams.width = width;
        recyclerView.setLayoutParams(layoutParams);

        recyclerView.setBackgroundColor(mBuilder.bgColor);


        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int recyclerviewHeight = recyclerView.getHeight();
                resizeHeight(recyclerviewHeight);
            }
        });

    }

    private void setSize2Dialog(int height) {
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        if (mBuilder.animator != 0) {
            dialogWindow.setWindowAnimations(mBuilder.animator);
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (mBuilder.gravity == HIndicatorBuilder.GRAVITY_RIGHT) {
            gravity = Gravity.RIGHT | (mBuilder.arrowdirection != HIndicatorBuilder.BOTTOM ? Gravity.TOP : Gravity.BOTTOM);
        } else if (mBuilder.gravity == HIndicatorBuilder.GRAVITY_LEFT) {
            gravity = Gravity.LEFT | (mBuilder.arrowdirection != HIndicatorBuilder.BOTTOM ? Gravity.TOP : Gravity.BOTTOM);
        } else {
            gravity = Gravity.CENTER_HORIZONTAL | (mBuilder.arrowdirection != HIndicatorBuilder.BOTTOM ? Gravity.TOP : Gravity.BOTTOM);
        }
        dialogWindow.setGravity(gravity);
        lp.width = mBuilder.width; // 宽度
        lp.height = height; // 高度
        dialogWindow.setAttributes(lp);
    }

    private void resizeHeight(int recyclerviewHeight) {

        int arrowHeght = mBuilder.arrowdirection == HIndicatorBuilder.TOP || mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM ? mArrowWidth : 0;
        int calcuteResult = recyclerviewHeight + arrowHeght;
        int recyc_height = recyclerviewHeight;
        if (mBuilder.height <= 0 || calcuteResult < mBuilder.height) {
            mResultHeight = calcuteResult;
        } else {
            mResultHeight = mBuilder.height;
            if (mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM) {
                recyc_height = mResultHeight - mArrowWidth;
            }
        }

        ViewGroup.LayoutParams params = rootLayout.getLayoutParams();


        //重新获取屏幕能接受的最大高度，如果当前高度超过屏幕能接受的恩最大高度，则设置为屏幕能接受的高度
        int canUseHeight = 0;
        if (mShowView != null) {
            int[] location = new int[2];
            mShowView.getLocationInWindow(location);

            int windowHeight = Utils.getLocationInWindow(mContext)[1];
            if (mBuilder.arrowdirection == HIndicatorBuilder.TOP) {
                canUseHeight = windowHeight - location[1] - mShowView.getHeight();
            } else if (mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM) {
                canUseHeight = location[1] - Utils.getStatusBarHeight(mContext);
            } else {
                canUseHeight = Utils.getLocationInContent(mContext)[1];
            }
        }

        if (mResultHeight > canUseHeight) {
            mResultHeight = canUseHeight;
            if (mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM) {
                recyc_height = mResultHeight - mArrowWidth;
            }
        }
        params.height = mResultHeight;
        rootLayout.setLayoutParams(params);


        if (mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM) {
            ViewGroup.LayoutParams recyc_layoutParam = recyclerView.getLayoutParams();
            recyc_layoutParam.height = recyc_height;
            recyclerView.setLayoutParams(recyc_layoutParam);
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mArrow.getLayoutParams();
        if (mBuilder.arrowdirection == HIndicatorBuilder.TOP) {
            layoutParams.leftMargin = (int) (mBuilder.width * mBuilder.arrowPercentage) - mArrowWidth / 2;
        } else if (mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM) {
            layoutParams.leftMargin = (int) (mBuilder.width * mBuilder.arrowPercentage) - mArrowWidth / 2;
        } else {
            layoutParams.topMargin = (int) (mResultHeight * mBuilder.arrowPercentage) - mArrowWidth / 2;
            Window dialogWindow = mDialog.getWindow();
            dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.y = lp.y - ((int) (mResultHeight * mBuilder.arrowPercentage));
            dialogWindow.setAttributes(lp);
        }
        mArrow.setLayoutParams(layoutParams);
        BaseDrawable arrowDrawable;
        if (mBuilder.mArrowDrawable == null) {
            arrowDrawable = new TriangleDrawable(mBuilder.arrowdirection, mBuilder.bgColor);
        } else {
            arrowDrawable = mBuilder.mArrowDrawable;
        }
        arrowDrawable.setBounds(mArrow.getLeft(), mArrow.getTop(), mArrow.getRight(), mArrow.getBottom());
        mArrow.setBackgroundDrawable(arrowDrawable);
        rootLayout.requestLayout();
        setSize2Dialog(mResultHeight);

    }

    public static HIndicatorDialog newInstance(Activity context, HIndicatorBuilder builder) {
        HIndicatorDialog dialog = new HIndicatorDialog(context, builder);
        return dialog;
    }

    public void show(View view) {
        mShowView = view;
        int x = 0;
        int y = 0;
        if (mBuilder.arrowdirection == HIndicatorBuilder.TOP || mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM) {
            if (mBuilder.gravity == HIndicatorBuilder.GRAVITY_LEFT) {
                x = -1 * (int) (mBuilder.width * mBuilder.arrowPercentage) + view.getWidth() / 2;
            } else if (mBuilder.gravity == HIndicatorBuilder.GRAVITY_RIGHT) {
                x = -1 * (mBuilder.width - (int) (mBuilder.width * mBuilder.arrowPercentage)) + view.getWidth() / 2;
            }
        } else {
            if (mBuilder.gravity == HIndicatorBuilder.GRAVITY_LEFT) {
                x = view.getWidth() - mArrowWidth / 2;
            } else if (mBuilder.gravity == HIndicatorBuilder.GRAVITY_RIGHT) {
                x = view.getWidth() - mArrowWidth / 2;
            }

        }
        show(view, x, y);
    }

    public void show(View view, int xOffset, int yOffset) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int height = Utils.getLocationInWindow(mContext)[1];
        int width = Utils.getLocationInWindow(mContext)[0];

        int x;
        int y;
        if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
            x = width - location[0] - view.getWidth();
        } else if ((gravity & Gravity.LEFT) == Gravity.LEFT) {
            x = location[0];
        } else {
            x = 0;
        }
        x += xOffset;
        if (x < 0) {
            x = 0;
        }
        if (mBuilder.arrowdirection == HIndicatorBuilder.BOTTOM) {
            y = height - location[1] + Utils.getNavigationBarHeight(mContext) - mArrowWidth / 2;
        } else if (mBuilder.arrowdirection == HIndicatorBuilder.TOP) {
            y = location[1] + view.getHeight() - mArrowWidth / 2;
        } else {
            y = location[1] + view.getHeight() / 2;
        }
        y += yOffset;
        if (y < 0) {
            y = 0;
        }

        show(x, y);

    }

    public void show(int x, int y) {
        recyclerView.setLayoutManager(mBuilder.mLayoutManager);
        recyclerView.setAdapter(mBuilder.mAdapter);
        setDialogPosition(x, y);
        mDialog.show();
    }

    private void setDialogPosition(int x, int y) {
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = x;
        lp.y = y;
        dialogWindow.setAttributes(lp);

    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public CardView getCardView() {
        return mCardView;
    }

    public View getArrow() {
        return mArrow;
    }
}
