package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.MineTeamAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.model.XcfcTeam;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcTeamResult;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.AddSubInstitutionActivity_;
import com.movitech.grande.activity.SubInstitutionInfoActivity_;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月27日 上午9:52:41
 * 类说明
 */
@EActivity(R.layout.activity_mine_team)
public class MineTeamActivity extends BaseActivity{
   
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.iv_add_client)
	ImageView ivAddClient;
	
	@ViewById(R.id.lv_team)
	LoadDataListView lvTeam;
	
	MineTeamAdapter mineTeamAdapter = null;
	XcfcTeam [] xcfcTeams = null;
	
	
	@App
	MainApp mApp;
	@Bean(NetHandler.class)
	INetHandler netHandler;
	
	@AfterViews
	void afterViews(){
		initTeamPages();
		doLoadDataTeam();
	}
	
	@Click
	void ivBack(){
		this.finish();
	}
	
	@Click
	void ivAddClient(){
		AddSubInstitutionActivity_.intent(context).start();
	}
	
	private void initTeamPages() {
		lvTeam.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				XcfcTeam team = ((MineTeamAdapter.ViewHolder) view.getTag()).xcfcTeam;
				Intent intent = new Intent(MineTeamActivity.this, SubInstitutionInfoActivity_.class);
				intent.putExtra(ExtraNames.TEAM_SUB_ID, team.getId());
				startActivity(intent);
			}
		});
	}
	@Background
	void doLoadDataTeam(){
		String id="d40c2265b5a44b8b95a57a0f6026b206";
		XcfcTeamResult teamResult = netHandler.postForGetTeamList(id);
		
		if (null == teamResult) {
			goBackMainThread("",false);
			return;
		}
		if (teamResult.getResultSuccess() == false) {
			goBackMainThread("",false);
			return;
		}
		xcfcTeams = teamResult.getTeams();
		//teamTotal = team
		goBackMainThread("", true);
		
	}
	
	private void goBackMainThread(String string, boolean success) {
		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		mineTeamAdapter = new MineTeamAdapter(MineTeamActivity.this);
		mineTeamAdapter.setData(xcfcTeams);
		lvTeam.setAdapter(mineTeamAdapter);
	}


}
