package com.zncm.easyzidian.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zncm.easyzidian.db.ZiDataAdapter;
import com.zncm.easyzidian.pojo.Zi;
import com.zncm.utils.CommonUtils;
import com.zncm.utils.SPUtils;

public class ZiDetails extends Activity {
	Dialog pb_dialog;
	ZiDataAdapter dda = new ZiDataAdapter(ZiDetails.this);
	String word = "";
	TextView tv01, tv02, tv03, tv04, tv05, tv06 = null;
	TextView t_tv01;
	ImageView t_iv02;

	Button t_btn01;

	Zi zi;
	boolean is_collected = false;
	SPUtils spUtils = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zi_details);
		word = getIntent().getExtras().getString("word");

		spUtils = new SPUtils(this);
		LinearLayout ll01 = (LinearLayout) findViewById(R.id.zi_details_lay);
		t_tv01 = (TextView) ll01.findViewById(R.id.title_tv01);
		t_btn01 = (Button) ll01.findViewById(R.id.title_btn01);
		t_btn01.setVisibility(View.VISIBLE);
		t_btn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		t_iv02 = (ImageView) ll01.findViewById(R.id.title_iv02);
		t_iv02.setVisibility(View.VISIBLE);
		is_collected = false;
		String[] like_items = null;
		if (spUtils.getVery_like().length() > 0) {
			like_items = spUtils.getVery_like().substring(1).split("\\|");
			for (int i = 0; i < like_items.length; i++) {
				if (word.equals(like_items[i])) {
					is_collected = true;
					break;
				}
			}
		}

		if (is_collected) {
			t_iv02.setImageResource(R.drawable.contact_star);
		} else {
			t_iv02.setImageResource(R.drawable.contact_notstar);
		}

		t_iv02.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (is_collected) {
					spUtils.setVery_like(spUtils.getVery_like().replace(
							"|" + word, ""));
					t_iv02.setImageResource(R.drawable.contact_notstar);
					is_collected = false;
				} else {
					spUtils.setVery_like(spUtils.getVery_like() + "|" + word);
					t_iv02.setImageResource(R.drawable.contact_star);
					is_collected = true;
				}
			}
		});
		tv01 = (TextView) findViewById(R.id.zi_details_tv01);
		tv02 = (TextView) findViewById(R.id.zi_details_tv02);
		tv03 = (TextView) findViewById(R.id.zi_details_tv03);
		tv04 = (TextView) findViewById(R.id.zi_details_tv04);
		tv05 = (TextView) findViewById(R.id.zi_details_tv05);
		tv06 = (TextView) findViewById(R.id.zi_details_tv06);
		ShowDialog();
		GetZiData getZiData = new GetZiData();
		getZiData.execute("");

	}

	class GetZiData extends AsyncTask<String, Integer, Integer> {
		protected Integer doInBackground(String... params) {
			int status = 0;
			zi = dda.QueryZi(word);

			return status;

		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		protected void onPostExecute(Integer integer) {
			super.onPostExecute(integer);

			pb_dialog.dismiss();

			tv01.setText(zi.getZi());
			t_tv01.setText(zi.getZi());
			tv02.setText("拼音:" + zi.getPy2());
			tv03.setText("笔画:" + zi.getBh());
			tv04.setText("部首:" + zi.getBs());
			tv05.setText("网络释义");
			tv05.setMovementMethod(LinkMovementMethod.getInstance());
			tv06.setText(zi.getJs());

			tv05.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					if (CommonUtils.isNetworkAvailable(ZiDetails.this)) {

						Intent intent = new Intent(ZiDetails.this,
								BikeWebView.class);
						intent.putExtra("word", zi.getZi());
						startActivity(intent);
					} else {
						CommonUtils.showText(ZiDetails.this, "请检查网络连接.");
					}

				}
			});

		}
	}

	private void ShowDialog() {
		LayoutInflater mInflater = LayoutInflater.from(ZiDetails.this);
		final View view = mInflater.inflate(R.layout.loading, null);
		pb_dialog = new AlertDialog.Builder(ZiDetails.this).setView(view)
				.create();
		pb_dialog.show();
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