package example.com.templateprogram.entity;

/**
 * Created by beijixiong on 2018/12/21.
 * ping后获值的实体类
 */

public class PingResponse {

    //ping的域名或者ip
    private String domain;
    //每次ping的时间
    private int ping_time;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getPing_time() {
        return ping_time;
    }

    public void setPing_time(int ping_time) {
        this.ping_time = ping_time;
    }
}
