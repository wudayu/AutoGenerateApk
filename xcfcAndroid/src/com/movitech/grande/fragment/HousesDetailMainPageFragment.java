package com.movitech.grande.fragment;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.HouseDetailImageSourceType;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.image.ImageDownLoader.onImageLoaderListener;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.utils.Formatter;
import com.movitech.grande.views.AutoScaleTextView;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.MainActivity_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 17, 2014 2:17:47 PM
 * @Description: This is David Wu's property.
 * 
 **/
@Deprecated
@EFragment(R.layout.fragment_houses_detail_main_page)
public class HousesDetailMainPageFragment extends BaseFragment {

	@ViewById(R.id.txt_people_appointed)
	TextView txtPeopleAppointed;
	@ViewById(R.id.ll_rmb_commmission)
	LinearLayout llRmbCommmission;
	@ViewById(R.id.rl_privilege_layout_recommend_houses)
	RelativeLayout rlPrivilegeLayoutRecommendHouses;
	@ViewById(R.id.txt_privilege_layout_recommend_houses)
	TextView txtPrivilegeLayoutRecommendHouses;
	@ViewById(R.id.txt_time_to_over)
	TextView txtTimeToOver;
	@ViewById(R.id.txt_times_recommended)
	TextView txtTimesRecommended;
	@ViewById(R.id.txt_name_layout_recommend_houses)
	TextView txtNameLayoutRecommendHouses;
	@ViewById(R.id.iv_privilege_recommend_houses)
	ImageView ivPrivilegeRecommendHouses;
	@ViewById(R.id.btn_house_appointment)
	Button btnHouseAppointment;
	@ViewById(R.id.txt_area_layout_recommend_houses)
	AutoScaleTextView txtAreaLayoutRecommendHouses;
	@ViewById(R.id.btn_recommend_immediately)
	Button btnRecommendImmediately;
	@ViewById(R.id.rl_houses_detail_block)
	RelativeLayout rlHousesDetailBlock;
	@ViewById(R.id.txt_commission)
	TextView txtCommission;
	@ViewById(R.id.rl_houses_name)
	RelativeLayout rlHousesName;
	@ViewById(R.id.rl_more_info)
	RelativeLayout rlMoreInfo;
	@ViewById(R.id.txt_section_layout_recommend_houses)
	TextView txtSectionLayoutRecommendHouses;
	@ViewById(R.id.iv_background)
	ImageView ivBackground;

	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@App
	MainApp mApp;
	
	ImageDownLoader imageDownLoader;
	Bitmap bitmap = null;
	private XcfcHouseDetail houseDetail;

	@AfterViews
	void afterViews() {
	}

	@Click
	void btnRecommendImmediately() {
		//((HousesDetailActivity) getActivity()).cancelQueryData();
		getActivity().finish();
		Intent intent = new Intent(this.getActivity(), MainActivity_.class);
		intent.putExtra(ExtraNames.JUMP_FROM_RECOMM, houseDetail);
		startActivity(intent);
	}

	@Click
	void btnHouseAppointment() {
		//((HousesDetailActivity) getActivity()).cancelQueryData();
		getActivity().finish();
		Intent intent = new Intent(this.getActivity(), MainActivity_.class);
		intent.putExtra(ExtraNames.JUMP_FROM_RECOMM, houseDetail);
		intent.putExtra(ExtraNames.JUMP_TO_APPOINT, true);
		startActivity(intent);
	}

	public void setHouseDetail(XcfcHouseDetail houseDetail) {
		this.houseDetail = houseDetail;

		doBindData();
	}

	boolean dataBinded = false;
	public void bindDataNow() {
		if (dataBinded)
			return;

		doBindData();

		dataBinded = true;
	}

	@UiThread
	void doBindData() {
		if (getActivity() == null || this.isDetached())
			return;
		if (houseDetail == null) {
			return;
		}
		//8.4号修改
		imageDownLoader = new ImageDownLoader(getActivity().getApplicationContext());
		List<String> picsrc = houseDetail.getImageAddressesBySourceType(HouseDetailImageSourceType.DETAIL_MAIN);
		if (picsrc.size() > 0) {
			// imageUtils.loadImage(picsrc.get(0),ivBackground);
	        
			bitmap = imageDownLoader.downloadImage(picsrc.get(0),
					new onImageLoaderListener() {

						@Override
						public void onImageLoader(Bitmap bitmap, String url) {
							if (ivBackground != null && bitmap != null) {
								ivBackground.setImageBitmap(bitmap);
							}
						}
					}, true);
			if (bitmap != null&& ivBackground.getDrawingCache() == null) {
				ivBackground.setImageBitmap(bitmap);
			}
		}
		txtCommission.setText(houseDetail.getRatio());
		txtNameLayoutRecommendHouses.setText(houseDetail.getName());
		txtSectionLayoutRecommendHouses.setText(Formatter
				.formatSection(houseDetail.getArea()));
		txtPrivilegeLayoutRecommendHouses.setText(houseDetail.getDiscount());
		txtAreaLayoutRecommendHouses.setText(houseDetail.getPrice()
				+ getString(R.string.str_square));
		txtTimeToOver.setText(houseDetail.getDiscountEndTime()
				+ getString(R.string.str_time_to_over_tail));
		txtTimesRecommended.setText(houseDetail.getRecommendedNum()
				+ getString(R.string.str_time_recommended_tail));
		txtPeopleAppointed.setText(houseDetail.getBespeakNum()
				+ getString(R.string.str_people_appointed_tail));

		rlHousesDetailBlock.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDestroy() {
		if (bitmap != null && bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		System.gc();
		super.onDestroy();
	}
}
