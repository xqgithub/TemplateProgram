package example.com.templateprogram.utils.apiencrypt;

/**
 * Created by beijixiong on 2019/1/4.
 */

public class LocationResponse {
    /**
     * is_domestic : true
     * client_ip : 127.0.0.1
     */

    private boolean is_domestic;
    private String client_ip;

    public boolean isIs_domestic() {
        return is_domestic;
    }

    public void setIs_domestic(boolean is_domestic) {
        this.is_domestic = is_domestic;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }
}
