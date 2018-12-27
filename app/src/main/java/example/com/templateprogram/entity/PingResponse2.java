package example.com.templateprogram.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by beijixiong on 2018/12/21.
 * ping后获值的实体类
 */

public class PingResponse2 implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        //ping的域名或者ip
        private String request_url;
        //每次ping的时间
        private int max_rt;

        public String getRequest_url() {
            return request_url;
        }

        public void setRequest_url(String request_url) {
            this.request_url = request_url;
        }

        public int getMax_rt() {
            return max_rt;
        }

        public void setMax_rt(int max_rt) {
            this.max_rt = max_rt;
        }
    }
}
