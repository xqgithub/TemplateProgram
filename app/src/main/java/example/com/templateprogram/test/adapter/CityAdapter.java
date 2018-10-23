package example.com.templateprogram.test.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.test.bean.CountryCode;

/**
 * Created by beijixiong on 2018/10/21.
 * 城市列表适配器
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    protected Context mContext;
    protected List<CountryCode.DataBean> mDatas;
    protected LayoutInflater mInflater;

    public CityAdapter(Context mContext, List<CountryCode.DataBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<CountryCode.DataBean> getDatas() {
        return mDatas;
    }

    public CityAdapter setDatas(List<CountryCode.DataBean> datas) {
        mDatas = datas;
        return this;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(final CityAdapter.ViewHolder holder, final int position) {
        final CountryCode.DataBean dataBean = mDatas.get(position);
        holder.tvCity.setText(dataBean.getZh());
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "pos:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.avatar.setImageResource(R.drawable.friend);
        holder.tv_code.setText("+" + dataBean.getCode());
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;
        ImageView avatar;
        View content;
        public TextView tv_code;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
            avatar = itemView.findViewById(R.id.ivAvatar);
            content = itemView.findViewById(R.id.content);
            tv_code = itemView.findViewById(R.id.tv_code);
        }
    }

}
