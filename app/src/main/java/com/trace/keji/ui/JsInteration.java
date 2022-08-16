package com.trace.keji.ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.SpannableString;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.trace.keji.MyApplication;
import com.trace.keji.R;
import com.trace.keji.database.DBManager;
import com.trace.keji.model.*;
import com.trace.keji.utils.*;
import com.tencent.smtt.sdk.WebView;


/**
 * JS方法专用
 * 
 * @author Administrator
 * 
 */
public class JsInteration {
	
	public WebView baseWebview;
	public MainActivity mContext;
	public boolean tag;
	private Vibrator vibrator;
	private SharedPreferences sp;
	MyApplication myApp;
	
	
	
	public JsInteration(MainActivity mContext, WebView baseWebview, boolean tag,MyApplication myApp) {
		this.mContext = mContext;
		this.baseWebview = baseWebview;
		this.tag = tag;
		this.myApp = myApp;
	}
	
	public Handler getHandler() {
		return mHandler;
	}

	@JavascriptInterface
	public void postMessage(String json) {
		try {
//			Log.i("", "====json+="+json);
			Model model= new Gson().fromJson(json, Model.class);
			Class clazz = JsInteration.this.getClass();
			
			// 只传方法
			if(model.data == null && model.method.equals("closeApp")){
				mHandler.sendEmptyMessage(JsConfig.closeApp);
			}
			else {
				Method m2 = clazz.getDeclaredMethod(model.method, String.class);
				m2.invoke(JsInteration.this, json); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 保存key value键值对到数据库
	 */
	public void addData(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.addData);//, json
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过key获取所有Value
	 */
	public void selectDataByKey(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.selectDataByKey);//, json
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置推送Tag
	 */
	public void setJPushTag(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.setJPushTag);//, json
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭APP
	 */
	public void closeApp(String json) {
		mHandler.sendEmptyMessage(JsConfig.closeApp);
	}
	
	/**
	 * 打电话
	 */
	public void call(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.call);//, json
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 放大缩小图片
	 */
	public void zoomImage(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.zoomImage);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开另外一个网页
	 */
	public void openOhterActivity(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.openOhterActivity);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开一个摄像头
	 */
	public void openPlayActivity(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.openPlayActivity);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 帮助提示
	 */
	public void helpTip(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.helpTip);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 扫描二维码
	 */
	public void scanQrcode(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.scanQrcode);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分享到第三方
	 */
	public void shareToOther(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.shareToOther);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 第三方登录
	 */
	public void otherLogin(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.otherLogin);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 给我好评
	 */
	public void giveHighPraise(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.giveHighPraise);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改状态栏的背景颜色
	 */
	public void changeStatusBarBg(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.changeStatusBarBg);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取软件的版本名称
	 */
	public void getVersionName(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.getVersionName);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 网页切换
	 */
	public void pageSwitch(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.pageSwitch);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取下载的链接
	 */
	public void getDownLoadUrl(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.getDownLoadUrl);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 清除webView缓存，历史
	 */
	public void clearWebView(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.clearWebView);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 调用webService
	 */
	public void callWebService(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.callWebService);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 获取经纬度
	 */
	public void getLtaLng(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.getLtaLng);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 震动
	 */
	public void setVibrate(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.setVibrate);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送短信
	 */
	public void sendMsg(String json) {
		try {
			Message msg = mHandler.obtainMessage(JsConfig.sendMsg);
			msg.getData().putString("json", json);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case JsConfig.getPhoneInfo:
					try {
						String json = (String)msg.getData().getString("json");
						CallBackModel model= new Gson().fromJson(json, CallBackModel.class);

						UserInfoModel mUserInfoModel = new UserInfoModel();
						mUserInfoModel.systemType = "Android";
						mUserInfoModel.phoneType = DeviceInfoUtil.getPhoneModel();
						mUserInfoModel.NetworkIp = DeviceInfoUtil.getNetworkIp(mContext);
						String poi = mContext.pfUserInfo.getStrPreference("poi", "");
						if (poi.equals("")) {
							poi = mContext.pfUserInfo.getStrPreference("address", "");
						}
						mUserInfoModel.poi = poi;
						mUserInfoModel.longitude = mContext.pfUserInfo.getStrPreference("newlng", "");
						mUserInfoModel.latitude = mContext.pfUserInfo.getStrPreference("newlat", "");
						String phoneInfo = JSON.toJSONString(mUserInfoModel);

						if(model.data.callbackData == null || model.data.callbackData.equals("")){
							baseWebview.loadUrl("javascript:" + model.data.callbackMethodName + "(\'"+phoneInfo+"\');");
						} else {
							baseWebview.loadUrl("javascript:" + model.data.callbackMethodName
									+ "(\'"+phoneInfo+"\' , $.shagriReplace(\'"+model.data.callbackData+"\','all',false))");
						}
					} catch (Exception e3) {
					}
					break;
				case JsConfig.addData:
					try {
						// 保存key value键值对到数据库
						String json = (String)msg.getData().getString("json");
						AddDataModel model= new Gson().fromJson(json, AddDataModel.class);
						DBManager.getDBManagerInit(mContext).addContent(model.data.Key, model.data.Value);
					} catch (Exception e3) {
					}
					break;
				case JsConfig.selectDataByKey:
					try {
						// 通过key获取所有Value
						String json = (String)msg.getData().getString("json");
						GetDataModel model= new Gson().fromJson(json, GetDataModel.class);
						List<DatabaseModel> list2 = DBManager.getDBManagerInit(mContext).getModelListByKey(model.data.Key);

						String value="";
						if(list2.size()>0){
							value=list2.get(0).value;
						}
						if(model.data.callbackData == null || model.data.callbackData.equals("")){
							String s="javascript:" + model.data.callbackMethodName + "(\'"+value+"\')";
							Log.d("test",s);
							baseWebview.loadUrl(s);
						} else {

							String s="javascript:" + model.data.callbackMethodName + "(\'"+value+"\') , (\'"+model.data.callbackData+"\')";
							Log.d("test",s);
							baseWebview.loadUrl(s);
						}
					} catch (Exception e2) {
					}
					break;
				case JsConfig.dealHtmlFileContentAndCallBack:// 打开本地网页
					try {
						Bundle b = msg.getData();
						// 替换
						// 前端替换回来
						String jsString = b.getString("StringValue")
								.replace("\"", "@0@").replace("\'", "@1@")
								.replace("\r", "@2@").replace("\t", "@4@")
								.replace("\n", "@3@").replace("</script>", "<\\/script>");
						baseWebview.loadUrl("javascript:" + b.getString("callbackMethodName") + "(\'" + jsString + "\')");
						super.handleMessage(msg);
					} catch (Exception e2) {
					}
					break;
				case JsConfig.closeApp:// 关闭app对话框
					mContext.openCloseDialog();
					break;
				case JsConfig.call:// 打电话
					try {
						String json = (String)msg.getData().getString("json");
						// 通过key获取所有Value
						CallNumberModel number= new Gson().fromJson(json, CallNumberModel.class);
						//用intent启动拨打电话
						Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number.data.callNumber));
						mContext.startActivity(intent);
					} catch (Exception e1) {
					}
					break;
				case JsConfig.setJPushTag:// 设置推送的Alias
					try {
						String json = (String)msg.getData().getString("json");
						PushTagModel model2= new Gson().fromJson(json, PushTagModel.class);
						if(model2 != null && model2.data != null){
							// 登录   498CA281803F4DB3A05CD3670FBD1001
							// 注意：Alias 不能有横杠，改成大写
							String a = model2.data.Tag.replace("-", "").toUpperCase();
							JPushUtil.getJPushUtil(mContext).setAlias(a);
						} else {
							// 注销
							JPushUtil.getJPushUtil(mContext).setAlias("");
						}
					} catch (Exception e) {
					}
					break;
				case JsConfig.zoomImage:
					try {
						String json = (String)msg.getData().getString("json");
						// 图片的放大缩小
						ImageListModel model= new Gson().fromJson(json, ImageListModel.class);
						if(model != null){
							Intent intent1 = new Intent(mContext, ImagePagerActivity.class);
							// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
							intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, Integer.valueOf(model.data.postion));
							intent1.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, model.data.lists);
							mContext.startActivity(intent1);

							mContext.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_right);
						}
					} catch (Exception e) {
					}
					break;
				case JsConfig.executeJs: // 点击通知栏执行的JS方法
					try {
						String s22=msg.getData().getString("method");
						baseWebview.loadUrl("javascript: "+s22);
					} catch (Exception e) {
					}
					break;
				case JsConfig.openOhterActivity: //打开另外一个网页
					try {
						String json = (String)msg.getData().getString("json");
						WebUrlModel model= new Gson().fromJson(json, WebUrlModel.class);
						Intent intent = new Intent(mContext, OtherActivity.class);
						intent.putExtra("url", model.data.url);
						mContext.startActivity(intent);

//					mContext.shagriGoBack = true;
						mContext.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_right);
					} catch (Exception e1) {
					}
					break;
