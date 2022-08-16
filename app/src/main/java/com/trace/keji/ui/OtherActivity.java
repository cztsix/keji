package com.trace.keji.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.trace.keji.R;

/**
 * 
 * 一个单独的网页
 * 
 * @author user
 * 
 */
public class OtherActivity extends AppCompatActivity {

	TextView tv_back, tv_titleName;
	WebView mywebview;
	String url;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.activity_other);
		
		getIntentValue();
		initView();
		initViewAction();
	}

	private void getIntentValue() {
		url = getIntent().getStringExtra("url");
	}

	private void initView() {
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_titleName = (TextView) findViewById(R.id.tv_titleName);
		tv_titleName.setText("");
		mywebview = (WebView) findViewById(R.id.mywebview);
	}

	@SuppressLint("NewApi")
	private void initViewAction() {
		tv_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				OtherActivity.this.finish();
			}
		});
		
		mywebview.loadUrl(url);
		mywebview.getSettings().setJavaScriptEnabled(true);
		mywebview.getSettings().setDomStorageEnabled(true);
		mywebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		
		// 方式二
		// 使用网页全屏显示
		mywebview.getSettings().setUseWideViewPort(true);
		mywebview.getSettings().setLoadWithOverviewMode(true);
		// 方式一
//		mywebview.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
//		mywebview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
		
		
		mywebview.setWebViewClient(new WebViewClient() {
		       @Override
		       public void onPageFinished(WebView view, String url) {
		    	   String title = view.getTitle();
		           tv_titleName.setText(title);
		       }
		   });
	}

	public static void startActivity(Activity activity, String url) {
		Intent intent = new Intent(activity, OtherActivity.class);
		intent.putExtra("url", url);
		activity.startActivity(intent);
	}
	
}
