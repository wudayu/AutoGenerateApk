package com.movitech.grande.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.movitech.grande.adapter.InfoListAdapter;
import com.movitech.grande.adapter.ViewPagerAdapter;
import com.movitech.grande.constant.Constant;
import com.movitech.grande.constant.NewsCatagoryId;
import com.movitech.grande.constant.Rolling;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.image.ImageDownLoader.onImageLoaderListener;
import com.movitech.grande.model.XcfcInfo;
import com.movitech.grande.model.XcfcInfoBanner;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcInfoBannerResult;
import com.movitech.grande.net.protocol.XcfcInfoesResult;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.views.CirclePageIndicator;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.InfoDetailActivity_;
/**
 *
 * @author: Wu Dayu 
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 2, 2014 8:11:27 PM
 * @Description: This is David Wu's property.
 *
 **/
@EFragment(R.layout.fragment_info_acitivity)
public class InfoNewcityFragment extends BaseFragment {

	@ViewById(R.id.lv_info)
	LoadDataListView lvNewcityInfo;
	
	@ViewById(R.id.info_line)
	View infoLine;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	
	ImageDownLoader loader = null;
	
	InfoListAdapter newCityAdapter = null;
	XcfcInfoesResult resultNewCity = null;
	XcfcInfo[] newcityActivities = null;
	
	XcfcInfoBannerResult resultNewCityBanner = null;
	XcfcInfoBanner[] newcityActivitiesBanner = null;
	
	int newcityCurrPage = 1;
	int newcityTotalPage = 0;
	boolean isLoading = false;
	boolean dataLoaded = false;
    ProcessingDialog processingDialog = null;
    View newCityInfoLoadMore = null;
    
    View viewBannerHeader = null;
    BaseViewPager vpBannerFragmentInfo = null;
	CirclePageIndicator circleIndicator = null;
	
	private Timer timer = null;
	private TimerTask timerTask = null;
	@AfterViews
	void afterViews() {
		initViews();
		doLoadData();
		
	}
	private void startBannerRollTask() {
		timerTask = new TimerTask() {
			@Override
			public void run() {
				if (!touching)
					switchPage();
			}
		};
		timer = new Timer();
		timer.schedule(timerTask, 0, Rolling.ROLLING_BREAK);
	}

