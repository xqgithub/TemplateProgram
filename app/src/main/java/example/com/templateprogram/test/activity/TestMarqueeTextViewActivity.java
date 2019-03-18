package example.com.templateprogram.test.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.test.view.MarqueeTextView;
import example.com.templateprogram.test.view.MarqueeTextView2;
import example.com.templateprogram.test.view.MarqueeTextView3;
import example.com.templateprogram.utils.LogUtils;
import example.com.templateprogram.utils.ScreenUtils;
import example.com.templateprogram.utils.StringUtils;

/**
 * Created by beijixiong on 2019/3/5.
 * 跑马灯
 */

public class TestMarqueeTextViewActivity extends BaseActivity {

    private MarqueeTextView mv;
    private MarqueeTextView2 mv2;
    private MarqueeTextView3 mv3;

    String content1 = "我是&海贼王&路飞，abc要成为%海贼王%的男人，请%祝福%我吧，&哈哈&";
    String content2 = "%露西按%吃&粑粑&吃的真是欢乐，你个狗的继续吃粑粑啊，哈哈";
    String content3 = "顶焦度计%我要变红&哈哈哈&多看看%大口大口看到";
    String content4 = "官方最新地址:https://ta3.app，旧地址已被封 &查看详情&";
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mv = findViewById(R.id.mv);
        mv2 = findViewById(R.id.mv2);
        mv3 = findViewById(R.id.mv3);

        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_marqueetextview;
    }

    public void startScroll(View view) {
    }

    public void pauseScroll(View view) {
        wordProcess(mv, content2);
    }

    public void resumeScroll(View view) {
        wordProcess(mv, content3);
    }

    public void initData() {
//        wordProcess(mv, content1);
//        mv.setText(content1);

//        mv2.setText(content1);
//        mv2.setRndDuration(20000);
//        mv2.startScroll();

        mv3.setViewMargin(ScreenUtils.dip2px(this, 100));
        for (int i = 0; i < 100; i++) {
            TextView textView = new TextView(this);
            mv3.addViewInQueue(textView);
            wordProcess(textView, content1);
        }
        mv3.setScrollSpeed(3);
        mv3.setScrollDirection(MarqueeTextView3.RIGHT_TO_LEFT);
        mv3.startScroll();
    }

    /**
     * 文字处理
     */
    String charactermatch1 = "&";
    String charactermatch2 = "%";
    String startflag = "_";
    String endflag = "__";

    private void wordProcess(TextView textView, String content) {
        try {
            if (!StringUtils.isBlank(content)) {
                Map<String, Integer> map = new HashMap<>();
                int map_size = 0;

                String resultcontent = specialCharacterProcess(content, charactermatch1, charactermatch2, map);
                SpannableString ss = new SpannableString(resultcontent);
                map_size = map.size() / 2;
                if (!map.isEmpty() && map_size > 0) {
                    for (int i = 0; i < map_size; i++) {
                        Object index1_start = map.get(charactermatch1 + startflag + i);
                        Object index2_start = map.get(charactermatch2 + startflag + i);
                        if (index1_start != null) {
                            Object index1_end = map.get(charactermatch1 + endflag + i);

                            //设置字体前景色为红色
                            ss.setSpan(new ForegroundColorSpan(Color.RED),
                                    (Integer) index1_start, (Integer) index1_end,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            //设置下划线
                            ss.setSpan(new UnderlineSpan(),
                                    (Integer) index1_start, (Integer) index1_end,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }

                        if (index2_start != null) {
                            Object index2_end = map.get(charactermatch2 + endflag + i);
                            //设置字体前景色为红色
                            ss.setSpan(new ForegroundColorSpan(Color.BLUE),
                                    (Integer) index2_start, (Integer) index2_end,
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        }
                    }
                }
                textView.setText(ss);
            } else {
                textView.setText("");
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            textView.setText(content);
        }
    }


    private String specialCharacterProcess(String srcText, String findText1, String findText2, Map<String, Integer> map) {
        String resultString = srcText;
        int num1 = 0;
        int num2 = 0;
        num1 = appearNumber(srcText, findText1) / 2;
        num2 = appearNumber(srcText, findText2) / 2;
        if (num1 > 0 || num2 > 0) {
            for (int i = 0; i < (num1 + num2); i++) {
                int index1_start = resultString.indexOf(findText1);
                int index2_start = resultString.indexOf(findText2);
                if (index1_start != -1 && index2_start != -1) {
                    if (index1_start > index2_start) {
                        map.put(findText2 + startflag + i, index2_start);
                        resultString = resultString.replaceFirst(findText2, "");

                        int index2_end = resultString.indexOf(findText2);
                        map.put(findText2 + endflag + i, index2_end);
                        resultString = resultString.replaceFirst(findText2, "");
                    } else {
                        map.put(findText1 + startflag + i, index1_start);
                        resultString = resultString.replaceFirst(findText1, "");

                        int index1_end = resultString.indexOf(findText1);
                        map.put(findText1 + endflag + i, index1_end);
                        resultString = resultString.replaceFirst(findText1, "");
                    }
                } else if (index1_start == -1 && index2_start != -1) {
                    map.put(findText2 + startflag + i, index2_start);
                    resultString = resultString.replaceFirst(findText2, "");

                    int index2_end = resultString.indexOf(findText2);
                    map.put(findText2 + endflag + i, index2_end);
                    resultString = resultString.replaceFirst(findText2, "");
                } else if (index2_start == -1 && index1_start != -1) {
                    map.put(findText1 + startflag + i, index1_start);
                    resultString = resultString.replaceFirst(findText1, "");

                    int index1_end = resultString.indexOf(findText1);
                    map.put(findText1 + endflag + i, index1_end);
                    resultString = resultString.replaceFirst(findText1, "");
                }


            }
        }
        return resultString;
    }


    /**
     * 相同的字符出现了几次
     */
    private int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mv3.stopScroll();
    }
}
