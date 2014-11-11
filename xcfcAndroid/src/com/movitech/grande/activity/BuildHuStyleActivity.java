package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.image.ImageDownLoader.onImageLoaderListener;
import com.movitech.grande.model.XcfcBuildHuStyle;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcHuStyleResult;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.ImagePagerZoomActivity_;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月18日 上午9:21:43
 * 类说明
 */
@EActivity(R.layout.activity_build_detail_hustyle)
public class BuildHuStyleActivity extends BaseActivity{
  
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	String itemId;
	String houseType;
	
	ProcessingDialog processingDialog = null;
	
	XcfcBuildHuStyle[] huStyleItems = null;
	
	ImageDownLoader downLoader = null;
	String huStylePicContent;
	@AfterViews
	void afterViews(){
		itemId = getIntent().getStringExtra("itemId");
		doLoadDataHuStyle();
		showProcessDialog();
	}
	
	@Background
	void doLoadDataHuStyle() {
		XcfcHuStyleResult huStyleResult = netHandler.postForGetHuStyle(itemId);
		
		if (null == huStyleResult) {
			
			goBackMainThread(getString(R.string.error_server_went_wrong),
					false);
			return;
		}
		
		if (huStyleResult.getResultSuccess() == false) {
			goBackMainThread(huStyleResult.getResultMsg(), false);
			return;
		}
		
		huStyleItems = huStyleResult.getHuStyleItems();
		goBackMainThread("", true);
	}
	
	private void goBackMainThread(String string, boolean success) {
		dismissProcessingDialog();
        if (success) {
			doBindDataHuStyle();
		}
	}

	@UiThread
	void doBindDataHuStyle() {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layoutParams.setMargins(0,
				getResources().getDimensionPixelSize(R.dimen.dp_five), 0, 0);
		LinearLayout ll = (LinearLayout)findViewById(R.id.ll_images_group);
		LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		TextView tvPicContent = new TextView(context);
		downLoader = new ImageDownLoader(context);
		for (int i = 0; i < huStyleItems.length; ++i) {
			/*if (huStyleItems[i].getHouseType().equals("3")) {
				huStylePicContent = huStyleItems[i].getAttachId();
			}*/
			final ImageView imageView = new ImageView(context);
			imageView.setAdjustViewBounds(true);
			imageView.setMaxHeight(getResources().getDimensionPixelSize(R.dimen.length_largest));
			imageView.setMaxWidth(getResources().getDimensionPixelSize(R.dimen.length_largest));
			imageView.setScaleType(ScaleType.FIT_XY);
			Bitmap bitmap = null;
			if (huStyleItems[i].getAttach() != null){
				final String picUrl = huStyleItems[i].getAttach().getUname();
				bitmap = downLoader.downloadImage(picUrl, new onImageLoaderListener() {
					
					@Override
					public void onImageLoader(Bitmap bitmap, String url) {
						if (imageView != null && bitmap != null) {
							imageView.setImageBitmap(bitmap);
						}
					}
				}, true);
				
				if (bitmap != null && imageView.getDrawingCache() == null) {
					imageView.setImageBitmap(bitmap);
				}
				
				ll.addView(imageView, layoutParams);
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(BuildHuStyleActivity.this,
								ImagePagerZoomActivity_.class);
						intent.putExtra("picUrl", picUrl);
						startActivity(intent);
					}
				});
			}
		}
		tvPicContent.setText(huStylePicContent);
		ll.addView(tvPicContent, tvLayoutParams);	
	}

	
	@Click
	void ivBack(){
		this.finish();
	}
	
	/**
	 * 显示进度条
	 */
	private void showProcessDialog() {
		processingDialog = new ProcessingDialog(context, true,
				new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {

					}
				});
		processingDialog.show();
	}
	
	/**
	 * 关闭进度条
	 */
	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}
	
}
