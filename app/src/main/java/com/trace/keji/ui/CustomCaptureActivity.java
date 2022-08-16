package com.trace.keji.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.trace.keji.R;

/**
 * @Class: CustomCaptureActivity
 * @Description: 自定义条形码/二维码扫描
 * @Author: wangnan7
 * @Date: 2017/5/19
 */

public class CustomCaptureActivity extends AppCompatActivity {

   private static final String TAG = "CustomCaptureActivity";
   public static final String KEY_TO = "key_to";
   public static final String KEY_FROM = "key_from";
   public static final String KEY_NOCOPY = "key_nocopy";

    /**
     * 条形码扫描管理器
     */
    private CaptureManager mCaptureManager;

    /**
     * 条形码扫描视图
     */
    private DecoratedBarcodeView mBarcodeView;

    private boolean isTorchOn = false;
    private TextView tvFlashLight;
    private ImageView ivFlashLight;
    private String titleName;
    private String scanHint;
    private TextView tvTitle;
    private TextView tvStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(R.layout.activity_zxing_layout);
        initToolbar();
        getIntentValue();
        mBarcodeView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        mCaptureManager = new CaptureManager(this, mBarcodeView);

        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
        mCaptureManager.decode();
        tvStatusView = mBarcodeView.findViewById(R.id.zxing_status_view);
//        tvStatusView.setText(Html.fromHtml(getResources().getString(R.string.tips_scan_code)));

        tvFlashLight = findViewById(R.id.tv_flash_light);
        ivFlashLight = findViewById(R.id.iv_flash_light);
        LinearLayout llFlashLight = findViewById(R.id.ll_flash_light);
        llFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTorchOn){
                    mBarcodeView.setTorchOn();
                    isTorchOn = true;
                    tvFlashLight.setText(getResources().getString(R.string.tip_flash_light_off));
                    ivFlashLight.setImageResource(R.drawable.light_on);
                }else {
                    mBarcodeView.setTorchOff();
                    isTorchOn = false;
                    tvFlashLight.setText(getResources().getString(R.string.tip_flash_light_on));
                    ivFlashLight.setImageResource(R.drawable.light_off);
                }

            }
        });
        initView();
    }

    private void initView() {
        if (titleName == null || titleName.equals("")) {
            tvTitle.setText("扫描二维码");
        } else {
            tvTitle.setText(titleName);
        }
        if (scanHint == null || scanHint.equals("")) {
            tvStatusView.setText("将二维码放入框内,即可自动扫描", null);
        } else {
            if(scanHint != null && scanHint.contains("#")){
                tvStatusView.setText(scanHint.split("#")[0]+""+scanHint.split("#")[1]);
            } else {
                tvStatusView.setText(scanHint);
            }
        }
    }

    private void getIntentValue() {
        titleName = getIntent().getStringExtra("titleName");
        scanHint = getIntent().getStringExtra("scanHint");
    }

    /**
     * 初始化窗口
     */
    private void initWindow() {
        // API_19及其以上透明掉状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags;
        }
    }

    /**
     * 初始化标题栏
     */
    private void initToolbar() {
        findViewById(R.id.fl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(noCopy.equals("1")){
//                    EventBus.getDefault().post(new MessageEvent(null, "scanBack"));
//                    finish();
//                }else{
//                    finish();
//                }
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaptureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCaptureManager.onSaveInstanceState(outState);
    }

    /**
     * 权限处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
       // mCaptureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 250) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                mBarcodeView.resume();
            } else {
                // TODO: display better error message.
                displayMessage();
            }
        }
    }

    /**
     * 按键处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    private void displayMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("操作提示")
                .setMessage("注意：当前缺少必要权限！\n请点击“设置”-“权限”-打开所需权限\n最后点击两次后退按钮，即可返回")
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

}
