package example.com.templateprogram.test.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.HashMap;
import java.util.Set;

import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;

/**
 * Created by XQ on 2018/1/26.
 * WebView和JS交互
 */
public class TestWebViewJSActivity extends BaseActivity implements View.OnClickListener {


    private Activity mActivity;
    private WebView wv_testwvjs;
    private Button btn_testwvjs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
//        initWebView();
//        initWebView2();
//        initWebView3();
//        initWebView4();
        initWebView5();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_webviewjs;
    }

    /**
     * 加载UI
     */
    public void initView() {
        mActivity = TestWebViewJSActivity.this;
        wv_testwvjs = (WebView) findViewById(R.id.wv_testwvjs);
        btn_testwvjs = (Button) findViewById(R.id.btn_testwvjs);
    }

    /**
     * 加载监听
     */
    public void initListener() {
        btn_testwvjs.setOnClickListener(this);
    }


    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_testwvjs:
//                Message message = Message.obtain();
//                message.what = 1;
//                handler.sendMessageDelayed(message, 100);

                Message message = Message.obtain();
                message.what = 2;
                handler.sendMessageDelayed(message, 100);
                break;
        }
    }


    /**
     * 初始化WebView
     * Android通过WebView调用 JS 代码
     */
    private void initWebView() {
        WebSettings webSettings = wv_testwvjs.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        wv_testwvjs.loadUrl("file:///android_asset/javascript.html");


        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        wv_testwvjs.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(mActivity);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });
    }

    /**
     * 初始化WebView
     * 通过WebView的addJavascriptInterface（）进行对象映射
     */
    private void initWebView2() {
        WebSettings webSettings = wv_testwvjs.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Java对象名
        //参数2：Javascript对象名
        wv_testwvjs.addJavascriptInterface(new AndroidtoJs(), "test");//AndroidtoJS类对象映射到js的test对象(这种方法存在严重的漏洞问题)


        // 载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        wv_testwvjs.loadUrl("file:///android_asset/javascript2.html");
    }


    /**
     * 初始化WebView
     * WebViewClient 的shouldOverrideUrlLoading ()方法回调拦截 url
     */
    private void initWebView3() {
        WebSettings webSettings = wv_testwvjs.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 步骤1：加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
        wv_testwvjs.loadUrl("file:///android_asset/javascript3.html");

        wv_testwvjs.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if (uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {

                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        System.out.println("js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    /**
     * 初始化WebView
     * WebChromeClient 的onJsAlert()、onJsConfirm()、onJsPrompt（）方法回调拦截JS对话框alert()、confirm()、prompt（） 消息
     */
    private void initWebView4() {
        WebSettings webSettings = wv_testwvjs.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 先加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
        wv_testwvjs.loadUrl("file:///android_asset/javascript4.html");


        wv_testwvjs.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // 根据协议的参数，判断是否是所需要的url(原理同方式2)
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）

                Uri uri = Uri.parse(message);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if (uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {

                        //
                        // 执行JS所需要调用的逻辑
                        System.out.println("js调用了Android的方法");
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();

                        //参数result:代表消息框的返回值(输入值)
                        result.confirm("js调用了Android的方法成功啦");
                    }
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            // 通过alert()和confirm()拦截的原理相同，此处不作过多讲述

            // 拦截JS的警告框
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            // 拦截JS的确认框
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }
        });
    }

    /**
     * 打开指定app应用，坚果vpn
     */
    private void initWebView5() {
        WebSettings webSettings = wv_testwvjs.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        wv_testwvjs.loadUrl("file:///android_asset/webopenapp.html");


        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        wv_testwvjs.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(mActivity);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });
    }


    /**
     * handler
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://第一种调用JS的方法

                    // 必须另开线程进行JS方法调用(否则无法调用)
                    // 注意调用的JS方法名要对应上
                    // 调用javascript的callJS()方法
                    wv_testwvjs.loadUrl("javascript:callJS()");
                    break;
                case 2://第二种调用JS的方法:该方法比第一种方法效率更高、使用更简洁,执行不会使页面刷新，而第一种方法(loadUrl)的执行则会,Android 4.4 后才可使用

                    // 只需要将第一种方法的loadUrl()换成下面该方法即可
                    wv_testwvjs.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            //此处为 js 返回的结果
                        }
                    });
                    break;
            }
        }
    };


    /**
     * 定义一个与JS对象映射关系的Android类
     */
    public class AndroidtoJs {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void hello(String msg) {
            System.out.println(msg);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
