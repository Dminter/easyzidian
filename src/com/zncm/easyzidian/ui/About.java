package com.zncm.easyzidian.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.UMFeedbackService;

public class About extends Activity {
	TextView t_tv01 = null;
	Button t_btn01 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		LinearLayout ll01 = (LinearLayout) findViewById(R.id.about_lay);
		t_tv01 = (TextView) ll01.findViewById(R.id.title_tv01);

		t_btn01 = (Button) ll01.findViewById(R.id.title_btn01);
		t_btn01.setVisibility(View.VISIBLE);
		t_btn01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		t_tv01.setText("帮助关于");
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}