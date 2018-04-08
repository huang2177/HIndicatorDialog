package com.huangbryant.hindicator;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huangbryant.hindicator.drawable.BaseDrawable;
import com.huangbryant.hindicator.listener.OnDismissListener;
import com.huangbryant.hindicator.listener.OnItemClickListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * 作者:huangshuang
 * 事件 2017/5/18.
 * 邮箱： 15378412400@163.com
 */

public class HIndicatorBuilder {
    public static final int TOP = 12;
    public static final int BOTTOM = 13;
    public static final int LEFT = 14;
    public static final int RIGHT = 15;

    public static final int GRAVITY_LEFT = 688;
    public static final int GRAVITY_RIGHT = 689;
    public static final int GRAVITY_CENTER = 670;

    protected int width;
    protected int height;
    protected int radius = 8;
    protected int bgColor = Color.WHITE;
    protected int mArrowWidth;
    /**
     * 箭头的位置
     */
    protected float arrowPercentage;
    protected int arrowdirection = TOP;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.Adapter mAdapter;
    protected int gravity = GRAVITY_LEFT;
    protected int animator;
    protected BaseDrawable mArrowDrawable;
    protected float alpha = 0f;
    protected float cardElevation = 0f;
    protected OnDismissListener mOnDismissListener;
    protected boolean enableTouchOutside;
    private Activity mContext;
    /**
     * 提供默认的数据类型，若是需要其他的数据类型，则需要实现BaseIndicatorAdapter自己实现
     */
    private List<String> mData;
    private OnItemClickListener mClickListener;

    public HIndicatorBuilder(Activity context) {
        this.mContext = context;
    }

    /**
     * 对话框宽度
     *
     * @param width px
     * @return
     */
    public HIndicatorBuilder width(int width) {
        this.width = width;
        return this;
    }

    /**
     * 对话框高度， -1则自动适配，否则如果内容真实的高度大于指定的高度，则使用指定的高度，否则使用真实的高度
     *
     * @param height 高度，单位px
     * @return
     */
    public HIndicatorBuilder height(int height) {
        this.height = height;
        return this;
    }

    /**
     * 对话框背景颜色
     *
     * @param color
     * @return
     */
    public HIndicatorBuilder bgColor(int color) {
        this.bgColor = color;
        return this;

    }

    /**
     * dialog的圆角度数 必须 >= 0
     *
     * @param radius 四周圆角度数
     * @return
     */
    public HIndicatorBuilder radius(int radius) {
        if (radius < 0) {
            new Exception("radius must >=0");
        }
        this.radius = radius;
        return this;
    }

    /**
     * 为dialog添加进入退出动画
     *
     * @param animator
     * @return
     */
    public HIndicatorBuilder animator(@StyleRes int animator) {
        this.animator = animator;
        return this;
    }

    /**
     * 三角箭头的宽高，因为他是正方形的。
     * 如果不填则默认是用 {@IndicatorDialog.ARROW_RECTAGE} 这个属性，把Dialog的width属性取出来 mBuilder.width * ARROW_RECTAGE 来获取这个箭头的宽度
     * 单位px
     * 当然，你也可以修改{@IndicatorDialog.ARROW_RECTAGE} 这个属性，但是注意： 它是static的，所以会对全局起效。
     *
     * @param width 箭头的宽高
     * @return
     */
    public HIndicatorBuilder arrowWidth(int width) {
        this.mArrowWidth = width;
        return this;
    }

    /**
     * 箭头的drawable，你可以通过继承BaseDrawable来实现自定义箭头的样式，默认会将箭头的高度/2往view方向偏移。
     * 不填则是用默认的三角箭头
     *
     * @param drawable
     * @return
     */
    public HIndicatorBuilder arrowDrawable(BaseDrawable drawable) {
        this.mArrowDrawable = drawable;
        return this;
    }

    /**
     * 箭头的位置，如果是上下方向，则 view的位置为 width*rectage,如果是左右方向，则 view的位置为 {@IndicatorDialog.mResultHeight} *rectage
     *
     * @param rectage
     * @return
     */
    public HIndicatorBuilder arrowRectage(float rectage) {
        if (rectage > 1 || rectage < 0) {
            new Exception("rectage must be 0 <= rectage <= 1");
        }
        this.arrowPercentage = rectage;
        return this;
    }

    /**
     * 箭头方向
     *
     * @param direction
     * @return
     */
    public HIndicatorBuilder arrowDirection(@ARROWDIRECTION int direction) {
        this.arrowdirection = direction;
        return this;
    }


    /**
     * //设置背景透明度，0~1.0  默认0.5
     *
     * @param alpha 默认0.5
     * @return
     */
    public HIndicatorBuilder dimAmount(float alpha) {
        this.alpha = alpha;
        return this;
    }

    /**
     * //设置背景透明度，0~1.0  默认0
     *
     * @param cardElevation 默认0
     * @return
     */
    public HIndicatorBuilder cardElevation(float cardElevation) {
        this.cardElevation = cardElevation;
        return this;
    }

    public HIndicatorBuilder layoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        return this;
    }

    public HIndicatorBuilder adapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        return this;
    }

    public HIndicatorBuilder gravity(@GRAVITY int gravity) {
        this.gravity = gravity;
        return this;
    }

    public HIndicatorBuilder enableTouchOutside(boolean enableTouchOutside) {
        this.enableTouchOutside = enableTouchOutside;
        return this;
    }

    public HIndicatorBuilder data(List<String> list) {
        this.mData = list;
        return this;
    }

    public HIndicatorBuilder clickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
        return this;
    }

    public HIndicatorBuilder onDisMissListener(OnDismissListener listener) {
        this.mOnDismissListener = listener;
        return this;
    }

    public HIndicatorDialog create() {
        if (width <= 0) {
            throw new NullPointerException("width can not be 0");
        }

        if (arrowPercentage < 0) {
            throw new NullPointerException("arrowPercentage can not < 0");
        }

        if (mAdapter == null) {
            mAdapter = new HIndicatorAdapter(mData, mContext, mClickListener);
        }

        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        }
        return HIndicatorDialog.newInstance(mContext, this);
    }

    @IntDef({TOP, BOTTOM, LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ARROWDIRECTION {
    }

    @IntDef({GRAVITY_LEFT, GRAVITY_RIGHT, GRAVITY_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GRAVITY {

    }
}
