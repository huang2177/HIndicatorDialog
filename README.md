# 引入
- 1.0 将下面代码添加到项目的根目录的build.gradle： 
```
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
- 2.0 将下面代码添加到app目录的build.gradle： 
```
dependencies { 
     compile 'com.github.huangbryant210707:HIndicatorDialog:2.0.0'
}
```

# 使用 

```
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
```
