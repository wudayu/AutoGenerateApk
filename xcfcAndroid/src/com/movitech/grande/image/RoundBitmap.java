package com.movitech.grande.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月5日 下午1:31:40 类说明
 */

public class RoundBitmap {
	// 图片圆角处理
	public static Bitmap getRoundedBitmap(Bitmap mBitmap, float round) {
		try {
			// 创建新的位图
			Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
					mBitmap.getHeight(), Config.ARGB_8888);
			// 把创建的位图作为画板
			Canvas mCanvas = new Canvas(bgBitmap);
			Paint mPaint = new Paint();
			Rect mRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
			RectF mRectF = new RectF(mRect);
			// 设置圆角半径为20
			float roundPx = round;
			mPaint.setAntiAlias(true);
			// 先绘制圆角矩形
			mCanvas.drawRoundRect(mRectF, roundPx, roundPx, mPaint);
			// 设置图像的叠加模式
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			// 绘制图像
			mCanvas.drawBitmap(mBitmap, mRect, mRect, mPaint);
			return bgBitmap;
		} catch (OutOfMemoryError e) {

		} catch (Exception e) {
			// TODO: handle exception
		}
		return mBitmap;

	}
}
