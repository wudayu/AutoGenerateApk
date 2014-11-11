package com.movitech.grande.receiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.movitech.grande.PushService_;

/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 22, 2014 9:02:28 AM
 * @Description: This is David Wu's property.
 *
 **/
public class BootCompletedReceiver extends BroadcastReceiver {

//	@Override
//	public void onReceive(Context context, Intent intent) {
//		PushService_.intent(context).start();
//	}

	private PendingIntent mAlarmSender;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 启动一个定时调度程序，每30分钟启动一个Service去更新数据
        mAlarmSender = PendingIntent.getService(context, 0, new Intent(context,
                PushService_.class), 0);
        long firstTime = SystemClock.elapsedRealtime();
        AlarmManager am = (AlarmManager) context
                .getSystemService(Activity.ALARM_SERVICE);
        am.cancel(mAlarmSender);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,
                30 * 1000, mAlarmSender);//30 * 60 * 1000, mAlarmSender);
    }
}
