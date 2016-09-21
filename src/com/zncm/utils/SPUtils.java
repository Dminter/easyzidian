package com.zncm.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

	Context context;
	SharedPreferences sp;

	public SPUtils(Context context) {
		this.context = context;
		this.sp = this.context.getSharedPreferences("sp_info", 0);
	}

	public String getVery_like() {
		return this.sp.getString("very_like", "");

	}

	public void setVery_like(String very_like) {
		SharedPreferences.Editor editor = this.sp.edit();
		editor.putString("very_like", very_like);
		editor.commit();
	}

}