	private void cancleBannerRollTask() {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	public void checkDataLoaded() {
		if (!dataLoaded && !isLoading) {	
			doLoadDataAndBindData();
		}
	}

	public void reDoLoad() {
		if (!isLoading) {
			cancleBannerRollTask();
			showProgressDialog();
			lvNewcityInfo.resetVar();
			lvNewcityInfo.resetFootView();
			lvNewcityInfo.addOnScrollListener();
			doLoadData();
		}
	}
	@UiThread
	void switchPage() {
		vpBannerFragmentInfo.setCurrentItem((vpBannerFragmentInfo.getCurrentItem() + 1) % Constant.BANNER_COUNT);
	}

	private void initViews() {	
		viewBannerHeader = getActivity().getLayoutInflater().inflate(
				R.layout.view_info_list_banner, null);
		vpBannerFragmentInfo = (BaseViewPager) viewBannerHeader.findViewById(R.id.vp_banner_fragment_info);
		circleIndicator = (CirclePageIndicator) viewBannerHeader.findViewById(R.id.circle_indicator);
		lvNewcityInfo.addHeaderView(viewBannerHeader);
		// 初始化ListView
		newCityInfoLoadMore = getActivity().getLayoutInflater().inflate(
				R.layout.item_loading, null);
		newCityInfoLoadMore.setOnClickListener(null);
		lvNewcityInfo.addFooterView(newCityInfoLoadMore);
		
		lvNewcityInfo.setOnScrollToEdgeCallBack(new OnScrollToEdgeCallBack() {
			public void toBottom() {
				if (!isLoading) {
					showProgressDialog();
					loadNewForNewcity();}
			}
		});
		
		
	}

	@Background
	void doLoadData(){
		isLoading = true;
		doLoadBanner();
		doLoadDataAndBindData();
	}
	
	void doLoadBanner(){
		resultNewCityBanner = netHandler.postForGetInfoBanner(NewsCatagoryId.CATAGORY_ID_NEWCITY);

		if (null == resultNewCityBanner) {
			goBackMainThreadBanner(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (resultNewCityBanner.getResultSuccess() == false) {
			goBackMainThreadBanner(resultNewCityBanner.getResultMsg(), false);
			return;
		}

		newcityActivitiesBanner = resultNewCityBanner.getInfoBanner();
		//newcityTotalPage = resultNewCity.getPageResult().getPageNo();
        
		goBackMainThreadBanner(resultNewCityBanner.getResultMsg(), true);
	}
	
	private void goBackMainThreadBanner(String resultMsg, boolean b) {
		initSlideViewPage();
	}

	void doLoadDataAndBindData() {
		resultNewCity = netHandler.postForGetInfoesResult(1, NewsCatagoryId.CATAGORY_ID_NEWCITY + "," + NewsCatagoryId.CATAGORY_ID_POLICY, "", "");

		if (null == resultNewCity) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (resultNewCity.getResultSuccess() == false) {
			goBackMainThread(resultNewCity.getResultMsg(), false);
			return;
		}

		newcityActivities = resultNewCity.getPageResult().getInfoes();
		newcityTotalPage = resultNewCity.getPageResult().getPageNo();
        
		goBackMainThread(resultNewCity.getResultMsg(), true);
	}

	private void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(this.getActivity(), msg);

		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		dismissProcessingDialog();
		isLoading = false;
		newCityAdapter = new InfoListAdapter(getActivity().getApplicationContext(), newcityActivities,
				R.layout.item_listview_fragment_info, imageUtils);
		lvNewcityInfo.setAdapter(newCityAdapter);
		lvNewcityInfo.setTotalPageCount(newcityTotalPage);
		lvNewcityInfo.setCurrentPage(1);

		lvNewcityInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XcfcInfo info = ((InfoListAdapter.ViewCache) view.getTag()).info;
				InfoDetailActivity_.intent(getActivity()).infoId(info.getId()).start();
			}
		});
		dataLoaded = true;
		dismissProcessingDialog();	
	}

	boolean touching = false;
	@UiThread
	void initSlideViewPage() {
		if (newcityActivitiesBanner == null ) {
			return;
		}
		List<View> views = new ArrayList<View>();
		loader = new ImageDownLoader(getActivity().getApplicationContext());
		int bannerCount = (Constant.BANNER_COUNT > newcityActivitiesBanner.length ? newcityActivitiesBanner.length : Constant.BANNER_COUNT);
		for (int i = 0; i < bannerCount; ++i) {
			final XcfcInfoBanner infoBanner = (XcfcInfoBanner)newcityActivitiesBanner[i];
			final View vg = LayoutInflater.from(context).inflate(R.layout.layout_publicity_fragment_info, null);
			final ImageView imageViewBanner = (ImageView) vg.findViewById(R.id.iv_publicity);
			Bitmap bitmap = null;
			bitmap = loader.downloadImage(infoBanner.getBannerSrc(), new onImageLoaderListener() {
				@Override
				public void onImageLoader(Bitmap bitmap, String url) {
					if (imageViewBanner != null && bitmap != null) {
						imageViewBanner.setImageBitmap(bitmap);
					}
				}
			}, false);
			if (imageViewBanner.getDrawable() == null && bitmap == null) {
				imageViewBanner
						.setBackgroundResource(R.drawable.default_info_banner);
			}else if (imageViewBanner.getDrawable() == null && bitmap != null) {
				imageViewBanner.setImageBitmap(bitmap);
			}
			((TextView) vg.findViewById(R.id.txt_publicity)).setText(infoBanner.getTitle());

			vg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					InfoDetailActivity_.intent(getActivity()).infoId(infoBanner.getId()).start();
				}
			});

			views.add(vg);
		}

		ViewPagerAdapter adapter = new ViewPagerAdapter();
		adapter.init();
		adapter.addAll(views);
		vpBannerFragmentInfo.setAdapter(adapter);
		circleIndicator.setViewPager(vpBannerFragmentInfo);
		startBannerRollTask();//开始banner的滚动
		vpBannerFragmentInfo.setOnTouchListener(new OnTouchListener() {
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
	
	@Background
	void loadNewForNewcity() {
		if (isLoading)
			return;
		isLoading = true;
		newcityCurrPage = lvNewcityInfo.getCurrentPage() + 1;
		resultNewCity = netHandler.postForGetInfoesResult(newcityCurrPage, NewsCatagoryId.CATAGORY_ID_NEWCITY + "," + NewsCatagoryId.CATAGORY_ID_POLICY, "", "");
		boolean illegal = (resultNewCity == null || !resultNewCity.getResultSuccess() || resultNewCity
				.getPageResult().getInfoes().length < 1);
		if (!illegal) {
			addNewcityItems();
		}
	}

	@UiThread
	void addNewcityItems() {
		dismissProcessingDialog();
		newCityAdapter.addItems(resultNewCity.getPageResult().getInfoes());
		lvNewcityInfo.setCurrentPage(newcityCurrPage);
		isLoading = false;
	}
	
	
	public void showProgressDialog(){
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
			}
		});
		processingDialog.show();
	}
	
	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}
	
	
}
