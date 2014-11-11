package com.movitech.grande.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

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
public class SelectPicPopupWindow extends PopupWindow {

    private Button btnTakePhoto, btnPickPhoto, btnCancel;
    private View mMenuView;

	public SelectPicPopupWindow(Activity context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.photo_picker_popwindow, null);
		btnTakePhoto = (Button) mMenuView.findViewById(R.id.btn_take_photo);
		btnPickPhoto = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
		btnCancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
		// 取消按钮
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}

	public void setOnClickListener(OnClickListener itemsOnClick) {
		// 设置按钮监听
		btnPickPhoto.setOnClickListener(itemsOnClick);
		btnTakePhoto.setOnClickListener(itemsOnClick);
	}
}