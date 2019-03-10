package example.com.templateprogram.test.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import example.com.templateprogram.R;

/**
 * Created by beijixiong on 2019/3/8.
 */

public class GlobalDialog extends Dialog {

    private Activity activity;

    public GlobalDialog(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_global);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = activity.getResources().getDimensionPixelSize(R.dimen.deimen_350x);
        lp.height = activity.getResources().getDimensionPixelSize(R.dimen.deimen_350x);
        window.setAttributes(lp);
    }
}
