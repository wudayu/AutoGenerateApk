package com.movitech.grande.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.ViewPagerAdapter;
import com.movitech.grande.constant.Constant;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.HouseDetailImageSourceType;
import com.movitech.grande.constant.Rolling;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.image.ImageDownLoader.onImageLoaderListener;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.model.XcfcHouseDetailItems;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcFavBuildResult;
import com.movitech.grande.net.protocol.XcfcHousesDetailResult;
import com.movitech.grande.net.protocol.XcfcIsCollectResult;
import com.movitech.grande.utils.Formatter;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.views.CirclePageIndicator;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.BuildHuStyleActivity_;
import com.movitech.grande.activity.BuildMapActivity_;
import com.movitech.grande.activity.LoginActivity_;
import com.movitech.grande.activity.MainActivity_;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月12日 下午5:32:39 类说明
 */
@EActivity(R.layout.activity_build_detail)
public class BuildDetailActivity extends BaseActivity {
	//图片的种类
	private static final int[] TYPES = {
		HouseDetailImageSourceType.HOUSES_TYPE,
		HouseDetailImageSourceType.HOUSES_EFFECT,
		HouseDetailImageSourceType.HOUSES_TRAFFIC,
		HouseDetailImageSourceType.HOUSES_SIGHT,
		HouseDetailImageSourceType.HOUSES_SAMPLE };
	
