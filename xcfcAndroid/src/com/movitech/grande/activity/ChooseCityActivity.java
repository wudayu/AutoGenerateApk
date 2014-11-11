package com.movitech.grande.activity;

import java.util.LinkedList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.ChooseCityAdapter;
import com.movitech.grande.adapter.ChooseCitysAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.model.CompanyModel;
import com.movitech.grande.model.XcfcCity;
import com.movitech.grande.haerbin.R;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 18, 2014 8:48:10 PM
 * @Description: This is David Wu's property.
 *
 **/
@EActivity(R.layout.activity_choose_city)
//@EActivity(R.layout.activity_selected_city)
public class ChooseCityActivity extends BaseActivity {

	/*private static final String [] NETERROR_CITY = {"上海","苏州","南京","无锡","杭州","昆山","南通","常州","武汉"};
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.iv_banner)
	ImageView ivBanner;
	@ViewById(R.id.iv_city_kunshan)
	ImageView ivCityKunshan;
	@ViewById(R.id.iv_city_wuxi)
	ImageView ivCityWuxi;
	@ViewById(R.id.iv_city_shanghai)
	ImageView ivCityShanghai;
	@ViewById(R.id.iv_city_hangzhou)
	ImageView ivCityHangzhou;
	@ViewById(R.id.iv_city_nanjing)
	ImageView ivCityNanjing;
	@ViewById(R.id.iv_city_nantong)
	ImageView ivCityNantong;
	@ViewById(R.id.iv_city_changzhou)
	ImageView ivCityChangzhou;
	@ViewById(R.id.iv_city_suzhou)
	ImageView ivCitySuzhou;
	@ViewById(R.id.iv_city_wuhan)
	ImageView ivCityWuhan;
	
	@ViewById(R.id.ll_city_shanghai)
	LinearLayout llCityShanghai;
	@ViewById(R.id.ll_city_changzhou)
	LinearLayout llCityChangzhou;
	@ViewById(R.id.ll_city_nanjing)
	LinearLayout llCityNanjing;
	@ViewById(R.id.ll_city_suzhou)
	LinearLayout llCitySuzhou;
	@ViewById(R.id.ll_city_kunshan)
	LinearLayout llCityKunshan;
	@ViewById(R.id.ll_city_wuxi)
	LinearLayout llCityWuxi;
	@ViewById(R.id.ll_city_hangzhou)
	LinearLayout llCityHangzhou;
	@ViewById(R.id.ll_city_nantong)
	LinearLayout llCityNantong;
	@ViewById(R.id.ll_city_wuhan)
	LinearLayout llCityWuhan;
	
	
	
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;

	XcfcCity selectedCity = null;
	XcfcCity[] cities = null;
	boolean useResult;
	
	boolean noNetChooseCity;

	int count = 0;

	ProcessingDialog processingDialog = null;

	@AfterViews
	void afterViews() {
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				BackgroundExecutor.cancelAll("queryData", false);
			}
		});

		useResult = getIntent().getBooleanExtra(ExtraNames.XCFC_CITY_USE_RESULT, false);
		noNetChooseCity = getIntent().getBooleanExtra(ExtraNames.XCFC_CITY_NET_ERROR, false);
		cities = mApp.getCities();
		selectedCity = mApp.getCurrCity();
		if (getIntent() != null && null != getIntent().getStringExtra("currentCity")) {
			if (useResult) {
				initCityView();
			} else if (noNetChooseCity) {
				initNetErrorCity();
			}
		}else
		    initViews();
	}

	@Background(id="queryData")
	void doLoadData() {
		XcfcStringResult result = netHandler.postForGetRuleBanner(RuleBannerTypeId.CITY_BANNER);

		if (result == null || !result.getResultSuccess() || result.getObjValue() == null || "".equals(result.getObjValue())) {
			goBackMainThread(null, false);
			return;
		}

		goBackMainThread(result.getObjValue(), true);
	}

	@UiThread
	void goBackMainThread(String url, boolean success) {
		if (success)
		dismissProcessingDialog();
	}

	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

	private void initViews() {
		if (null == selectedCity)
			return;
		if (useResult)
			return;
		if (getIntent().getBooleanExtra(ExtraNames.XCFC_CITY_FIRST, false))
			mApp.setCurrUser(null);

		if (selectedCity.getName().hashCode() == cities[0].getName().hashCode()) {
			setCityOn(ivCityShanghai, R.drawable.select_city_on);
			
		}else if (selectedCity.getName().hashCode() == cities[1].getName().hashCode()) {
			setCityOn(ivCitySuzhou, R.drawable.select_city_on);
		}else if (selectedCity.getName().hashCode() == cities[2].getName().hashCode()) {
			setCityOn(ivCityNanjing, R.drawable.select_city_on);
		}else if (selectedCity.getName().hashCode() == cities[3].getName().hashCode()) {
			setCityOn(ivCityWuxi, R.drawable.select_city_on);
		}else if (selectedCity.getName().hashCode() == cities[4].getName().hashCode()) {
			setCityOn(ivCityHangzhou, R.drawable.select_city_on);
		}else if (selectedCity.getName().hashCode() == cities[5].getName().hashCode()) {
			setCityOn(ivCityKunshan, R.drawable.select_city_on);
		}else if (selectedCity.getName().hashCode() == cities[6].getName().hashCode()) {
			setCityOn(ivCityNantong, R.drawable.select_city_on);
		}else if (selectedCity.getName().hashCode() == cities[7].getName().hashCode()) {
			setCityOn(ivCityChangzhou, R.drawable.select_city_on);
		}else if (selectedCity.getName().hashCode() == cities[8].getName().hashCode()) {
			setCityOn(ivCityWuhan, R.drawable.select_city_on);
		}
	}
	
	private void initCityView(){		
		String currentCity = getIntent().getStringExtra("currentCity"); 
		if (currentCity.hashCode() == cities[0].getName().hashCode()) {
			setCityOn(ivCityShanghai, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == cities[1].getName().hashCode()) {
			setCityOn(ivCitySuzhou, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == cities[2].getName().hashCode()) {
			setCityOn(ivCityNanjing, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == cities[3].getName().hashCode()) {
			setCityOn(ivCityWuxi, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == cities[4].getName().hashCode()) {
			setCityOn(ivCityHangzhou, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == cities[5].getName().hashCode()) {
			setCityOn(ivCityKunshan, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == cities[6].getName().hashCode()) {
			setCityOn(ivCityNantong, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == cities[7].getName().hashCode()) {
			setCityOn(ivCityChangzhou, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == cities[8].getName().hashCode()) {
			setCityOn(ivCityWuhan, R.drawable.select_city_on);
		}
	}

	private void initNetErrorCity() {
		
		String currentCity = getIntent().getStringExtra("currentCity"); 
		if (currentCity.hashCode() == NETERROR_CITY[0].hashCode()) {
			setCityOn(ivCityShanghai, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == NETERROR_CITY[1].hashCode()) {
			setCityOn(ivCitySuzhou, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == NETERROR_CITY[2].hashCode()) {
			setCityOn(ivCityNanjing, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == NETERROR_CITY[3].hashCode()) {
			setCityOn(ivCityWuxi, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == NETERROR_CITY[4].hashCode()) {
			setCityOn(ivCityHangzhou, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == NETERROR_CITY[5].hashCode()) {
			setCityOn(ivCityKunshan, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == NETERROR_CITY[6].hashCode()) {
			setCityOn(ivCityNantong, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == NETERROR_CITY[7].hashCode()) {
			setCityOn(ivCityChangzhou, R.drawable.select_city_on);
		}else if (currentCity.hashCode() == NETERROR_CITY[8].hashCode()) {
			setCityOn(ivCityWuhan, R.drawable.select_city_on);
		}
	
	}
	
	@SuppressWarnings("unused")
	private void setCityButtonOn(View view, int resource) {
		LayoutParams lp = view.getLayoutParams();
		lp.width = getResources().getDimensionPixelSize(R.dimen.side_iv_city_on);
		lp.height = getResources().getDimensionPixelSize(R.dimen.side_iv_city_on);

		view.setLayoutParams(lp);
		view.setBackgroundResource(resource);
	}
	
	private void setCityOn(View view, int resource){
		view.setBackgroundResource(resource);
	}
	
	
	@Click
	public void llCityShanghai(View v) {
		resetCityImage();
		setCityOn(ivCityShanghai, R.drawable.select_city_on);
		Utils.toastMessageForce(ChooseCityActivity.this, getString(R.string.toast_choose_city_nodata));
		return;
		//后期会改回来的不能删除，该版本注释
		if (useResult) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.XCFC_CITY, cities[0]);
		    setResult(ReqCode.SELECT_CITY, intent);
		    finish();
		    return;
		}else if (noNetChooseCity) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[0]);
		    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
		    finish();
		    return;
		}

		mApp.setCurrCity(cities[0]);
	    setResult(ReqCode.SELECT_CITY);
	    finish();
	}
	
	@Click
	public void llCitySuzhou(View v) {
		resetCityImage();
		setCityOn(ivCitySuzhou, R.drawable.select_city_on);
		Utils.toastMessageForce(ChooseCityActivity.this, getString(R.string.toast_choose_city_nodata));
		return;
		//后期会改回来的不能删除，该版本注释
		if (useResult) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.XCFC_CITY, cities[1]);
		    setResult(ReqCode.SELECT_CITY, intent);
		    finish();
		    return;
		}else if (noNetChooseCity) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[1]);
		    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
		    finish();
		    return;
		}

		mApp.setCurrCity(cities[1]);
	    setResult(ReqCode.SELECT_CITY);
	    finish();
	}
	
	@Click
	public void llCityNanjing(View v) {
		resetCityImage();
		setCityOn(ivCityNanjing, R.drawable.select_city_on);
		Utils.toastMessageForce(ChooseCityActivity.this, getString(R.string.toast_choose_city_nodata));
		return;
		//后期会改回来的不能删除，该版本注释
		if (useResult) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.XCFC_CITY, cities[2]);
		    setResult(ReqCode.SELECT_CITY, intent);
		    finish();
		    return;
		}else if (noNetChooseCity) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[2]);
		    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
		    finish();
		    return;
		}

		mApp.setCurrCity(cities[2]);
	    setResult(ReqCode.SELECT_CITY);
	    finish();
	}
	
	@Click
	public void llCityWuxi(View v) {
		resetCityImage();
		setCityOn(ivCityWuxi, R.drawable.select_city_on);
		Utils.toastMessageForce(ChooseCityActivity.this, getString(R.string.toast_choose_city_nodata));
		return;
		//后期会改回来的不能删除，该版本注释
		if (useResult) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.XCFC_CITY, cities[3]);
		    setResult(ReqCode.SELECT_CITY, intent);
		    finish();
		    return;
		}else if (noNetChooseCity) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[3]);
		    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
		    finish();
		    return;
		}
		mApp.setCurrCity(cities[3]);
	    setResult(ReqCode.SELECT_CITY);
	    finish();
	}
	
	@Click
	public void llCityHangzhou(View v) {
		resetCityImage();
		setCityOn(ivCityHangzhou, R.drawable.select_city_on);
		Utils.toastMessageForce(ChooseCityActivity.this, getString(R.string.toast_choose_city_nodata));
		return;
		//后期会改回来的不能删除，该版本注释
		if (useResult) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.XCFC_CITY, cities[4]);
		    setResult(ReqCode.SELECT_CITY, intent);
		    finish();
		    return;
		}else if (noNetChooseCity) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[4]);
		    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
		    finish();
		    return;
		}

		mApp.setCurrCity(cities[4]);
	    setResult(ReqCode.SELECT_CITY);
	    finish();
	}
	
	@Click
	public void llCityKunshan(View v) {
		resetCityImage();
		setCityOn(ivCityKunshan, R.drawable.select_city_on);
		Utils.toastMessageForce(ChooseCityActivity.this, getString(R.string.toast_choose_city_nodata));
		return;
		//后期会改回来的不能删除，该版本注释
		if (useResult) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.XCFC_CITY, cities[5]);
		    setResult(ReqCode.SELECT_CITY, intent);
		    finish();
		    return;
		}else if (noNetChooseCity) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[5]);
		    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
		    finish();
		    return;
		}

		mApp.setCurrCity(cities[5]);
	    setResult(ReqCode.SELECT_CITY);
	    finish();
	}
	
	@Click
	public void llCityNantong(View v) {
		resetCityImage();
		setCityOn(ivCityNantong, R.drawable.select_city_on);
		Utils.toastMessageForce(ChooseCityActivity.this, getString(R.string.toast_choose_city_nodata));
		return;
		//后期会改回来的不能删除，该版本注释
		if (useResult) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.XCFC_CITY, cities[6]);
		    setResult(ReqCode.SELECT_CITY, intent);
		    finish();
		    return;
		}else if (noNetChooseCity) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[6]);
		    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
		    finish();
		    return;
		}

		mApp.setCurrCity(cities[6]);
	    setResult(ReqCode.SELECT_CITY);
	    finish();
	}
	
	@Click
	public void llCityChangzhou(View v) {
		resetCityImage();
		setCityOn(ivCityChangzhou, R.drawable.select_city_on);
		
		if (useResult) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.XCFC_CITY, cities[7]);
		    setResult(ReqCode.SELECT_CITY, intent);
		    finish();
		    return;
		}else if (noNetChooseCity) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[7]);
		    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
		    finish();
		    return;
		}

		mApp.setCurrCity(cities[7]);
	    setResult(ReqCode.SELECT_CITY);
	    finish();
	}
	@Click
	public void llCityWuhan(View v) {
		resetCityImage();
		setCityOn(ivCityWuhan, R.drawable.select_city_on);
		Utils.toastMessageForce(ChooseCityActivity.this, getString(R.string.toast_choose_city_nodata));
		return;
		//后期会改回来的不能删除，该版本注释
		if (useResult) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.XCFC_CITY, cities[8]);
		    setResult(ReqCode.SELECT_CITY, intent);
		    finish();
		    return;
		}else if (noNetChooseCity) {
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[8]);
		    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
		    finish();
		    return;
		}

		mApp.setCurrCity(cities[8]);
	    setResult(ReqCode.SELECT_CITY);
	    finish();
	}
	
	
	@Click
	void ivBack() {
		this.finish();
	}
	
	//重置城市图标
	private void resetCityImage(){
		ivCityKunshan.setBackgroundResource(R.drawable.select_ico_ks);
		ivCityWuxi.setBackgroundResource(R.drawable.select_ico_wx);
		ivCityShanghai.setBackgroundResource(R.drawable.select_ico_sh);
		ivCityHangzhou.setBackgroundResource(R.drawable.select_ico_hz);
		ivCityNanjing.setBackgroundResource(R.drawable.select_ico_nj);
		ivCityNantong.setBackgroundResource(R.drawable.select_ico_nt);
		ivCityChangzhou.setBackgroundResource(R.drawable.select_ico_cz);
		ivCitySuzhou.setBackgroundResource(R.drawable.select_ico_sz);
		ivCityWuhan.setBackgroundResource(R.drawable.select_ico_wh);
	}

	*/
	@ViewById(R.id.gv_city)
	GridView gvCity;
	
