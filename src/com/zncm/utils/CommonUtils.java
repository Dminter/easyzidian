package com.zncm.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsManager;
import android.widget.Toast;

public class CommonUtils {

	public static boolean isChinese(String str) {
		boolean isChinese = false;
		if (str.length() < str.getBytes().length) {
			isChinese = true;
		} else {
			isChinese = false;
		}
		return isChinese;
	}

	public static ArrayList<String> TenRandom(int max) {
		ArrayList<String> temp = new ArrayList<String>();
		Random random = new Random();
		HashSet<Integer> set = new HashSet<Integer>();
		while (true) {
			int number = random.nextInt(max);
			set.add(number);
			if (set.size() == 15)
				break;
		}
		for (Iterator it = set.iterator(); it.hasNext();) {
			temp.add(it.next().toString());
		}

		return temp;

	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}

	}

	public static void showText(Context context,String text) {
		Toast.makeText(context, text,
				Toast.LENGTH_SHORT).show();
	}

	public static void sendMessageToContact(Context context, String content,
			String phoneNumber) {

		SmsManager smsManager = SmsManager.getDefault();
		ArrayList<String> messages = smsManager.divideMessage(content);
		for (String message : messages) {
			smsManager.sendTextMessage(phoneNumber, null, message, null, null);
		}
		UiUtils.TextToastShow(context, "短信发送成功.");
	}

	public static String getVersionName(Context context) {
		String versionName = "未知";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return versionName;
	}
}
