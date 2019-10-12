package example.com.templateprogram.test.meituan;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * 美团里的城市bean
 * Created by Administrator on 2019/10/12.
 */

public class MeiTuanBean extends BaseIndexPinyinBean {
    private String city;//城市名字

    public MeiTuanBean() {
    }

    public MeiTuanBean(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public MeiTuanBean setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String getTarget() {
        return city;
    }
}