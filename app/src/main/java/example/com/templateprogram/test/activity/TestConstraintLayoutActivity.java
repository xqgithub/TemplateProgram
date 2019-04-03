package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import butterknife.BindView;
import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by beijixiong on 2019/4/2.
 * 约束布局
 */

public class TestConstraintLayoutActivity extends BaseActivity {


    @BindView(R.id.btn_A)
    Button btnA;
    @BindView(R.id.btn_B)
    Button btnB;
    @BindView(R.id.btn_C)
    Button btnC;
    @BindView(R.id.btn_D)
    Button btnD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_constraintlayout;
    }

    private void initData() {





        btnC.setText("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        btnD.setText("DDDDDDDDDDDDDD");
    }


}
