package example.com.templateprogram.test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.test.activity.TestAIDLActivity;
import example.com.templateprogram.test.activity.TestCeilingActivity;
import example.com.templateprogram.test.activity.TestCopy;
import example.com.templateprogram.test.activity.TestDeBuggerActivity;
import example.com.templateprogram.test.activity.TestFontActivity;
import example.com.templateprogram.test.activity.TestGifActivity;
import example.com.templateprogram.test.activity.TestGirdRecyclerViewActivity;
import example.com.templateprogram.test.activity.TestGlideActivity;
import example.com.templateprogram.test.activity.TestGoogleBannerActivity;
import example.com.templateprogram.test.activity.TestGoogleInterstitialActivity;
import example.com.templateprogram.test.activity.TestGoogleNativeAdsAdvancedActivity;
import example.com.templateprogram.test.activity.TestGoogleRewardedVideoActivity;
import example.com.templateprogram.test.activity.TestJsonResolve;
import example.com.templateprogram.test.activity.TestMessengerActivity;
import example.com.templateprogram.test.activity.TestPicassoActivity;
import example.com.templateprogram.test.activity.TestVideoViewActivity;
import example.com.templateprogram.test.activity.TestWebViewJSActivity;
import example.com.templateprogram.test.activity.Testone;
import example.com.templateprogram.utils.StaticStateUtils;

/**
 * Created by XQ on 2017/11/28.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater inflater;
    private List<String> mDatas;
    private Context mContext;

    public MainRecyclerAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }


    /**
     * ViewHolder类
     */
    public class MyViewHolder extends ViewHolder {
        public TextView tv;
        public LinearLayout ll_item;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_item);
            ll_item = (LinearLayout) view.findViewById(R.id.ll_item);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_main, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    /**
     * 主要用于适配渲染数据到View中。方法提供给你了一个viewHolder，而不是原来的convertView
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        LogUtils.i("holder----->" + position);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).tv.setText(mDatas.get(position));
            ((MyViewHolder) holder).ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    LogUtils.i("position---->短点击" + position);
                    if (position == 0) {
                        StaticStateUtils.intentToJump(mContext, Testone.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 1) {
                        StaticStateUtils.intentToJump(mContext, TestCopy.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 2) {
                        StaticStateUtils.intentToJump(mContext, TestMessengerActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 3) {
                        StaticStateUtils.intentToJump(mContext, TestAIDLActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 4) {
                        StaticStateUtils.intentToJump(mContext, TestJsonResolve.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 5) {
                        StaticStateUtils.intentToJump(mContext, TestWebViewJSActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 6) {
                        StaticStateUtils.intentToJump(mContext, TestPicassoActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 7) {
                        StaticStateUtils.intentToJump(mContext, TestDeBuggerActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 8) {
                        StaticStateUtils.intentToJump(mContext, TestFontActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 9) {
                        StaticStateUtils.intentToJump(mContext, TestGlideActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 10) {
                        StaticStateUtils.intentToJump(mContext, TestGirdRecyclerViewActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 11) {
                        StaticStateUtils.intentToJump(mContext, TestGoogleBannerActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 12) {
                        StaticStateUtils.intentToJump(mContext, TestGoogleInterstitialActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 13) {
                        StaticStateUtils.intentToJump(mContext, TestGoogleNativeAdsAdvancedActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 14) {
                        StaticStateUtils.intentToJump(mContext, TestGoogleRewardedVideoActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 15) {
                        StaticStateUtils.intentToJump(mContext, TestCeilingActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 16) {
                        StaticStateUtils.intentToJump(mContext, TestVideoViewActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    } else if (position == 17) {
                        StaticStateUtils.intentToJump(mContext, TestGifActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     * Item点击事件的接口
     */
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }


}
