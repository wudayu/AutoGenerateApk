package com.movitech.grande.fragment;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.MineTeamAdapter;
import com.movitech.grande.adapter.RecomFragmentPageAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.model.XcfcTeam;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcTeamResult;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.NoSlideViewPager;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.AddSubInstitutionActivity_;
import com.movitech.grande.activity.SubInstitutionInfoActivity_;
import com.movitech.grande.fragment.RecomAppointmentFragment_;
import com.movitech.grande.fragment.RecomRecommendFragment_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 10, 2014 10:54:34 AM
 * @Description: This is David Wu's property.
 * 
 **/
@EFragment(R.layout.fragment_recom)
public class RecomFragment extends BaseFragment {
	@ViewById(R.id.rl_recom_tab)
	RelativeLayout rlRecomTab;
	@ViewById(R.id.rl_team)
	RelativeLayout rlTeam;
	@ViewById(R.id.iv_add_client)
	ImageView ivAddClient;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	
	@ViewById(R.id.txt_top_word_indicator_recommend)
	TextView txtTopWordIndicatorRecommend;
	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.txt_top_word_indicator_appointment)
	TextView txtTopWordIndicatorAppointment;
	@ViewById(R.id.puppet)
	View puppet;
	@ViewById(R.id.rl_top_indicator_external)
	RelativeLayout rlTopIndicatorExternal;
	@ViewById(R.id.rl_top_word_indicator_appointment)
	RelativeLayout rlTopWordIndicatorAppointment;
	@ViewById(R.id.top_indicator_left)
	View topIndicatorLeft;
	@ViewById(R.id.vp_fragment_recom)
	NoSlideViewPager vpFragmentRecom;
	@ViewById(R.id.top_indicator_right)
	View topIndicatorRight;
	@ViewById(R.id.rl_top_word_indicator_recommend)
	RelativeLayout rlTopWordIndicatorRecommend;

	@ViewById(R.id.lv_team)
	LoadDataListView lvTeam;
	
	private RecomRecommendFragment recommendFragment = null;
	private RecomAppointmentFragment appointmentFragment = null;
    
	MineTeamAdapter mineTeamAdapter = null;
	XcfcTeam [] xcfcTeams = null;
	
	
	private boolean isLoadTeamData = false;
	
	@App
	MainApp mApp;
	@Bean(NetHandler.class)
	INetHandler netHandler;
	
 	@AfterViews
	void afterViews() {
		initPages();
		initTeamPages();
	}


	private void initTeamPages() {
		ivBack.setVisibility(View.GONE);
		lvTeam.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				XcfcTeam team = ((MineTeamAdapter.ViewHolder) view.getTag()).xcfcTeam;
				Intent intent = new Intent(getActivity(), SubInstitutionInfoActivity_.class);
				intent.putExtra(ExtraNames.TEAM_SUB_ID, team.getId());
				startActivity(intent);
			}
		});
	}


	public void teamViews() {
		rlRecomTab.setVisibility(View.GONE);
		rlTeam.setVisibility(View.VISIBLE);
	}
	
	public void recomViews(){
		rlTeam.setVisibility(View.GONE);
		rlRecomTab.setVisibility(View.VISIBLE);
	}
	
	private void initPages() {
		List<Fragment> pages = new ArrayList<Fragment>();
		recommendFragment = new RecomRecommendFragment_();
		appointmentFragment = new RecomAppointmentFragment_();
		pages.add(recommendFragment);
		pages.add(appointmentFragment);

		RecomFragmentPageAdapter adapter = new RecomFragmentPageAdapter(getChildFragmentManager());
		adapter.addAll(pages);
		vpFragmentRecom.setAdapter(adapter);
	}

	@Click
	public void rlTopWordIndicatorRecommend() {
		vpFragmentRecom.setCurrentItem(0);

		topIndicatorLeft.setVisibility(View.VISIBLE);
		topIndicatorRight.setVisibility(View.INVISIBLE);
		txtTopWordIndicatorRecommend.setTextColor(getResources().getColor(
				R.color.letter_main_red));
		txtTopWordIndicatorAppointment.setTextColor(getResources().getColor(
				R.color.col_words_inactive_grey));
	}

	@Click
	public void rlTopWordIndicatorAppointment() {
		vpFragmentRecom.setCurrentItem(1);

		topIndicatorLeft.setVisibility(View.INVISIBLE);
		topIndicatorRight.setVisibility(View.VISIBLE);
		txtTopWordIndicatorRecommend.setTextColor(getResources().getColor(
				R.color.col_words_inactive_grey));
		txtTopWordIndicatorAppointment.setTextColor(getResources().getColor(
				R.color.letter_main_red));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		recommendFragment.onActivityResult(requestCode, resultCode, data);
		appointmentFragment.onActivityResult(requestCode, resultCode, data);

		super.onActivityResult(requestCode, resultCode, data);
	}

	public RecomRecommendFragment getRecommendFragment() {
		return recommendFragment;
	}

	public RecomAppointmentFragment getAppointmentFragment() {
		return appointmentFragment;
	}
	
	public void loadDateTeamList(){
		if (!isLoadTeamData) {
			doLoadDataTeam();
		}
	}
	
	@Background
	void doLoadDataTeam(){
		isLoadTeamData = true;
		XcfcTeamResult teamResult = netHandler.postForGetTeamList(mApp.getCurrUser().getId());
		
		if (null == teamResult) {
			goBackMainThread("",false);
			return;
		}
		if (teamResult.getResultSuccess() == false) {
			goBackMainThread("",false);
			return;
		}
		xcfcTeams = teamResult.getTeams();
		goBackMainThread("", true);
		
	}
	
	private void goBackMainThread(String string, boolean success) {
		isLoadTeamData = false;
		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		mineTeamAdapter = new MineTeamAdapter(getActivity().getApplicationContext());
		mineTeamAdapter.setData(xcfcTeams);
		lvTeam.setAdapter(mineTeamAdapter);
	}


	@Click
	void ivAddClient(){
		AddSubInstitutionActivity_.intent(getActivity()).start();
	}
}
