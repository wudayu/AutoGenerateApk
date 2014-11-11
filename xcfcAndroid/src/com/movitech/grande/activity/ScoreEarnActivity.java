package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcEarnIntegral;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcDayMarkResult;
import com.movitech.grande.net.protocol.XcfcEarnIntegralResult;
import com.movitech.grande.utils.NetWorkUtil;
import com.movitech.grande.views.AutoScaleTextView;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.ScoreRuleActivity_;

/**
 * 
 * @author Warkey.Song
 * @Description: This is Earn Point Activity.
 */
@EActivity(R.layout.activity_earn_score)
public class ScoreEarnActivity extends BaseActivity {
	private static final String IS_MARK = "0";
	private static final int SIGNSCORE = 0;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.rl_bottom)
	RelativeLayout rlBottom;

	@ViewById(R.id.tv_today_score)
	AutoScaleTextView tvTodayScore;
	@ViewById(R.id.tv_total_score)
	AutoScaleTextView tvTotalScore;
	@ViewById(R.id.tv_today_complete)
	TextView tvTodayComplete;

	@ViewById(R.id.rl_right)
	RelativeLayout rlRight;// 每日签到按钮

	@Bean(NetHandler.class)
	NetHandler netHandler;
	@App
	MainApp mApp;

	XcfcEarnIntegral earnIntegral = null;
	NetWorkUtil netWorkUtil = null;
	@SuppressLint("ResourceAsColor")
	@AfterViews
	void afterViews() {
		
		netWorkUtil = new NetWorkUtil(context);
		if (!netWorkUtil.isNetworkConnected()) {
			return;
		}
		doLoadIntegralData();
	}

	@Click
	void ivBack() {
		finish();
	}

	@Click
	void rlRight() {
		doLoadDayMark();
	}

	@Click
	void rlBottom() {
		ScoreRuleActivity_.intent(context).start();
	}

	@Background
	void doLoadIntegralData() {
		XcfcEarnIntegralResult integralResult = netHandler.postForGetEarnIntegral(mApp.getCurrUser().getId());
		if (null == integralResult) {
			goBackMainThreadIntegral(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (integralResult.getResultSuccess() == false) {
			goBackMainThreadIntegral(integralResult.getResultMsg(), false);
			return;
		}
		earnIntegral = integralResult.getObjValue();
		goBackMainThreadIntegral("", true);
	}

	private void goBackMainThreadIntegral(String msg, boolean succeed) {
		Utils.toastMessage(this, msg);
		if (succeed) {
			doBindIntegralData();
		}
	}

	@UiThread
	void doBindIntegralData() {
		tvTodayScore.setText(earnIntegral.getIntegral());
		tvTotalScore.setText(earnIntegral.getIntegrals());
		if (earnIntegral.getIsMark().equals(IS_MARK)) {
			tvTodayComplete.setTextColor(getResources().getColor(R.color.col_text_recommend_yellow));
			tvTodayComplete.setText(getString(R.string.txt_score_daily_sign));
			rlRight.setClickable(true);

		} else {
			tvTodayComplete.setTextColor(getResources().getColor(R.color.col_earn_already_succeed));
			tvTodayComplete.setText(getString(R.string.txt_score_already_succeed));
			rlRight.setClickable(false);
		}
	}

	@Background
	void doLoadDayMark() {
		XcfcDayMarkResult markResult = netHandler.postForCheckDayMark(mApp.getCurrUser().getId());
		if (null == markResult) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (markResult.getResultSuccess() == false) {
			goBackMainThread(markResult.getResultMsg(), false);
			return;
		}
		goBackMainThread("", true);
	}

	@SuppressLint("ResourceAsColor")
	@UiThread
	void goBackMainThread(String msg, boolean succeed) {
		Utils.toastMessageForce(ScoreEarnActivity.this, msg);
		if (succeed) {
			Utils.toastMessageForce(ScoreEarnActivity.this, getString(R.string.toast_daymark));
			String todayScoreNum = tvTodayScore.getText().toString().trim();
			int todayScore = 0;
			if (todayScoreNum != null && (!todayScoreNum.equals(""))) {
				todayScore = Integer.valueOf(todayScoreNum) + SIGNSCORE;
			}
			tvTodayScore.setText(todayScore + "");

			String totalScoreNum = tvTotalScore.getText().toString().trim();
			int totalScore = 0;
			if (totalScoreNum != null && (!totalScoreNum.equals(""))) {
				totalScore = Integer.valueOf(totalScoreNum) + SIGNSCORE;
			}
			tvTotalScore.setText(totalScore + "");
			tvTodayComplete.setTextColor(getResources().getColor(R.color.col_earn_already_succeed));
			tvTodayComplete.setText(getString(R.string.txt_score_already_succeed));
			rlRight.setClickable(false);

		} 
	}
}