//				case JsConfig.openPlayActivity: //打开一个摄像头
//					try {
//						String json = (String)msg.getData().getString("json");
//						PlayModel model= new Gson().fromJson(json, PlayModel.class);
//						// 全屏
//						if(model != null && model.data.type == 1){
//							PlayActivity.startActivity(mContext, model, "");
//						}
//						// 半屏 + 网页
//						else if(model != null && model.data.type == 2 && model.data.url != null){
//							if(!tag){
//								JsMethodActivity.shagriGoBack = true;
//								VideoPlayActivity.startActivity(mContext, model);
//							} else {
//								if(ActivityManager.getInstance().getCurrActivity() instanceof VideoPlayActivity){
//									VideoPlayActivity v = (VideoPlayActivity)ActivityManager.getInstance().getCurrActivity();
//									v.startPlay(model);
//								} else {
//									Log.i("", "-------page切换-底部tab栏切换->还没有到视频网页=");
//								}
//							}
//						}
//						// 数据不正常的情况
//						else {
//							ShowUtil.showCenterToast(mContext, "数据错误，摄像头无法打开！", 1);
//							return;
//						}
//						mContext.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_right);
//					} catch (Exception e1) {
//					}
//					break;
				case JsConfig.helpTip: //帮助提示
					try {
						String json = (String)msg.getData().getString("json");
						HelpTipModel model= new Gson().fromJson(json, HelpTipModel.class);
						if(model != null){
							if(model.data.type.equals("1")){
								// Toast提示
								ShowUtil.showCenterToast(mContext, model.data.mssage, 3);
							} else {
								// 对话框提示
								mContext.helpTipDialog(model);
							}
						}
					} catch (Exception e1) {
					}
					break;
				case JsConfig.scanQrcode: //扫描二维码
					try {
						String json = (String)msg.getData().getString("json");
						mContext.mScanCallback = new Gson().fromJson(json, ScanCallback.class);

						// 去扫描二维码页面
//						Intent intent = new Intent(mContext, CustomCaptureActivity.class);
//						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//						intent.putExtra("titleName", mContext.mScanCallback.data.titleName);
//						intent.putExtra("scanHint", mContext.mScanCallback.data.scanHint);

//						mContext.startActivityForResult(intent, mContext.SCANQRCODE_RESULTCODE);

						IntentIntegrator intentIntegrator = new IntentIntegrator(mContext);
						intentIntegrator.addExtra("titleName",mContext.mScanCallback.data.titleName);
						intentIntegrator.addExtra("scanHint", mContext.mScanCallback.data.scanHint);
						// 设置自定义扫描Activity
						intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
						intentIntegrator.initiateScan();

//						mContext.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_right);
					} catch (Exception e1) {
					}
					break;