	ChooseCityAdapter chooseCityAdapter;
	ChooseCitysAdapter citysAdapter;
	String [] citysArray = null;
	
	@App
	MainApp mApp;
    boolean useResult;
	boolean noNetChooseCity;
	XcfcCity selectedCity = null;
	XcfcCity[] cities = null;
	List<CompanyModel> models = null;
	
	
	
	@AfterViews
	void afterViews() {
		citysArray = context.getResources().getStringArray(R.array.citys_company);
		createXcfcCity(citysArray);
		
		useResult = getIntent().getBooleanExtra(ExtraNames.XCFC_CITY_USE_RESULT, false);
		noNetChooseCity = getIntent().getBooleanExtra(ExtraNames.XCFC_CITY_NET_ERROR, false);
		//cities = mApp.getCities();
		selectedCity = mApp.getCurrCity();
		//chooseCityAdapter = new ChooseCityAdapter(context, citysArray);
	    models = parse();
	    
		citysAdapter = new ChooseCitysAdapter(context, models);
		gvCity.setAdapter(citysAdapter);
		
		gvCity.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				
				if (useResult) {
					Intent intent = new Intent();
					XcfcCity currCity = new XcfcCity();
					String city = models.get(position).getCmpName();
					currCity.setName(city.substring(0, city.indexOf("公司")));
					intent.putExtra(ExtraNames.XCFC_CITY, currCity);
				    setResult(ReqCode.SELECT_CITY, intent);
				    finish();
				    return;
				}else if (noNetChooseCity) {
					Intent intent = new Intent();
					//intent.putExtra(ExtraNames.NET_ERROR_SELECT_CITY, NETERROR_CITY[7]);
				    setResult(ReqCode.NET_ERROR_SELECT_CITY, intent);
				    finish();
				    return;
				}
				XcfcCity currCity = new XcfcCity();
				String city = models.get(position).getCmpName();
				currCity.setName(city.substring(0, city.indexOf("公司")));
				mApp.setCurrCity(currCity);
			    setResult(ReqCode.SELECT_CITY);
			    finish();
			}
		});
	}
	
	private List<CompanyModel> parse() {
		String[] cmpsArray = context.getResources().getStringArray(R.array.allcitys);
		List<CompanyModel> cmpList=new LinkedList<CompanyModel>();
		for(int i=0,ln=cmpsArray.length;i<ln;i++){
			String temp=cmpsArray[i];
			String[] splitRes=temp.split("@");
			CompanyModel m=new CompanyModel();
			for(int j=0,s=splitRes.length;j<s;j++){
				String str=splitRes[j];
				if(str.contains("img")){
					m.setIconResid(context.getResources().getIdentifier(str,"drawable",getPackageName()));
				}else if(str.contains("citys")){
					int arrayId=context.getResources().getIdentifier(str,"array",getPackageName());
					String[] subCmp = context.getResources().getStringArray(arrayId);
					m.setChildrenCity(subCmp);
				}else{
					m.setCmpName(str);
				}
			}
			cmpList.add(m);
		}
		return cmpList;
		
	}
	
	
	private void createXcfcCity (String [] citysArray) {
		if (citysArray == null) {
			return;
		}
		cities = new XcfcCity [citysArray.length];
		for (int i = 0; i < citysArray.length; i++) {
			XcfcCity xcfcCity = new XcfcCity();
			xcfcCity.setName(citysArray[i]);
			cities[i] = xcfcCity;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (getIntent().getBooleanExtra(ExtraNames.XCFC_CITY_FIRST, false))
				this.closeAllActivity();
		}

		return super.onKeyDown(keyCode, event);
	}
	
	
}
