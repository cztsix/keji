package com.trace.keji.ui;

import android.app.AlertDialog;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.JsResult;
//import android.webkit.ValueCallback;
//import android.webkit.WebChromeClient;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Configuration;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.trace.keji.MyApplication;
import com.trace.keji.R;
import com.trace.keji.common.Constant;
import com.trace.keji.dialog.LDialog;
import com.trace.keji.model.HelpTipModel;
import com.trace.keji.model.JsConfig;
import com.trace.keji.model.ScanCallback;
import com.trace.keji.utils.PreferenceUtil;
import com.trace.keji.utils.ToastUtil;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.sdk.WebChromeClient;
import  com.tencent.smtt.sdk.ValueCallback;

import java.io.File;

/**
 * @author user
 */
public class MainActivity extends AppCompatActivity {
    public static final int SCANQRCODE_RESULTCODE = 100;// 扫描二维码回调
    public static final int FILECHOOSER_RESULTCODE = 110;// 文件选择回调
    public static boolean isForeground = false;
    public static boolean shagriGoBack = false;
    public static boolean loadPageFinish = false;
    private WebView mWebView;
    private Button btnJump;
    private LDialog dialog;
    public MyApplication myApp;
    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mFilePathCallback;
    public ScanCallback mScanCallback;
    public PreferenceUtil pfUserInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.activity_main);
        getPfUserInfo();
        initView();
        initWebview();
    }

    private void initWebview() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccessFromFileURLs(false);
        webSetting.setAllowUniversalAccessFromFileURLs(false);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();

        mWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelFilePathCallback();
            }
        });

        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            public boolean onJsAlert(android.webkit.WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b2 = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("title")
                        .setMessage(message)
                        .setPositiveButton("ok", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });

                b2.setCancelable(false);
                b2.create();
                b2.show();
                return true;
            }

            // 浏览器打开文件选择器
            // For Android > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }

            // 浏览器打开文件选择器
            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            // 浏览器打开文件选择器
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                if (mUploadMessage != null) {
                    return;
                }
                mUploadMessage = uploadMsg;

                startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if(mFilePathCallback != null) {
                    return  false;
                }

                mFilePathCallback = filePathCallback;
                startActivityForResult(createDefaultOpenableIntent(), 15);
                return true;
            }
        });


        final JsInteration mJsInteration = new JsInteration(this, mWebView, true, myApp);


        mWebView.addJavascriptInterface(mJsInteration, "shagri_app");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (!loadPageFinish) {
                    loadPageFinish = true;
                    // 网页加载完成之后
                    webView.loadUrl("javascript: if ( typeof onStart != 'undefined' & onStart instanceof Function ) {onStart();};");
                }

                final String js = pfUserInfo.getStrPreference("JS", "");
                if (!js.equals("")) {
                    pfUserInfo.saveStrPreference("JS", "");
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message msg = mJsInteration.getHandler().obtainMessage(JsConfig.executeJs);
                            msg.getData().putString("method", js);
                            mJsInteration.getHandler().sendMessage(msg);
                        }
                    }).start();
                }
            }
        });
        mWebView.loadUrl(Constant.WEBVIEW_URL);
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.mywebview);
        btnJump = (Button) findViewById(R.id.btnJump);
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QrCodeActivity.class));
            }
        });
    }

    public PreferenceUtil getPfUserInfo() {
        if (pfUserInfo == null) {
            pfUserInfo = new PreferenceUtil("UserInfo", this);
        }
        return pfUserInfo;
    }

    /**
     * 当点击后退按钮后，执行如下判定：
     * 1、连击2次，执行快速退出
     * 2、每次单击，调用页面的$.shagriGoBack()方法方法返回true，不执行任何操作，方法返回false，则弹出推出提示框，点击确定后关闭程序
     */
    private long currentTime = 0;

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if ((System.currentTimeMillis() - currentTime) > 1000) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }
                currentTime = System.currentTimeMillis();
            } else {
                openCloseDialog();
            }
            return false;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        try {
            super.onConfigurationChanged(newConfig);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.v("Himi", "onConfigurationChanged_ORIENTATION_LANDSCAPE");
            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                Log.v("Himi", "onConfigurationChanged_ORIENTATION_PORTRAIT");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 打开二次确认对话框
     */
    public void openCloseDialog() {
        if (dialog == null) {
            dialog = new LDialog(this, R.layout.dialog_confirm); //你的布局
            dialog.with()  //必须
                    //1.设置宽
                    .setWidth(280) //单位:dp
                    //.setWidthPX() //单位:px
                    //.setWidthRatio(0.8) //占屏幕宽比例

                    //2.设置高
                    .setHeight(150) //单位:dp
                    //.setHeightPX() //单位:px
                    //.setHeightRatio() //占屏幕高比例

                    //3.设置背景
                    .setBgColor(Color.WHITE) //背景颜色
                    //.setBgColorRes(R.color.colorWhite) //res资源
                    .setBgRadius(5) //圆角, 单位：dp
                    //.setBgRadiusPX() //圆角, 单位：px

                    //4.设置弹框位置 和 动画(显示和隐藏动画)
                    .setGravity(Gravity.CENTER) //设置弹框位置
                    //.setGravity(Gravity.LEFT, 0, 0) //设置弹框位置(偏移量)
                    .setAnimationsStyle(R.style.dialog_translate) //设置动画

                    //5.设置具体布局
                    //5.1 常见系统View属性
//                .setText(R.id.tv_title, "确定")
                    .setTextColor(R.id.tv_title, getResources().getColor(R.color.colorAccent))
                    .setTextColor(R.id.tv_confirm, getResources().getColor(R.color.colorAccent))
                    //.setBackgroundColor()
                    //.setBackgroundRes()
                    //.setImageBitmap()
                    .setVisible(R.id.tv_title, true)
                    //.setGone()
                    //5.2 其它属性
                    .setCancelBtn(R.id.tv_cancel) //设置按钮，弹框消失的按钮
                    .setOnClickListener(new LDialog.DialogOnClickListener() { //设置按钮监听
                        @Override
                        public void onClick(View v, LDialog customDialog) {
                            System.exit(0);
                            customDialog.dismiss();
                        }
                    }, R.id.tv_confirm)//可以传多个
                    .show(); //显示
        } else {
            dialog.show();
        }

    }

    private Intent createDefaultOpenableIntent() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooser = createChooserIntent(createCameraIntent());
        chooser.putExtra(Intent.EXTRA_INTENT, i);
        return chooser;
    }

    private Intent createChooserIntent(Intent... intents) {
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        chooser.putExtra(Intent.EXTRA_TITLE, "选择图片");
        return chooser;
    }

    private Uri mOutPutFileUri;

    private Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String path = Environment.getExternalStorageDirectory().toString() + "/shagri/12316";
        File path1 = new File(path);
        if (!path1.exists()) {
            path1.mkdirs();
        }
        File file = new File(path1, System.currentTimeMillis() + ".jpg");
        mOutPutFileUri = Uri.fromFile(file);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
        return cameraIntent;
    }

    private void cancelFilePathCallback() {
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
        } else if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //扫描二维码返回结果
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null && !TextUtils.isEmpty(scanResult.getContents())) {
            String str = scanResult.getContents();
            ToastUtil.showLongToast(MainActivity.this, str);
            if (mScanCallback != null) {
                if (mScanCallback.data.callbackData == null || mScanCallback.data.callbackData.equals("")) {
                    // 执行扫描二维码回调的JS
                    mWebView.loadUrl("javascript:" + mScanCallback.data.callbackMethodName + "(\'" + str + "\');");
                } else {
                    // 执行扫描二维码回调的JS
                    mWebView.loadUrl("javascript:" + mScanCallback.data.callbackMethodName
                            + "(\'" + str + "\');");
//								+ "(\'"+s+"', $.shagriReplace(\'"+mScanCallback.data.callbackData+"\','all',false))");
                }
            }
        }

        if (resultCode == RESULT_OK) {
            // 文件选择回调
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == mUploadMessage) {
                    return;
                }
                Uri result = null;
                result = data == null ? null : data.getData();
                if (result == null) {
                    result = mOutPutFileUri;
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            } else if (requestCode == 15) {
                if (null == mFilePathCallback) {
                    return;
                }
                Uri result = null;
                result = data == null ? null :  data.getData();
                if (result == null) {
                    result = mOutPutFileUri;
                }

                Uri[] resuts = new Uri[]{ result };

                mFilePathCallback.onReceiveValue(resuts);
                mFilePathCallback = null;
            }
            // 扫描二维码回调
            else if (requestCode == SCANQRCODE_RESULTCODE) {
                String s = data.getStringExtra("result");
                if (mScanCallback != null) {
                    if (mScanCallback.data.callbackData == null || mScanCallback.data.callbackData.equals("")) {
                        // 执行扫描二维码回调的JS
                        mWebView.loadUrl("javascript:" + mScanCallback.data.callbackMethodName + "(\'" + s + "\');");
                    } else {
                        // 执行扫描二维码回调的JS
                        mWebView.loadUrl("javascript:" + mScanCallback.data.callbackMethodName
                                + "(\'" + s + "\');");
//								+ "(\'"+s+"', $.shagriReplace(\'"+mScanCallback.data.callbackData+"\','all',false))");
                    }
                }
            }
        } else if (resultCode == 0 || data == null) {
            cancelFilePathCallback();
        } else if (resultCode == RESULT_CANCELED) {
            cancelFilePathCallback();
        }
    }

    /**
     * 帮助提示
     *
     * @param
     */
    public void helpTipDialog(final HelpTipModel model) {
        AlertDialog.Builder b2 = new AlertDialog.Builder(this)
                .setMessage(model.data.mssage)
                .setPositiveButton("确认", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (model.data.callbackData != null && !model.data.callbackData.equals("")) {
                            mWebView.loadUrl("javascript:" + model.data.callbackMethodName + "(" + true + ", $.shagriReplace(\'" + model.data.callbackData + "\','all',false))");
                        } else {
                            mWebView.loadUrl("javascript:" + model.data.callbackMethodName + "(true)");
                        }
                    }
                }).setNegativeButton("取消", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (model.data.callbackData != null && !model.data.callbackData.equals("")) {
//							myWebview.loadUrl("javascript:" + model.data.callbackMethodName + "("+false+", \'"+model.data.callbackData+"\')");
                            mWebView.loadUrl("javascript:" + model.data.callbackMethodName + "(" + false + ", $.shagriReplace(\'" + model.data.callbackData + "\','all',false))");
                        } else {
                            mWebView.loadUrl("javascript:" + model.data.callbackMethodName + "(false)");
                        }
                    }
                });
        b2.setCancelable(true);
        b2.create();
        b2.show();
    }
}

