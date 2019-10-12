package example.com.templateprogram.test.adapter;

import android.content.Context;

import java.util.List;

import example.com.templateprogram.R;
import example.com.templateprogram.test.meituan.CommonAdapter;
import example.com.templateprogram.test.meituan.MeiTuanBean;
import example.com.templateprogram.test.meituan.ViewHolder;

/**
 * Created by Administrator on 2019/10/12.
 */

public class MeituanAdapter extends CommonAdapter<MeiTuanBean> {

    public MeituanAdapter(Context context, int layoutId, List<MeiTuanBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final MeiTuanBean cityBean) {
        holder.setText(R.id.tvCity, cityBean.getCity());
    }
}
