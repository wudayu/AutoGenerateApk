//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.movitech.grande.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.movitech.grande.generic.ImageUtils_;
import com.movitech.grande.haerbin.R.id;
import com.movitech.grande.haerbin.R.layout;
import com.movitech.grande.net.NetHandler_;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class SubInstitutionInfoActivity_
    extends SubInstitutionInfoActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_institution_infomation);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        imageUtils = ImageUtils_.getInstance_(this);
        netHandler = NetHandler_.getInstance_(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static SubInstitutionInfoActivity_.IntentBuilder_ intent(Context context) {
        return new SubInstitutionInfoActivity_.IntentBuilder_(context);
    }

    public static SubInstitutionInfoActivity_.IntentBuilder_ intent(Fragment supportFragment) {
        return new SubInstitutionInfoActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        rlAccountStatus = ((RelativeLayout) hasViews.findViewById(id.rl_account_status));
        llGrantCommission = ((LinearLayout) hasViews.findViewById(id.ll_grant_commission));
        txtCountRecommendSuccessed = ((TextView) hasViews.findViewById(id.txt_count_recommend_successed));
        llEarnedCommission = ((LinearLayout) hasViews.findViewById(id.ll_earned_commission));
        llImportentClient = ((RelativeLayout) hasViews.findViewById(id.ll_importent_client));
        txtWaitCommission = ((TextView) hasViews.findViewById(id.txt_wait_commission));
        txtEarnedCommission = ((TextView) hasViews.findViewById(id.txt_earned_commission));
        llSellSuccessed = ((RelativeLayout) hasViews.findViewById(id.ll_sell_successed));
        txtCountSeaHouse = ((TextView) hasViews.findViewById(id.txt_count_sea_house));
        txtTbStatus = ((TextView) hasViews.findViewById(id.txt_tb_status));
        ivUserImage = ((ImageView) hasViews.findViewById(id.iv_user_image));
        llWaitCommission = ((LinearLayout) hasViews.findViewById(id.ll_wait_commission));
        llRecommendSuccessed = ((RelativeLayout) hasViews.findViewById(id.ll_recommend_successed));
        ivBack = ((ImageView) hasViews.findViewById(id.iv_back));
        llClientBlockFragmentMine = ((LinearLayout) hasViews.findViewById(id.ll_client_block_fragment_mine));
        llSeaHouse = ((RelativeLayout) hasViews.findViewById(id.ll_sea_house));
        rlCommissionInfo = ((LinearLayout) hasViews.findViewById(id.rl_commission_info));
        llRecommendConfirmed = ((RelativeLayout) hasViews.findViewById(id.ll_recommend_confirmed));
        txtNameInstitution = ((TextView) hasViews.findViewById(id.txt_name_institution));
        tbSelected = ((ToggleButton) hasViews.findViewById(id.tb_selected));
        txtCountSellSuccessed = ((TextView) hasViews.findViewById(id.txt_count_sell_successed));
        rlPersonalInfo = ((RelativeLayout) hasViews.findViewById(id.rl_personal_info));
        txtCommissionTotal = ((TextView) hasViews.findViewById(id.txt_commission_total));
        txtCountRecommendConfirmed = ((TextView) hasViews.findViewById(id.txt_count_recommend_confirmed));
        rlUserImage = ((RelativeLayout) hasViews.findViewById(id.rl_user_image));
        txtCountImportentClient = ((TextView) hasViews.findViewById(id.txt_count_importent_client));
        txtGrantCommission = ((TextView) hasViews.findViewById(id.txt_grant_commission));
        txtName = ((TextView) hasViews.findViewById(id.txt_name));
        {
            View view = hasViews.findViewById(id.ll_grant_commission);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SubInstitutionInfoActivity_.this.llGrantCommission();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_wait_commission);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SubInstitutionInfoActivity_.this.llWaitCommission();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_sea_house);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SubInstitutionInfoActivity_.this.llSeaHouse();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_recommend_confirmed);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SubInstitutionInfoActivity_.this.llRecommendConfirmed();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.iv_back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SubInstitutionInfoActivity_.this.ivBack();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_importent_client);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SubInstitutionInfoActivity_.this.llImportentClient();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_sell_successed);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SubInstitutionInfoActivity_.this.llSellSuccessed();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_recommend_successed);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SubInstitutionInfoActivity_.this.llRecommendSuccessed();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_earned_commission);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        SubInstitutionInfoActivity_.this.llEarnedCommission();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public void doBindData() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                SubInstitutionInfoActivity_.super.doBindData();
            }

        }
        );
    }

    @Override
    public void goBackMainThreadTogBtn(final String msg, final boolean success) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                SubInstitutionInfoActivity_.super.goBackMainThreadTogBtn(msg, success);
            }

        }
        );
    }

    @Override
    public void doLoadSubOrgStatus(final String status) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    SubInstitutionInfoActivity_.super.doLoadSubOrgStatus(status);
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    @Override
    public void doLoadDataInstitution() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    SubInstitutionInfoActivity_.super.doLoadDataInstitution();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, SubInstitutionInfoActivity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, SubInstitutionInfoActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public SubInstitutionInfoActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent_, requestCode);
            } else {
                if (context_ instanceof Activity) {
                    ((Activity) context_).startActivityForResult(intent_, requestCode);
                } else {
                    context_.startActivity(intent_);
                }
            }
        }

    }

}