//				case JsConfig.shareToOther: //第三方分享
//					try {
//						String json = (String)msg.getData().getString("json");
//						ShareModel mShareModel= new Gson().fromJson(json, ShareModel.class);
//						if(mShareModel != null){
//							// 实例化并弹出分享框
//							mContext.initPopView(mShareModel);
//						}
//					} catch (Exception e1) {
//						String m = e1.getMessage();
//						Log.i("", "----m = "+m);
//					}
//					break;
//				case JsConfig.otherLogin: //第三方登录
//					try {
//						String json = (String)msg.getData().getString("json");
//						LoginModel model= new Gson().fromJson(json, LoginModel.class);
//						if(model != null){
//							// 初始化QQ登录
//							mContext.initLoginData();
//
//							// QQ登录
//							if (model.data.type == 1) {
//								mContext.qqLogin(model);
//							}
//							// 微信登录
//							else if (model.data.type == 2) {
//								mContext.wxLogin(model);
//							}
//							// 新浪登录
//							else if (model.data.type == 3) {
//								mContext.signAuthorize(model);
//							}
//						}
//					} catch (Exception e1) {
//					}
//					break;
				case JsConfig.giveHighPraise: //给我们好评
					try {
						String mAddress = "market://details?id="+ mContext.getPackageName();
						Intent marketIntent = new Intent("android.intent.action.VIEW");
						marketIntent.setData(Uri.parse(mAddress));
						mContext.startActivity(marketIntent);
					} catch (Exception e) {
					}
					break;
