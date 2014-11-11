package com.movitech.grande.views;

import com.movitech.grande.haerbin.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MutilLineTextViewLayout extends LinearLayout {
	
	//每行显示3个
	private int PEER_LINE_NUMS=3;
	
	public MutilLineTextViewLayout(Context context, String[] contents) {
		super(context);
		this.removeAllViews();
		this.setOrientation(VERTICAL);  
		
		int tempVar = contents.length%PEER_LINE_NUMS;
		int lines=1;
		if (tempVar == 0) {
			lines = contents.length/PEER_LINE_NUMS;
		} else {
			lines = contents.length/PEER_LINE_NUMS + 1;
		}
		
		int index = 0;
		for(int i=1;i<=lines;i++) { 
			LinearLayout line=new LinearLayout(context);
			line.setOrientation(LinearLayout.HORIZONTAL);
			line.setPadding((int)context.getResources().getDimension(R.dimen.dp_five), 0, 0, 0);
			for (int j = 1; j <= PEER_LINE_NUMS; j++) {
				if (index > contents.length - 1)
					break;
				TextView txt = new TextView(context);
				txt.setText(contents[index]);
				txt.setGravity(Gravity.LEFT);
				txt.setTextSize(12.0f);
				index ++;
				//txt.setL
			    line.addView(txt, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,1.0f));
			}
			addView(line, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}

	public MutilLineTextViewLayout(Context context) {
		super(context);
		
	}

	@SuppressLint("NewApi")
	public MutilLineTextViewLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public MutilLineTextViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

}
