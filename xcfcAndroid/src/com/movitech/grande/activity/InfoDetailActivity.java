package com.movitech.grande.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.sina.weibo.SinaWeibo.ShareParams;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.Constant;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.image.RoundBitmap;
import com.movitech.grande.image.ImageDownLoader.onImageLoaderListener;
import com.movitech.grande.model.XcfcInfoDetail;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.BaseResult;
import com.movitech.grande.net.protocol.XcfcInfoDetailResult;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.haerbin.R;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 26, 2014 9:00:16 PM
 * @Description: This is David Wu's property.
 *
 **/
@EActivity(R.layout.activity_info_detail)
public class InfoDetailActivity extends BaseActivity {
	private static final int SHARE_COUNT = 5;
	private static final float ROUND_BITMAP = 10;
	private String[] shareText = null;
	private static final int[] SHARE_DRAWABLE_ICON = {
			R.drawable.btn_weixin_hy, R.drawable.btn_weixin_pyq,
			R.drawable.btn_sina, R.drawable.btn_qq, R.drawable.btn_sms };
	private String filePath;
	private String IMAGE_FILE_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/share_info";
	
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_info_detail)
	ImageView ivInfoDetail;
	@ViewById(R.id.txt_info_time)
	TextView txtInfoTime;
	@ViewById(R.id.txt_info_context)
	TextView txtInfoContext;
	@ViewById(R.id.txt_info_title)
	TextView txtInfoTitle;
	@ViewById(R.id.iv_time_clock)
	ImageView txtTimeClock;

	@ViewById(R.id.iv_share)
	ImageView ivShare;
	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	
	
	@App
	MainApp mApp;

	@Extra
	String infoId;

	XcfcInfoDetail detail;

	ProcessingDialog processingDialog = null;
	
	ImageDownLoader loader = null;
	Bitmap bitmap = null;
	Dialog shareDialog = null;
	GridView gridView = null;
	boolean sinaBack = false;
	@AfterViews
	void afterViews() {
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				BackgroundExecutor.cancelAll("queryData", false);
				//InfoDetailActivity.this.finish();
			}
		});
		processingDialog.show();

		Utils.debug(Utils.TAG, "infoID = " + infoId);
		// 载入数据
		doLoadDataAndBindData();
		
		initData();
	}
	
	private void initData(){
		shareText = getResources().getStringArray(R.array.share_platform);
	}

	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

	@Background(id="queryData")
	void doLoadDataAndBindData() {
		//XcfcInfoDetailResult result = netHandler.postForGetInfoDetailResult(infoId, mApp.getCurrUser() == null ? mApp.getGuestId() : mApp.getCurrUser().getId());
		XcfcInfoDetailResult result = netHandler.postForGetInfoDetailResult(infoId, mApp.getCurrUser().getId());
		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}

		detail = result.getInfoDetail();

		goBackMainThread(result.getResultMsg(), true);
	}

	private void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(this, msg);

		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		if (detail == null)
			return;

		txtInfoTitle.setText(detail.getTitle());
		txtInfoTime.setText(detail.getCreateTime());
		txtTimeClock.setVisibility(View.VISIBLE);
		try {
			if (detail.getPicsrc() != null && !detail.getPicsrc().equals("")) {
				imageUtils.loadInfoDetailImage(detail.getPicsrc(), ivInfoDetail);
				/*loader = new ImageDownLoader(context);
				bitmap = loader.downloadImage(detail.getPicsrc(),new onImageLoaderListener() {
							@Override
							public void onImageLoader(Bitmap bitmap, String url) {
								if (ivInfoDetail != null && bitmap != null) {
									ivInfoDetail.setImageBitmap(RoundBitmap
											.getRoundedBitmap(bitmap, ROUND_BITMAP));
								}
							}
						}, true);
				if (bitmap != null && ivInfoDetail.getDrawable() == null) {
					ivInfoDetail.setImageBitmap(RoundBitmap.getRoundedBitmap(bitmap, ROUND_BITMAP));
					saveBitmap(bitmap);
				}
			}else 
				ivInfoDetail.setVisibility(View.GONE);*/
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		txtInfoContext.setText(Html.fromHtml(detail.getContext()));

		dismissProcessingDialog();
	}

	@Click
	void ivBack() {
		this.finish();
	}
	
	/**
	 * 分享
	 */
	@Click
	void ivShare() {
		ShareSDK.initSDK(this);
		createShareDialog();
	}
	
	
	@SuppressWarnings("deprecation")
	private void createShareDialog() {
		shareDialog = new Dialog(context, R.style.add_dialog);
		shareDialog.setContentView(R.layout.activity_build_detail_share_dialog);
		gridView = (GridView) shareDialog.findViewById(R.id.gv_share);
		SimpleAdapter adapterSimple;
		ArrayList<HashMap<String, Object>> listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < SHARE_COUNT; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("image", SHARE_DRAWABLE_ICON[i]);
			map.put("txt", shareText[i]);
			listItems.add(map);
		}
		String[] from = { "image", "txt" };
		int[] to = { R.id.iv_share, R.id.tv_share };
		adapterSimple = new SimpleAdapter(this, listItems,
				R.layout.item_gridview_share, from, to);
		gridView.setAdapter(adapterSimple);
		Window window = shareDialog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		shareDialog.getWindow().setAttributes(lp);
		shareDialog.show();
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg3) {
				switch (position) {
				case 0:
					shareWeixinFriend();
					break;
				case 1:
					shareWeixinMoments();
					break;
				case 2:
					shareSinaWeibo();
					break;
				case 3:
					tencentWeibo();
					break;
				case 4:
					shareSMS();
					break;
				default:
					break;
				}
			}
		});
	}

	private void shareSinaWeibo() {
		ShareParams sp = new ShareParams();
		//sp.setTitle(detail.getTitle());
		sp.setText(detail.getTitle() + Constant.SHARE_URL);
		sp.setImageUrl(detail.getPicsrc());
		Platform weibo = ShareSDK.getPlatform(context, SinaWeibo.NAME);
		weibo.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				Utils.toastMessageForce(InfoDetailActivity.this,getString(R.string.share_error));
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				// TODO Auto-generated method stub
				//shareIntegral();
			  // Utils.toastMessageForce(InfoDetailActivity.this,getString(R.string.share_success));
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				Utils.toastMessageForce(InfoDetailActivity.this,getString(R.string.share_cancel));
			}
		}); // 设置分享事件回调
		// 执行图文分享
		weibo.share(sp);
		sinaBack = true;
	}

	private void shareWeixinFriend() {
		ShareParams sp = new ShareParams();
		//sp.setText(detail.getTitle() + Constant.SHARE_URL);
		//sp.setImageUrl(detail.getPicsrc());
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setTitle(detail.getTitle());
		//sp.setText(detail.getContext());
		sp.setUrl(Constant.SHARE_URL);
		sp.setImageUrl(detail.getPicsrc());
		Platform wechat = ShareSDK.getPlatform(context, Wechat.NAME);
		wechat.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				Utils.toastMessageForce(InfoDetailActivity.this, getString(R.string.share_error));
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				//shareIntegral();
				//Utils.toastMessageForce(InfoDetailActivity.this,getString(R.string.share_success));

			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub
			}
		}); // 设置分享事件回调
		// 执行图文分享
		wechat.share(sp);
	}

	private void shareWeixinMoments() {
		ShareParams sp = new ShareParams();
		//sp.setText(detail.getTitle() + Constant.SHARE_URL);
		//sp.setImageUrl(detail.getPicsrc());
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setTitle(detail.getTitle());
		
		//sp.setText(detail.getContext());
		
		sp.setUrl(Constant.SHARE_URL);
		sp.setImageUrl(detail.getPicsrc());
		Platform weMoment = ShareSDK.getPlatform(context, WechatMoments.NAME);
		weMoment.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				Utils.toastMessageForce(InfoDetailActivity.this, getString(R.string.share_error));
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				//shareIntegral();
				//Utils.toastMessageForce(InfoDetailActivity.this, getString(R.string.share_success));
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {

			}
		}); // 设置分享事件回调
		// 执行图文分享
		weMoment.share(sp);
	}

	private void tencentWeibo() {
		ShareParams sp = new ShareParams();
		sp.setText(detail.getTitle() + Constant.SHARE_URL);
		
		sp.setImagePath(filePath);	
		Platform tencentWeibo = ShareSDK
				.getPlatform(context, TencentWeibo.NAME);
		tencentWeibo.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				Utils.toastMessageForce(InfoDetailActivity.this, getString(R.string.share_error));
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				//shareIntegral();
				//Utils.toastMessageForce(InfoDetailActivity.this, getString(R.string.share_success));
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {

			}
		}); // 设置分享事件回调
		// 执行图文分享
		tencentWeibo.share(sp);
	}

	private void shareSMS() {
		ShareParams sp = new ShareParams();
		sp.setText(detail.getTitle() + Constant.SHARE_URL);
		Platform sms = ShareSDK.getPlatform(context, ShortMessage.NAME);
		sms.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {

			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				//shareIntegral();
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {

			}
		}); // 设置分享事件回调
		// 执行图文分享
		sms.share(sp);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	
	
	
	private void saveBitmap(Bitmap bitmap) {
		FileOutputStream b = null;
		File file = new File(IMAGE_FILE_PATH);		
		file.mkdirs();
		filePath = IMAGE_FILE_PATH + "share_info" + ".jpg";
		try {
			b = new FileOutputStream(filePath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				b.flush();
				b.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void onDestroy() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.isRecycled();
		}
		super.onDestroy();
		System.gc();
	}
	
	/*private void shareIntegral(){
		if (mApp.getCurrUser() != null) {
			doLoadShareIntegral();
		}
	}*/
	
	
	@Background
	void doLoadShareIntegral(){
		BaseResult result = netHandler.postForGetShareIntegral(mApp.getCurrUser().getId(), infoId);
		if (result == null || result.getResultSuccess() == false) {
			goBackMainShareThread("", false);
			return;
		}
		
		goBackMainShareThread(result.getResultMsg(), true);
	}
    @UiThread
    void goBackMainShareThread(String msg, boolean success) {
    	sinaBack = false;
		if (success) {
			Utils.toastMessageForce(InfoDetailActivity.this, msg);
		}
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
    	/*if (sinaBack) {//sina微博分享绕开审核机制下的处理
    		shareIntegral();
		}*/
    }

}
