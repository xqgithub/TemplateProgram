package example.com.templateprogram.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.utils.LogUtils;

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
        LogUtils.i("holder----->" + position);
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).tv.setText(mDatas.get(position));
            ((MyViewHolder) holder).ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i("position---->短点击" + position);
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
