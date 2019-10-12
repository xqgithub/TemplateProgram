package example.com.templateprogram.test.meituan;

/**
 * 美团最顶部Header
 * Created by Administrator on 2019/10/12.
 */

public class MeituanTopHeaderBean {
    private String txt;

    public MeituanTopHeaderBean(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public MeituanTopHeaderBean setTxt(String txt) {
        this.txt = txt;
        return this;
    }

}
