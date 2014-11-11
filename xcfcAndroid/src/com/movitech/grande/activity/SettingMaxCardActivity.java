package com.movitech.grande.activity;

import java.io.UnsupportedEncodingException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.movitech.grande.MainApp;
import com.movitech.grande.haerbin.R;

/**
 * 
 * @author: Tao Yangjun
 * @En_Name: Potter Tao
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 19, 2014 13:48:10 PM
 * @Description: This is Potter Tao's property.
 * 
 **/

/**
 * 本demo是仿微信的二维码名片 本身google的二维码是一个开源的项目我们要制作一个二维码很简单 本例的作用是将图片与二维码结合，当然图片不能太大
 * ，要不然二维码读不出来。
 */
@EActivity(R.layout.activity_setting_max_card)
public class SettingMaxCardActivity extends BaseActivity {

	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	// 显示二维码图片
	@ViewById(R.id.iv_max_card)
	ImageView ivMaxCard;

	@App
	MainApp mApp;

	// 图片宽度的一般
	private static final int IMAGE_HALFWIDTH = 60;

	// 插入到二维码里面的图片对象
	private Bitmap mBitmap;
	// 需要插图图片的大小 这里设定为40*40
//	int[] pixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];

	// 返回按钮点击事件
	@Click
	void ivBack() {
		this.finish();
	}

	@AfterViews
	void afterViews() {
		// 构造需要插入的图片对象
		mBitmap = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.default_avatar)).getBitmap();
		// 缩放图片
		Matrix m = new Matrix();
		float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
		float sy = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getHeight();
		m.setScale(sx, sy);
		// 重新构造一个40*40的图片
		mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
				mBitmap.getHeight(), m, false);

		try {
			String s = mApp.getCurrUser().getId();
			ivMaxCard.setImageBitmap(cretaeBitmap(new String(s.getBytes(),
					"ISO-8859-1")));
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码
	 * 
	 * @param 字符串
	 * @return Bitmap
	 * @throws WriterException
	 */
	public Bitmap cretaeBitmap(String str) throws WriterException {
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 600, 600);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int halfW = width / 2;
		int halfH = height / 2;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
						&& y > halfH - IMAGE_HALFWIDTH
						&& y < halfH + IMAGE_HALFWIDTH) {
					pixels[y * width + x] = mBitmap.getPixel(x - halfW
							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
				} else {
					if (matrix.get(x, y)) {
						pixels[y * width + x] = 0xff000000;
					}
				}

			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		return bitmap;
	}
}
