package com.zncm.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UiUtils {

	public static void TextToastShow(Context context, String msg) {

		Toast toast = new Toast(context);
		TextView textView = new TextView(context);
		LinearLayout mLinearLayout = new LinearLayout(context);
		textView.setText(msg);
		textView.setTextColor(Color.WHITE);
		textView.setTextSize(12);
		textView.setWidth(400);
		textView.setMinWidth(10);
		textView.setPadding(10, 10, 10, 10);
		// textView.setBackgroundResource(R.drawable.toast_bg);
		textView.setGravity(Gravity.CENTER);
		mLinearLayout.addView(textView);
		toast.setView(mLinearLayout);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}
}