//				case JsConfig.changeStatusBarBg: //修改状态栏背景
//					try {
//						String json = (String)msg.getData().getString("json");
//						ChangeStatusBarBgModel model= new Gson().fromJson(json, ChangeStatusBarBgModel.class);
//						//修改状态栏背景
//						mContext.changeStatusBarBg(model.data.bgcolor, model.data.color);
//					} catch (Exception e) {
//					}
//					break;
				case JsConfig.getVersionName: // 获取软件的版本名称
					try {
						String json = (String)msg.getData().getString("json");
						VersionModel model= new Gson().fromJson(json, VersionModel.class);

						String versionName = VersionUtil.getVersionName(mContext);
						if(model.data.callbackData == null || model.data.callbackData.equals("")){
							baseWebview.loadUrl("javascript:" + model.data.callbackMethodName + "(\'"+versionName+"\');");
						} else {
							baseWebview.loadUrl("javascript:" + model.data.callbackMethodName + "(\'"+versionName+"\', shagri.replace(\'"+model.data.callbackData+"\','all',false))");
						}
					} catch (Exception e1) {
					}
					break;
//				case JsConfig.pageSwitch: // page切换-->JS回调到首页
//					try {
//						String json = (String)msg.getData().getString("json");
//						VideoModel model= new Gson().fromJson(json, VideoModel.class);
//
//						if(ActivityManager.getInstance().getCurrActivity() instanceof VideoPlayActivity){
//							VideoPlayActivity v = (VideoPlayActivity)ActivityManager.getInstance().getCurrActivity();
//							v.finishVideoPlay(model);
//
//							if(myApp != null && myApp.getMyWebview() != null){
//								if(model.data.callbackData == null || model.data.callbackData.equals("")){
//									myApp.getMyWebview().loadUrl("javascript:" + model.data.callbackMethodName + "(\'"+model.data.callbackData+"\');");
//								} else {
//									myApp.getMyWebview().loadUrl("javascript:" + model.data.callbackMethodName + "(\'"+model.data.callbackData+"\', shagri.replace(\'"+model.data.callbackData+"\','all',false))");
//								}
//							}
//						} else {
//							Log.i("", "-------page切换------当前页面不是VideoPlayActivity");
//						}
//					} catch (JsonSyntaxException e1) {
//					}
//					break;
//				case JsConfig.getDownLoadUrl: // 获取下载链接
//					try {
//						String json = (String)msg.getData().getString("json");
//						DownLoadModel model= new Gson().fromJson(json, DownLoadModel.class);
//						if(model != null){
//							mContext.startDownLoad(model.data.url, model.data.fileName);
//						}
//					} catch (JsonSyntaxException e1) {
//					}
//					break;
				case JsConfig.clearWebView:
					baseWebview.clearCache(true);
					baseWebview.clearHistory();
					ShowUtil.showCenterToast(mContext, "清理完毕", 3);
					//baseWebview.reload();
					break;
				case JsConfig.callWebService:
					try {
						//
						String json = (String)msg.getData().getString("json");
						CallWebServiceModel model= new Gson().fromJson(json, CallWebServiceModel.class);
						String str = model.data.url;
//					List<CallWebServiceModel.Model> list1 = model.data.pars;
//					CallWebServiceModel webServiceModel= new CallWebServiceModel();
//					webServiceModel.data.url = model.data.url;
//					webServiceModel.data.method = model.data.method;
//					webServiceModel.data.pars = list1;
//					webServiceModel.data.callbackData = model.data.callbackData;
//					webServiceModel.data.callbackMethodName = model.data.callbackMethodName;
						String callbackWebServiceString = JSON.toJSONString(model);

						if(model.data.callbackData == null || model.data.callbackData.equals("")){


							String s="javascript:" + model.data.callbackMethodName + "(\'"+callbackWebServiceString+"\')";
							Log.d("test",s);
							baseWebview.loadUrl(s);
						} else {

							String s="javascript:" + model.data.callbackMethodName + "(\'"+callbackWebServiceString+"\') , (\'"+model.data.callbackData+"\')";
							Log.d("test",s);
							baseWebview.loadUrl(s);
						}
					} catch (Exception e2) {
					}
					break;
				case JsConfig.getLtaLng:
					try {
						String json = (String)msg.getData().getString("json");
						CallBackModel model= new Gson().fromJson(json, CallBackModel.class);

						UserInfoModel mUserInfoModel = new UserInfoModel();
						mUserInfoModel.longitude = mContext.pfUserInfo.getStrPreference("newlng", "");
						mUserInfoModel.latitude = mContext.pfUserInfo.getStrPreference("newlat", "");
						String ltaLng = JSON.toJSONString(mUserInfoModel);

						if(model.data.callbackData == null || model.data.callbackData.equals("")){
							baseWebview.loadUrl("javascript:" + model.data.callbackMethodName + "(\'"+ltaLng+"\');");
						} else {
							baseWebview.loadUrl("javascript:" + model.data.callbackMethodName
									+ "(\'"+ltaLng+"\' , $.shagriReplace(\'"+model.data.callbackData+"\','all',false))");
						}
					} catch (Exception e3) {
					}
					break;
				case JsConfig.setVibrate:
					/*
					 * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
					 */
					vibrator = (Vibrator)mContext.getSystemService(mContext.VIBRATOR_SERVICE);
//		        long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
					vibrator.vibrate(100);           //重复两次上面的pattern 如果只想震动一次，index设为-1
					break;
				case JsConfig.sendMsg:
					String json = (String)msg.getData().getString("json");
					SendMsgModel model= new Gson().fromJson(json, SendMsgModel.class);
					SendMessage.sendSmsWithBody(
							mContext,model.data.callNumber,model.data.content);

					break;
				case JsConfig.setHomePage:
					String homePageJsonStr = (String)msg.getData().getString("json");
					HomePageModel homePageModel= new Gson().fromJson(homePageJsonStr, HomePageModel.class);
					sp = mContext.getSharedPreferences("url",mContext.MODE_PRIVATE);
					Editor editor = sp.edit();//获取编辑器
					editor.putString("url", homePageModel.data.url);
					editor.commit();//提交修改
			}
		}
	};
	
	
}
