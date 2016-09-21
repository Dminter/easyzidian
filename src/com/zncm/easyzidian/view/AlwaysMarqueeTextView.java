package com.zncm.easyzidian.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 一直滚动的TextView[跑马灯效果]
 * 
 * @Description :
 * @author Dminter
 * @version 1.0
 * @created Oct 16, 2012 3:27:37 PM
 * @fileName com.chinagyl.appstore.view.AlwaysMarqueeTextView.java
 * 
 */
public class AlwaysMarqueeTextView extends TextView {

	public AlwaysMarqueeTextView(Context context) {
		super(context);
	}

	public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AlwaysMarqueeTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}