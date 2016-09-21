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

public class More extends Activity {
	TextView t_tv01 = null;
	Button t_btn01 = null;
	TableRow tr01, tr02, tr03;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		LinearLayout ll01 = (LinearLayout) findViewById(R.id.more_lay);
		t_tv01 = (TextView) ll01.findViewById(R.id.title_tv01);
		t_btn01 = (Button) ll01.findViewById(R.id.title_btn01);
		t_btn01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		t_tv01.setText("更多");
		tr01 = (TableRow) findViewById(R.id.more_tr01);
		tr02 = (TableRow) findViewById(R.id.more_tr02);
		tr03 = (TableRow) findViewById(R.id.more_tr03);
		tr01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(More.this, Details.class);
				intent.putExtra("type", "like");
				intent.putExtra("key", "我的收藏");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
		tr02.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				UMFeedbackService.openUmengFeedbackSDK(More.this);
			}
		});
		tr03.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(More.this, About.class);
				startActivity(intent);

			}
		});
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