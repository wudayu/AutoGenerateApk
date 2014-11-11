package com.movitech.grande;

import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.NotificationId;
import com.movitech.grande.constant.PushMessageKeyCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcPushMessage;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcPushMessageResult;
import com.movitech.grande.PushService_;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.MainActivity_;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 21, 2014 8:32:35 PM
 * @Description: This is David Wu's property.
 *
 **/
@EService
public class PushService extends Service {

	@SystemService
	NotificationManager notificationManager;

	@Bean(NetHandler.class)
	INetHandler netHandler;

	@App
	MainApp mApp;

	Timer timer = null;
	long pushSpace = 0;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		//Utils.debug(Utils.TAG, "hehe, qidongle ~~~~~");
		pushSpace = Long.valueOf(getString(R.string.push_time_in_second));

		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		timer = new Timer();
		timer.schedule(new PushTimerTask(this), 5000, 1800000);

		return super.onStartCommand(intent, flags, startId);
	}

	class PushTimerTask extends TimerTask {

		Context context;

		public PushTimerTask(Context context) {
			this.context = context;
		}

		@Override
		public void run() {
			XcfcUser currUser = mApp.getCurrUser();
			/*String guestId = mApp.getGuestId();
			if (currUser == null && guestId == null)
				return;
            */
			if (currUser == null) {
				return;
			}
			//XcfcPushMessageResult rst = netHandler.postForGetPushMessage((currUser == null || currUser.getId() == null) ? guestId : currUser.getId());
			XcfcPushMessageResult rst = netHandler.postForGetPushMessage(currUser.getId());
			if (rst == null || rst.getResultSuccess() == false || rst.getObjValue() == null) {
				return;
			}

			XcfcPushMessage[] messages = rst.getObjValue();
			
			for (int i = 0; i < messages.length; ++i) {
				String keyCode = messages[i].getKeyCode();

				NotificationCompat.Builder mBuilder =
				        new NotificationCompat.Builder(context)
						.setAutoCancel(true)
				        .setSmallIcon(R.drawable.app_icon)
				        .setContentTitle(getString(R.string.app_name))//getString(R.string.app_name))
				        .setContentText(messages[i].getContent());
				// Creates an explicit intent for an Activity in your application
				Intent resultIntent = new Intent(context, MainActivity_.class);
				resultIntent.putExtra(ExtraNames.XCFC_PUSH_TYPE, keyCode);

				// The stack builder object will contain an artificial back stack for the
				// started Activity.
				// This ensures that navigating backward from the Activity leads out of
				// your application to the Home screen.
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
				// Adds the back stack for the Intent (but not the Intent itself)
				// stackBuilder.addParentStack(SplashActivity_.class);
				// Adds the Intent that starts the Activity to the top of the stack
				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent =
				        stackBuilder.getPendingIntent(
				            0,
				            PendingIntent.FLAG_UPDATE_CURRENT
				        );
				mBuilder.setContentIntent(resultPendingIntent);
			
				// mId allows you to update the notification later on.
				if (PushMessageKeyCode.MY_MESSAGE.equals(keyCode)) {
					notificationManager.notify(NotificationId.M, mBuilder.build());
				} else if (PushMessageKeyCode.INFO_POLICY.equals(keyCode)) {
					notificationManager.notify(NotificationId.Z, mBuilder.build());
				} else if (PushMessageKeyCode.ACTIVITY.equals(keyCode)) {
					notificationManager.notify(NotificationId.H, mBuilder.build());
				}
			}

			/*
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			// 新建一个通知，指定其图标和标题
			// 第一个参数为图标，第二个参数为标题，第三个参数为通知时间
			Notification notification = new Notification();
			notification.defaults = Notification.DEFAULT_SOUND;// 发出默认声音
			notification.flags = Notification.FLAG_ONGOING_EVENT;
			for (int i = 0; i < messages.length; i++) {
				Intent intent = new Intent(PushService.this, SplashActivity.class);
				intent.putExtra(ExtraNames.XCFC_PUSH_TYPE, messages[i].getKeyCode());
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//						| Intent.FLAG_ACTIVITY_NEW_TASK);
				// 当点击消息时就会向系统发送openintent意图
				PendingIntent contentIntent = PendingIntent.getActivity(PushService.this,
						R.string.app_name, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				notification.setLatestEventInfo(PushService.this, messages[i].getKeyCode(),
						messages[i].getContent(), contentIntent);
				mNotificationManager.notify(i, notification);// 发送通知
			}
			*/
		}
	}

	@Override
	public void onDestroy() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				PushService_.intent(getApplicationContext()).start();
			}
		}, 5000);

		super.onDestroy();
	}
}
