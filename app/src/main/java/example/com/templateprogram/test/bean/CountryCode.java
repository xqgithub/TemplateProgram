package example.com.templateprogram.test.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import java.util.List;

/**
 * Created by beijixiong on 2018/10/21.
 * 国家名称和代码
 */

public class CountryCode {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends BaseIndexPinyinBean {
        /**
         * code : 86
         * tw : 中國
         * en : China
         * locale : CN
         * zh : 中国
         */

        private int code;
        private String tw;
        private String en;
        private String locale;
        private String zh;
        private boolean isTop;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getTw() {
            return tw;
        }

        public void setTw(String tw) {
            this.tw = tw;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public String getZh() {
            return zh;
        }

        public void setZh(String zh) {
            this.zh = zh;
        }

        public boolean isTop() {
            return isTop;
        }

        public void setTop(boolean top) {
            isTop = top;
        }

        @Override
        public String getTarget() {
            return zh;
        }

        @Override
        public boolean isNeedToPinyin() {
            return !isTop;
        }


        @Override
        public boolean isShowSuspension() {
            return !isTop;
        }
    }
}
