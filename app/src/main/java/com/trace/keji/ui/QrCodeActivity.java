package com.trace.keji.ui;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.trace.mining.app.zxing.camera.CameraManager;
import com.trace.mining.app.zxing.decoding.CaptureActivityHandler;
import com.trace.mining.app.zxing.decoding.InactivityTimer;
import com.trace.mining.app.zxing.view.ViewfinderView;
import com.trace.keji.R;


/**
 * Initial the camera
 * 
 * @author Ryan.Tang
 */
@SuppressWarnings("deprecation")
public class QrCodeActivity extends AppCompatActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	
	TextView tv_back,tv_titleName;

	String titleName;
	String scanHint;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		CameraManager.init(getApplication());

		getIntentValue();
		initView();
		initViewAction();

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}
	
	private void getIntentValue() {
		titleName = getIntent().getStringExtra("titleName");
		scanHint = getIntent().getStringExtra("scanHint");
	}
	
	private void initView() {
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_titleName = (TextView) findViewById(R.id.tv_titleName);
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		
		if (titleName == null || titleName.equals("")) {
			tv_titleName.setText("扫描二维码");
		} else {
			tv_titleName.setText(titleName);
		}
		if (scanHint == null || scanHint.equals("")) {
			viewfinderView.setScanHint("将二维码放入框内,即可自动扫描", null);
		} else {
			if(scanHint != null && scanHint.contains("#")){
//				String aaa = scanHint.split("#")[0] +"\n"+ scanHint.split("#")[1];
				viewfinderView.setScanHint(scanHint.split("#")[0], scanHint.split("#")[1]);
			} else {
				viewfinderView.setScanHint(scanHint,null);
			}
		}
	}
	
	private void initViewAction() {
		tv_back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				inactivityTimer.onActivity();
				QrCodeActivity.this.finish();
			}
		});
	}
	protected void onResume() {
		super.onResume();
		try {
			SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			if (hasSurface) {
				initCamera(surfaceHolder);
			} else {
				surfaceHolder.addCallback(this);
				surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			}
			decodeFormats = null;
			characterSet = null;
			
			playBeep = true;
			AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
			if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
				playBeep = false;
			}
			initBeepSound();
			vibrate = true;
		} catch (Exception e) {
			Log.i("", "------好像无权限-e:"+e.getMessage());
		}
	}

	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	protected void onDestroy() {
		if(inactivityTimer != null){
			inactivityTimer.shutdown();
		}
		super.onDestroy();
	}
	
	/**
	 * 处理扫描结果
	 * @param result
	 */
	public void handleDecode(Result result) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(QrCodeActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
		} else {
			Intent resultIntent = new Intent();
			resultIntent.putExtra("result", resultString);
//			resultIntent.putExtra("method", method);
//			Bundle bundle = new Bundle();
//			bundle.putString("result", resultString);
//			bundle.putParcelable("bitmap", barcode);
//			resultIntent.putExtras(bundle);
			this.setResult(RESULT_OK, resultIntent);
		}
		QrCodeActivity.this.finish();
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(QrCodeActivity.this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}	