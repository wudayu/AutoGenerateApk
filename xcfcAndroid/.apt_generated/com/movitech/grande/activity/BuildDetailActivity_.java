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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.mapapi.map.MapView;
import com.movitech.grande.MainApp_;
import com.movitech.grande.generic.ImageUtils_;
import com.movitech.grande.haerbin.R.id;
import com.movitech.grande.haerbin.R.layout;
import com.movitech.grande.net.NetHandler_;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.views.CirclePageIndicator;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class BuildDetailActivity_
    extends BuildDetailActivity
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
        setContentView(layout.activity_build_detail);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        mApp = MainApp_.getInstance();
        netHandler = NetHandler_.getInstance_(this);
        imageUtils = ImageUtils_.getInstance_(this);
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

    public static BuildDetailActivity_.IntentBuilder_ intent(Context context) {
        return new BuildDetailActivity_.IntentBuilder_(context);
    }

    public static BuildDetailActivity_.IntentBuilder_ intent(Fragment supportFragment) {
        return new BuildDetailActivity_.IntentBuilder_(supportFragment);
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
        circleIndicator = ((CirclePageIndicator) hasViews.findViewById(id.circle_indicator));
        tvDeliveryDate = ((TextView) hasViews.findViewById(id.tv_delivery_date));
        txtHousesSynopsis = ((TextView) hasViews.findViewById(id.txt_houses_synopsis));
        ivIntroduceFold = ((ImageView) hasViews.findViewById(id.iv_introduce_fold));
        llIntroduceBuild = ((LinearLayout) hasViews.findViewById(id.ll_introduce_build));
        btnAppointmentImmediately = ((Button) hasViews.findViewById(id.btn_appointment_immediately));
        tvDecorationSituation = ((TextView) hasViews.findViewById(id.tv_decoration_situation));
        ivFav = ((ImageView) hasViews.findViewById(id.iv_fav));
        tvOpenDate = ((TextView) hasViews.findViewById(id.tv_open_date));
        tvRealestateCto = ((TextView) hasViews.findViewById(id.tv_realestate_cto));
        txtMapArea = ((TextView) hasViews.findViewById(id.txt_map_area));
        tvEquity = ((TextView) hasViews.findViewById(id.tv_equity));
        txtPrivilegeHouses = ((TextView) hasViews.findViewById(id.txt_privilege_houses));
        btnRecommendImmediately = ((Button) hasViews.findViewById(id.btn_recommend_immediately));
        ivTrafficFold = ((ImageView) hasViews.findViewById(id.iv_traffic_fold));
        bmapView = ((MapView) hasViews.findViewById(id.bmap_view));
        tvTraffic = ((TextView) hasViews.findViewById(id.tv_traffic));
        txtAppointmentNum = ((TextView) hasViews.findViewById(id.txt_appointment_num));
        llHustylePic = ((LinearLayout) hasViews.findViewById(id.ll_hustyle_pic));
        txtPriceHouses = ((TextView) hasViews.findViewById(id.txt_price_houses));
        ivMap = ((ImageView) hasViews.findViewById(id.iv_map));
        txtPrivilegeDate = ((TextView) hasViews.findViewById(id.txt_privilege_date));
        txtCommission = ((TextView) hasViews.findViewById(id.txt_commission));
        txtPhone = ((TextView) hasViews.findViewById(id.txt_phone));
        llPhone = ((LinearLayout) hasViews.findViewById(id.ll_phone));
        tvBuildArea = ((TextView) hasViews.findViewById(id.tv_build_area));
        ivBack = ((ImageView) hasViews.findViewById(id.iv_back));
        txtRecommendNum = ((TextView) hasViews.findViewById(id.txt_recommend_num));
        vpBannerBuildPic = ((BaseViewPager) hasViews.findViewById(id.vp_banner_build_pic));
        tvBuildSpecial = ((TextView) hasViews.findViewById(id.tv_build_special));
        llIntroduceFold = ((LinearLayout) hasViews.findViewById(id.ll_introduce_fold));
        tvPrice = ((TextView) hasViews.findViewById(id.tv_price));
        tvBuildType = ((TextView) hasViews.findViewById(id.tv_build_type));
        txtNameHouses = ((TextView) hasViews.findViewById(id.txt_name_houses));
        tvRateGreen = ((TextView) hasViews.findViewById(id.tv_rate_green));
        ivShare = ((ImageView) hasViews.findViewById(id.iv_share));
        llTrafficFold = ((LinearLayout) hasViews.findViewById(id.ll_traffic_fold));
        llTrafficBuild = ((LinearLayout) hasViews.findViewById(id.ll_traffic_build));
        txtSectionHouses = ((TextView) hasViews.findViewById(id.txt_section_houses));
        {
            View view = hasViews.findViewById(id.iv_back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.ivBack();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.btn_appointment_immediately);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.btnAppointmentImmediately();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.btn_recommend_immediately);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.btnRecommendImmediately();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_introduce_fold);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.llIntroduceFold();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_traffic_fold);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.llTrafficFold();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.bmap_view);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.bmapView();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.iv_fav);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.ivFav();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ll_phone);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.llPhone();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.iv_share);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.ivShare();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.iv_map);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BuildDetailActivity_.this.ivMap();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public void goBackMainThread(final String msg, final boolean success) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                BuildDetailActivity_.super.goBackMainThread(msg, success);
            }

        }
        );
    }

    @Override
    public void goBackMainThreadFav(final String msg, final boolean success, final String isLike) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                BuildDetailActivity_.super.goBackMainThreadFav(msg, success, isLike);
            }

        }
        );
    }

    @Override
    public void switchPage() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                BuildDetailActivity_.super.switchPage();
            }

        }
        );
    }

    @Override
    public void goBackMainIsCollect(final String msg, final boolean succeed) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                BuildDetailActivity_.super.goBackMainIsCollect(msg, succeed);
            }

        }
        );
    }

    @Override
    public void doLoadIsCollect() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    BuildDetailActivity_.super.doLoadIsCollect();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    @Override
    public void doLoadDataAndBindData() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("queryData", 0, "") {


            @Override
            public void execute() {
                try {
                    BuildDetailActivity_.super.doLoadDataAndBindData();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    @Override
    public void doLoadFavBuild(final String isLike) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    BuildDetailActivity_.super.doLoadFavBuild(isLike);
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
            intent_ = new Intent(context, BuildDetailActivity_.class);
        }

        public IntentBuilder_(Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, BuildDetailActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public BuildDetailActivity_.IntentBuilder_ flags(int flags) {
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