package example.com.templateprogram.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Activity mActivity;
    private TextView tv_main_photoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initView() {
        mActivity = MainActivity.this;
        tv_main_photoview = (TextView) findViewById(R.id.tv_main_photoview);


        tv_main_photoview.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_main_photoview:
                Intent intent1 = new Intent(mActivity,
                        PhotoViewActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
        }
    }
}