	private String huStyleType = "3";
	private static final int SHARE_COUNT = 5;
	private String[] shareText = null;
	//	
	private static final int[] SHARE_DRAWABLE_ICON = {
		R.drawable.btn_weixin_hy, R.drawable.btn_weixin_pyq,
			R.drawable.btn_sina, R.drawable.btn_qq, R.drawable.btn_sms };
	private String filePath;
	private String IMAGE_FILE_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/grande";
	//设置显示的文本行数
	private static final int TEXTLINE = 4;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.iv_fav)
	ImageView ivFav;
	@ViewById(R.id.iv_share)
	ImageView ivShare;

	@ViewById(R.id.vp_banner_build_pic)
	BaseViewPager vpBannerBuildPic;
	@ViewById(R.id.circle_indicator)
	CirclePageIndicator circleIndicator;

	@ViewById(R.id.txt_commission)
	TextView txtCommission;
	@ViewById(R.id.btn_recommend_immediately)
	Button btnRecommendImmediately;
	@ViewById(R.id.btn_appointment_immediately)
	Button btnAppointmentImmediately;

	@ViewById(R.id.tv_price)
	TextView tvPrice;
	@ViewById(R.id.txt_name_houses)
	TextView txtNameHouses;
	@ViewById(R.id.txt_section_houses)
	TextView txtSectionHouses;
	@ViewById(R.id.txt_houses_synopsis)
	TextView txtHousesSynopsis;// 特色
	@ViewById(R.id.txt_privilege_houses)
	TextView txtPrivilegeHouses;
	@ViewById(R.id.txt_price_houses)
	TextView txtPriceHouses;
	@ViewById(R.id.txt_recommend_num)
	TextView txtRecommendNum;
	@ViewById(R.id.txt_appointment_num)
	TextView txtAppointmentNum;
	@ViewById(R.id.txt_privilege_date)
	TextView txtPrivilegeDate;

	@ViewById(R.id.ll_introduce_build)
	LinearLayout llIntroduceBuild;
	@ViewById(R.id.ll_introduce_fold)
	LinearLayout llIntroduceFold;
	@ViewById(R.id.iv_introduce_fold)
	ImageView ivIntroduceFold;
	
	@ViewById(R.id.tv_open_date)
	TextView tvOpenDate;
	@ViewById(R.id.tv_build_special)
	TextView tvBuildSpecial;
	@ViewById(R.id.tv_build_type)
	TextView tvBuildType;
	@ViewById(R.id.tv_decoration_situation)
	TextView tvDecorationSituation;
	@ViewById(R.id.tv_build_area)
	TextView tvBuildArea;
	@ViewById(R.id.tv_equity)
	TextView tvEquity;
	@ViewById(R.id.tv_rate_green)
	TextView tvRateGreen;
	@ViewById(R.id.tv_realestate_cto)
	TextView tvRealestateCto;
	@ViewById(R.id.tv_delivery_date)
	TextView tvDeliveryDate;

	//@ViewById(R.id.bmap_view)
	//MapView bmapView;
	@ViewById(R.id.iv_map)
	ImageView ivMap;
	@ViewById(R.id.txt_map_area)
	TextView txtMapArea;

	@ViewById(R.id.tv_traffic)
	TextView tvTraffic;
	@ViewById(R.id.ll_traffic_build)
	LinearLayout llTrafficBuild;
	@ViewById(R.id.ll_traffic_fold)
	LinearLayout llTrafficFold;
	@ViewById(R.id.iv_traffic_fold)
	ImageView ivTrafficFold;
	

	@ViewById(R.id.ll_hustyle_pic)
	LinearLayout llHustylePic;
	
	@ViewById(R.id.ll_phone)
	LinearLayout llPhone;
	@ViewById(R.id.txt_phone)
	TextView txtPhone;
	
	@ViewById(R.id.bmap_view)
	MapView bmapView;
	
	@Bean(NetHandler.class)
	INetHandler netHandler;
	
	@App
	MainApp mApp;

	XcfcHouseDetail houseDetail = null;
	String buildingId = "";
	boolean isFavFlag = false;// 该楼盘是否收藏的标记
	boolean introduceFold = true;//楼盘介绍是否折叠
	boolean trafficFold = true;//交通状况是否折叠
	ProcessingDialog processingDialog = null;
	Dialog shareDialog = null;
	
	BaiduMap mBaiduMap = null;
	GridView shareGridView = null;
	int tvBuildSpecialLineCount;
	int tvTrafficLineCount;
	int bannerCount;
	
	String sharePicUrl;
    String mapStaticPicUrl = "http://api.map.baidu.com/staticimage?";//百度地图静态bitmap访问接口
    ImageDownLoader downLoader = null;
    @Bean(ImageUtils.class)
	IImageUtils imageUtils;
    
	@AfterViews
	void afterViews() {
		shareText = getResources().getStringArray(R.array.share_platform);
		Intent intent = getIntent();
		buildingId = intent.getStringExtra("id");
		downLoader = new ImageDownLoader(context);
		showProcessDialog();
		doLoadDataAndBindData();
		doLoadIsCollect();
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
	
	/**
	 * 网络访问：是否收藏该楼盘
	 */
	@Background
	void doLoadIsCollect() {
		/*if (mApp.getCurrUser() == null) {
			return;
		}*/
		if (!mApp.isLogin()) {
			return;
		}
		XcfcIsCollectResult isCollectResult = netHandler.postForGetIsCollect(
				mApp.getCurrUser().getId(), buildingId);
		if (null == isCollectResult) {
			goBackMainIsCollect(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (isCollectResult.getResultSuccess() == false) {
			goBackMainIsCollect(isCollectResult.getResultMsg(), false);
			return;
		}
		if (isCollectResult.isObjValue()) {
			goBackMainIsCollect("", true);
		}
	}

	@UiThread
	void goBackMainIsCollect(String msg, boolean succeed) {
		if (succeed) {
			isFavFlag = true;
			ivFav.setBackgroundResource(R.drawable.btn_like_ok);
		}
	}
	/**
	 * 加载楼盘详情信息
	 */
	@Background(id = "queryData")
	void doLoadDataAndBindData() {
		/*XcfcHousesDetailResult rst = netHandler.postForGetHousesDetailResult(
				buildingId, mApp.getCurrUser() == null ? mApp.getGuestId()
						: mApp.getCurrUser().getId());*/
		System.out.println("buildingId---------------" + buildingId);
		System.out.println("mApp.getCurrUser().getId()-------------" +mApp.getCurrUser().getId());
		XcfcHousesDetailResult rst = netHandler.postForGetHousesDetailResult(buildingId, mApp.getCurrUser().getId());
		if (null == rst) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (rst.getResultSuccess() == false) {
			goBackMainThread(rst.getResultMsg(), false);
			return;
		}
		houseDetail = rst.getHouseDetail();

		Utils.debug(Utils.TAG, houseDetail.toString());
		goBackMainThread(rst.getResultMsg(), true);
	}

	public void cancelQueryData() {
		BackgroundExecutor.cancelAll("queryData", false);
	}
	@UiThread
	void goBackMainThread(String msg, boolean success) {
		dismissProcessingDialog();
		if (success) {
			initSlideViewPage();
			bindDataBuildName();
			bindDataBuildIntroduce();
			bindStaticMap();
			bindDataBuildTraffic();
			setHustylePic();
			bindDataPhone();
		}
	}
	
	
	@Background
	void doLoadFavBuild(String isLike) {
		XcfcFavBuildResult xcfcFavBuildResult = netHandler.postForGetFavBuild(
				mApp.getCurrUser().getId(), buildingId, isLike, "0");
		if (null == xcfcFavBuildResult) {
			goBackMainThreadFav(getString(R.string.error_server_went_wrong),
					false, isLike);
			return;
		}
		if (xcfcFavBuildResult.getResultSuccess() == false) {
			goBackMainThreadFav(xcfcFavBuildResult.getResultMsg(), false,
					isLike);
			return;
		}
		goBackMainThreadFav("", true, isLike);
	}

	@UiThread
	void goBackMainThreadFav(String msg, boolean success, String isLike) {
		if (success) {
			if (isLike.equals("0")) {
				isFavFlag = true;
				ivFav.setBackgroundResource(R.drawable.btn_like_ok);
			} else if (isLike.equals("1")) {
				isFavFlag = false;
				ivFav.setBackgroundResource(R.drawable.btn_like);
			}
		}
	}
	@Click
	void ivBack() {
		this.finish();
	}
	
	@Click
	void ivFav() {
		/*if (mApp.getCurrUser() == null) {
			LoginActivity_.intent(this).start();
			return;
		}*/
		if (!mApp.isLogin()) {
			LoginActivity_.intent(this).start();
			return;
		}
		if (!isFavFlag) {
			doLoadFavBuild("0");
		} else {
			// doLoadCancelFavBuild();
			doLoadFavBuild("1");
		}

	}
	
	@Click
	void ivShare(){
		ShareSDK.initSDK(this);
		createShareDialog();
	}
	@Click
	void llPhone() {
		if (houseDetail == null) {
			return;
		}
		String phoneNumber = houseDetail.getHotline();
		Uri uri = Uri.parse("tel:" + phoneNumber);
		try {
			Intent intent = new Intent(Intent.ACTION_DIAL, uri);
			startActivityForResult(intent, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Utils.toastMessage(this, getString(R.string.tips_cannot_tail));
		}
	}
	@Click
	void btnRecommendImmediately() {
		cancelQueryData();
		finish();
		Intent intent = new Intent(context, MainActivity_.class);
		intent.putExtra(ExtraNames.JUMP_FROM_RECOMM, houseDetail);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		
	}

	@Click
	void btnAppointmentImmediately() {
		cancelQueryData();
		finish();
		Intent intent = new Intent(context, MainActivity_.class);
		intent.putExtra(ExtraNames.JUMP_FROM_RECOMM, houseDetail);
		intent.putExtra(ExtraNames.JUMP_TO_APPOINT, true);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	@Click 
	void bmapView() {
		
	}
	
	@Click
	void llIntroduceFold(){
		if (introduceFold) {
			llIntroduceBuild.setVisibility(View.VISIBLE);
			tvBuildSpecial.setLines(tvBuildSpecialLineCount);
			ivIntroduceFold.setBackgroundResource(R.drawable.btn_fold);
			introduceFold = false;
		} else {
			llIntroduceBuild.setVisibility(View.GONE);
			if (tvBuildSpecialLineCount > TEXTLINE) {
				tvBuildSpecial.setLines(TEXTLINE);
			}
			ivIntroduceFold.setBackgroundResource(R.drawable.btn_unfold);
			introduceFold = true;
		}
	}
	
	@Click 
	void llTrafficFold(){
		if (trafficFold) {
			tvTraffic.setLines(tvTrafficLineCount);
			ivTrafficFold.setBackgroundResource(R.drawable.btn_fold);
			trafficFold = false;
		} else {
			if (tvTrafficLineCount > TEXTLINE) {
				tvTraffic.setLines(TEXTLINE);
			}
			ivTrafficFold.setBackgroundResource(R.drawable.btn_unfold);
			trafficFold = true;
		}
	}
	boolean touching = false;
	private void initSlideViewPage() {
		List<View> views = new ArrayList<View>();
		List<String> effectPicAddress = houseDetail.getImageAddressesBySourceType(TYPES[1]);
		if (effectPicAddress == null ) {
			return;
		}
		bannerCount = effectPicAddress.size();
		if (bannerCount > 0 ) {
			sharePicUrl = effectPicAddress.get(0);
		}
		for (int i = 0; i < bannerCount; ++i) {
			String url = effectPicAddress.get(i);
			final View vg = LayoutInflater.from(context).inflate(R.layout.layout_build_detail_banner, null);
			final RelativeLayout llPush = (RelativeLayout) vg.findViewById(R.id.rl_push);
			final ImageView imageViewBanner = (ImageView) vg.findViewById(R.id.iv_publicity);
			llPush.setVisibility(View.GONE);
			Bitmap bitmap = null;
			bitmap = downLoader.downloadImage(url, new onImageLoaderListener() {
				@Override
				public void onImageLoader(Bitmap bitmap, String url) {
					if (imageViewBanner != null && bitmap != null) {
						imageViewBanner.setImageBitmap(bitmap);
					}
				}
			}, true);
			if (bitmap != null) {
				imageViewBanner.setImageBitmap(bitmap);
			}
            if (i==0) {
			    saveBitmap(bitmap);
			}
			//imageUtils.loadHouseBannerImage(url, imageViewBanner);
			vg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});

			views.add(vg);
		}

		ViewPagerAdapter adapter = new ViewPagerAdapter();
		adapter.init();
		adapter.addAll(views);
		vpBannerBuildPic.setAdapter(adapter);
		circleIndicator.setViewPager(vpBannerBuildPic);
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if (!touching)
					switchPage();
			}
		}, 0, Rolling.ROLLING_BREAK);
		vpBannerBuildPic.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					touching = true;
					break;
				case MotionEvent.ACTION_UP:
					touching = false;
					break;
				}
				return false;
			}
		});
	}
	@UiThread
	void switchPage() {
		if (bannerCount == 0) {
			return;
		}
		vpBannerBuildPic.setCurrentItem((vpBannerBuildPic.getCurrentItem() + 1) % bannerCount);
	}
	
	/**
	 * 设置楼盘名部分的信息
	 */
	private void bindDataBuildName(){
		txtCommission.setText(houseDetail.getRatio());
		txtNameHouses.setText(houseDetail.getName());
		txtSectionHouses.setText(Formatter.formatSection(houseDetail.getArea()));
		txtHousesSynopsis.setText(houseDetail.getSalePoint());
		txtPrivilegeHouses.setText(houseDetail.getDiscount());
		if (houseDetail.getPricePerSuiteScope().equals("")) {
			tvPrice.setText(context.getResources().getString(R.string.hint_average_price));
			txtPriceHouses.setText(houseDetail.getPrice() + context.getResources().getString(R.string.str_square_unit));
		}else if (houseDetail.getPrice().equals("")) {
			tvPrice.setText(context.getResources().getString(R.string.hint_total_price));
			txtPriceHouses.setText(houseDetail.getPricePerSuiteScope() + context.getResources().getString(R.string.str_square_total_unit));
		}
		txtRecommendNum.setText(getSpannable(houseDetail.getRecommendedNum() + getString(R.string.txt_num)));
		txtAppointmentNum.setText(getSpannable(houseDetail.getBespeakNum()+ getString(R.string.txt_people)) );
		txtPrivilegeDate.setText(houseDetail.getDiscountEndTime());
	}
	
	/**
	 * 设置特定位置的字体的颜色与大小
	 * @param spannableString 要设置的字符串
	 * @return 
	 */
	private SpannableString getSpannable(String spannableString){
		SpannableString spannable = new SpannableString(spannableString);
		spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF5712")), 0, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spannable.setSpan(new RelativeSizeSpan(1.2f), 0, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;		
	}
	
	/**
	 *楼盘介绍
	 */
	private void bindDataBuildIntroduce() {
		if (houseDetail == null) {
			return;
		}
		tvOpenDate.setText(houseDetail.getSaleStartAt());
		tvBuildSpecial.setText(houseDetail.getCharacteristic());
		tvBuildSpecial.post(new Runnable() {
			@Override
			public void run() {
				tvBuildSpecialLineCount = tvBuildSpecial.getLineCount();
				if (tvBuildSpecialLineCount > TEXTLINE) {
					tvBuildSpecial.setLines(TEXTLINE);
				}
			}
		});
		tvBuildType.setText(houseDetail.getBuildingType());
		tvDecorationSituation.setText(houseDetail.getFitmentLevelName());//装修类型
		tvBuildArea.setText(houseDetail.getBuildingArea());
		tvEquity.setText(houseDetail.getPropertyRightsName());//产权
		tvRateGreen.setText(houseDetail.getGreeningRate());
	    tvRealestateCto.setText(houseDetail.getPropertyCompany());
		tvDeliveryDate.setText(houseDetail.getDeliveryTime());
	}
	
	/**
	 * 加载静态地图（bitmap）
	 */
	private void bindStaticMap() {
		String centerPoint = houseDetail.getAddressX()+"," + houseDetail.getAddressY();
		int width = ivMap.getWidth();
		int height = ivMap.getHeight();
		if (width > 1000) {
			width = 1000;
		}if (height > 1000) {
			height = 1000;
		}
		int zoom = 10;
		int scale = 1;
		String mapUrl = String.format(mapStaticPicUrl + "center=%s&width=%d&height=%d&zoom=%d&scale=%d", centerPoint, width, height, zoom, scale);
		/*Bitmap bitmap = downLoader.downloadImage(mapUrl, new onImageLoaderListener() {
			@Override
			public void onImageLoader(Bitmap bitmap, String url) {
				if (bitmap != null) {
					ivMap.setImageBitmap(bitmap);
				}
			}
		}, true);
		if (bitmap != null) {
			ivMap.setImageBitmap(bitmap);
		}
		*/
		imageUtils.loadInfoImage(mapUrl, ivMap);	
		txtMapArea.setText(houseDetail.getAddress());
	}
	
	@Click 
	void ivMap() {
		Intent intent = new Intent(BuildDetailActivity.this, BuildMapActivity_.class);
		intent.putExtra("longitude", houseDetail.getAddressX());
		intent.putExtra("latitude", houseDetail.getAddressY());
		intent.putExtra("buildName", houseDetail.getName());
		startActivity(intent);
	}
	
	/**
	 * 交通状况
	 */
	private void bindDataBuildTraffic() {
		tvTraffic.setText(houseDetail.getTrafficCfg());
		tvTraffic.post(new Runnable() {
			@Override
			public void run() {
			    tvTrafficLineCount = tvTraffic.getLineCount();
				if (tvTrafficLineCount > TEXTLINE) {
					tvTraffic.setLines(TEXTLINE);
				}
			}
		});
	}
	private void setHustylePic() {
		if (houseDetail.getItems() == null) {
			return;
		}
		for (int i = 0; i < houseDetail.getItems().length; i++) {
			if (houseDetail.getItems()[i] != null) {
				if (houseDetail.getItems()[i].getSourceType() != null && houseDetail.getItems()[i].getSourceType().equals(huStyleType)) {
					llHustylePic.addView(addOneHuStylePic(houseDetail.getItems()[i]));
				}
			}
		}
	}
	
	// FIXME may be crash
	private View addOneHuStylePic(final XcfcHouseDetailItems xcfcHouseDetailItems) {
		View view = null;
		view = LayoutInflater.from(context).inflate(
				R.layout.item_view_build_detail_hustyle, null);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, BuildHuStyleActivity_.class);
				intent.putExtra("itemId", xcfcHouseDetailItems.getId());
				context.startActivity(intent);
			}
		});
		
		final ImageView ivStyleHouses = (ImageView) view.findViewById(R.id.iv_style_houses);
		final TextView txtStyleHouses = (TextView) view.findViewById(R.id.txt_style_houses);
		final TextView txtStyleArea = (TextView) view.findViewById(R.id.txt_style_area);
		final TextView txtHustyleName = (TextView) view.findViewById(R.id.txt_hustyle_name);
		
		txtStyleHouses.setText(xcfcHouseDetailItems.getHouseType());
		txtHustyleName.setText(xcfcHouseDetailItems.getHouseTypeDesc());
		txtStyleArea.setText(xcfcHouseDetailItems.getHouseTypeArea());
		
		Bitmap bitmap = null;
		if (xcfcHouseDetailItems.getAttach() != null) {
		bitmap = downLoader.downloadImage(xcfcHouseDetailItems.getAttach().getUname(), new onImageLoaderListener() {
			
			@Override
			public void onImageLoader(Bitmap bitmap, String url) {
				if (ivStyleHouses != null && bitmap != null) {
					ivStyleHouses.setImageBitmap(bitmap);
				}
			}
		}, false);
		if (ivStyleHouses.getDrawable() == null && bitmap == null) {
			ivStyleHouses.setBackgroundResource(R.drawable.default_houseimg);
		}else if (ivStyleHouses.getDrawable() == null && bitmap != null) {
			ivStyleHouses.setImageBitmap(bitmap);
		}		
			//imageUtils.loadImage(uri, imageView);
	}
		return view;
	}
	
	private void bindDataPhone() {
		if (houseDetail == null) {
			return;
		}
		txtPhone.setText(houseDetail.getHotline());
	}

	
	@SuppressWarnings("deprecation")
	private void createShareDialog() {
		shareDialog = new Dialog(context, R.style.add_dialog);
		shareDialog.setContentView(R.layout.activity_build_detail_share_dialog);
		shareGridView = (GridView) shareDialog.findViewById(R.id.gv_share);
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
		shareGridView.setAdapter(adapterSimple);
		Window window = shareDialog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		shareDialog.getWindow().setAttributes(lp);
		shareDialog.show();
		shareGridView.setOnItemClickListener(new OnItemClickListener() {

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
		sp.setText(houseDetail.getName() + Constant.SHARE_URL);
		sp.setImageUrl(sharePicUrl);
		Platform weibo = ShareSDK.getPlatform(context, SinaWeibo.NAME);
		weibo.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {

			}
			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
			}
		}); // 设置分享事件回调
		// 执行图文分享
		weibo.share(sp);
	}

	private void shareWeixinFriend() {
		ShareParams sp = new ShareParams();
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setText(houseDetail.getName());
		sp.setUrl(Constant.SHARE_URL);
		sp.setImageUrl(sharePicUrl);
		Platform wechat = ShareSDK.getPlatform(context, Wechat.NAME);
		wechat.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				Utils.toastMessageForce(BuildDetailActivity.this, getString(R.string.share_error));
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				Utils.toastMessageForce(BuildDetailActivity.this, getString(R.string.share_success));
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
			}
		}); // 设置分享事件回调
		// 执行图文分享
		wechat.share(sp);
	}

	private void shareWeixinMoments() {
		ShareParams sp = new ShareParams();
		sp.setShareType(Platform.SHARE_WEBPAGE);
		sp.setTitle(houseDetail.getName());
		sp.setUrl(Constant.SHARE_URL);
		sp.setImageUrl(sharePicUrl);
		Platform weMoment = ShareSDK.getPlatform(context, WechatMoments.NAME);
		weMoment.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				Utils.toastMessageForce(BuildDetailActivity.this, getString(R.string.share_error));
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				Utils.toastMessageForce(BuildDetailActivity.this, getString(R.string.share_success));
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
		sp.setText(houseDetail.getName() + Constant.SHARE_URL);
		sp.setImagePath(filePath);
			
		Platform tencentWeibo = ShareSDK
				.getPlatform(context, TencentWeibo.NAME);
		tencentWeibo.setPlatformActionListener(new PlatformActionListener() {
			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				Utils.toastMessageForce(BuildDetailActivity.this, getString(R.string.share_error));
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				Utils.toastMessageForce(BuildDetailActivity.this, getString(R.string.share_success));
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		}); // 设置分享事件回调
		// 执行图文分享
		tencentWeibo.share(sp);
	}

	private void shareSMS() {
		ShareParams sp = new ShareParams();
		sp.setText(houseDetail.getName() + Constant.SHARE_URL);

		Platform sms = ShareSDK.getPlatform(context, ShortMessage.NAME);
		sms.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		}); // 设置分享事件回调
		// 执行图文分享
		sms.share(sp);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc(); 
	}

    
	/**
	 * 保存图片到本地腾讯微博和新浪微博分享图片是本地图片地址
	 * @param bitmap
	 */
	private void saveBitmap(Bitmap bitmap) {
		FileOutputStream b = null;
		File file = new File(IMAGE_FILE_PATH);		
		file.mkdirs();
		filePath = IMAGE_FILE_PATH + "grande" + ".jpg";
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
	
	
}
