package com.trace.keji.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.trace.keji.R;
import com.trace.keji.photoview.PhotoViewAttacher;
import com.trace.keji.ui.ImagePagerActivity;

/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
	
	private String mImageUrl;
	private ImageView mImageView;
	private ProgressBar progressBar;
	private PhotoViewAttacher mAttacher;
	
	public static ImagePagerActivity mImagePagerActivity;

	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);

		mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});
		
		mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
//				Toast.makeText(getActivity(), "", 1000).show();
				Log.i("", "----------图片长按---------");
				
				mImagePagerActivity.popViewShow();
				return false;
			}
		});
		
//		mAttacher.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View arg0, MotionEvent arg1) {
//				// TODO Auto-generated method stub
//				Log.i("", "----------图片长按---------");
//				return true;
//			}
//		});

		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//
//		ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
//			@Override
//			public void onLoadingStarted(String imageUri, View view) {
//				progressBar.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//				String message = null;
//				switch (failReason.getType()) {
//				case IO_ERROR:
//					message = "下载错误";
//					break;
//				case DECODING_ERROR:
//					message = "图片无法显示";
//					break;
//				case NETWORK_DENIED:
//					message = "网络有问题，无法下载";
//					break;
//				case OUT_OF_MEMORY:
//					message = "图片太大无法显示";
//					break;
//				case UNKNOWN:
//					message = "未知的错误";
//					break;
//				}
//				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//				progressBar.setVisibility(View.GONE);
//			}
//
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				progressBar.setVisibility(View.GONE);
//				mAttacher.update();
//			}
//		});
	}
}
