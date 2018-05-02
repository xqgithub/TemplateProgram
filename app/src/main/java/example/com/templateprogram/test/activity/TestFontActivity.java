package example.com.templateprogram.test.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by admin on 2018/4/13.
 */

public class TestFontActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_testfont1;
    private RelativeLayout rl_testfont;


    private static final int changfonts = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case changfonts:
                    changeFonts(rl_testfont, TestFontActivity.this, "fonts/Avenir.ttc");
                    break;
            }
        }
    };

    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rl_testfont = (RelativeLayout) findViewById(R.id.rl_testfont);
        tv_testfont1 = (TextView) findViewById(R.id.tv_testfont1);
        Message message = Message.obtain();
        message.what = changfonts;
        handler.sendMessage(message);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_testfont;
    }

    /**
     * 用递归的方式实现加载自定义字体
     *
     * @param root
     * @param act
     * @param fontspath
     */
    public static void changeFonts(ViewGroup root, Activity act, String fontspath) {
        Typeface tf = Typeface.createFromAsset(act.getAssets(),
                fontspath);
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, act, fontspath);
            }
        }
    }
}
