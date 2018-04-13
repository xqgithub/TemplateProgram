package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by admin on 2018/3/5.
 */
public class TestDeBuggerActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int num = 10;
        int min = Math.min(num, 100);
        System.out.println("min----->" + min);

        alertName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_debugger;
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {

    }

    private String name = "default";

    public void alertName() {
        System.out.println(name);
        debug();
    }

    public void debug() {
        this.name = "debug";
        System.out.println("name----->" + name);
    }
}
