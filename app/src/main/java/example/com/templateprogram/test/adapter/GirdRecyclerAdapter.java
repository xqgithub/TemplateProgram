package example.com.templateprogram.test.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import example.com.templateprogram.R;

/**
 * Created by admin on 2018/5/10.
 */

public class GirdRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private List<String> mDatas;
    private Context mContext;

    public GirdRecyclerAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }


    /**
     * ViewHolder类
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
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
        GirdRecyclerAdapter.MyViewHolder myViewHolder = new GirdRecyclerAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).tv.setText(mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mDatas.get(position).length() > 9) {
                        return 6;
                    } else {
                        return 3;
                    }
                }
            });
        }
    }
}
