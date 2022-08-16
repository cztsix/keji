package com.trace.keji.ui;

import java.io.File;
import java.net.URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.trace.keji.R;
import com.trace.keji.fragment.ImageDetailFragment;
import com.trace.keji.photoview.HackyViewPager;
import com.trace.keji.utils.BitmapUtil;
import com.trace.keji.utils.FileUtil;
import com.trace.keji.utils.ShowUtil;

/**
 * 图片浏览
 * 
 * @author user
 * 
 */
public class ImagePagerActivity extends FragmentActivity {
	
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index"; 
	public static final String EXTRA_IMAGE_URLS = "image_urls";

	private HackyViewPager mPager;
	private TextView indicator;
	private TextView tv_back;
	
	private int pagerPosition;
	String[] urls;
	
	ImagePagerAdapter mAdapter;
	
	int currPostion;
	
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private View parentView;
	
	private String path;// 图片保存在路径
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.image_detail_pager, null);
		setContentView(parentView);
		ImageDetailFragment.mImagePagerActivity = this;
		
		initIntentValue();
		initView();
		initViewAction();
		initPopView();
		
		CharSequence text = getString(R.string.viewpager_indicator, pagerPosition+1, mPager.getAdapter().getCount());
		indicator.setText(text);
		
		// 更新下标
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public void onPageScrollStateChanged(int arg0) {
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			public void onPageSelected(int arg0) {
				currPostion = arg0;
				
				CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText(text);
			}
		});
		
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		
		mPager.setCurrentItem(pagerPosition);
	}
	
	private void initIntentValue() {
		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		urls = getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);
	}
	
	private void initView() {
		mPager = (HackyViewPager) findViewById(R.id.pager);
		indicator = (TextView) findViewById(R.id.indicator);
		tv_back = (TextView) findViewById(R.id.tv_back);
	}

	private void initViewAction() {
		mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
		mPager.setAdapter(mAdapter);

		tv_back.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				ImagePagerActivity.this.finish();
			}
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentStatePagerAdapter {

		public String[]  fileList;

		public ImagePagerAdapter(FragmentManager fm, String[]  fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.length;
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList[position];
			return ImageDetailFragment.newInstance(url);
		}
	}
	
	
	public void popViewShow() {
		ll_popup.startAnimation(AnimationUtils.loadAnimation(ImagePagerActivity.this, R.anim.activity_translate_in));
		pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
	}
	
	
	private void initPopView() {
		pop = new PopupWindow(this);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		
		// 保存到相册
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
				
				getBitmap(urls[currPostion]);
			}
		});
		// 取消
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
	}
	
	
	private void getBitmap(final String imageUrl) {
		new Thread(new Runnable() {
			public void run() {
				try {
					URL url = new URL(imageUrl);
					Bitmap mBitmap = BitmapUtil.getBitmapByUrl(url);
					
					path = FileUtil.savaBitmap(mBitmap);
					
					mHandler.sendEmptyMessage(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				File file = new File(path);
				Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.fromFile(file);
				intent.setData(uri);
				
				//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
				ImagePagerActivity.this.sendBroadcast(intent);

				ShowUtil.showCenterToast(ImagePagerActivity.this, "保存成功", 1);
				break;
			}
		}
	};
	

	
		
}
