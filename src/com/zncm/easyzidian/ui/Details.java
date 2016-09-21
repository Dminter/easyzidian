package com.zncm.easyzidian.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zncm.easyzidian.db.ZiDataAdapter;
import com.zncm.easyzidian.pojo.Zi;
import com.zncm.easyzidian.view.AlwaysMarqueeTextView;
import com.zncm.easyzidian.view.KeywordsFlow;
import com.zncm.utils.CommonUtils;
import com.zncm.utils.SPUtils;

public class Details extends Activity {

	ZiDataAdapter dda = new ZiDataAdapter(Details.this);
	ArrayList<Zi> ziList = null;
	String key = "";
	int type = 1;
	GridViewAdapter gridViewAdapter = null;
	GridView gv01 = null;
	ProgressBar pb01 = null;
	TextView tv01 = null;
	TextView t_tv01 = null;
	Button t_btn01 = null;
	SPUtils spUtils = null;
	KeywordsFlow kf01 = null;
	RelativeLayout rl01, rl02;
	Button btn01;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		spUtils = new SPUtils(this);
		gv01 = (GridView) findViewById(R.id.details_gv01);
		pb01 = (ProgressBar) findViewById(R.id.details_pb01);
		tv01 = (TextView) findViewById(R.id.details_tv01);
		LinearLayout ll01 = (LinearLayout) findViewById(R.id.details_lay);
		t_tv01 = (TextView) ll01.findViewById(R.id.title_tv01);
		t_btn01 = (Button) ll01.findViewById(R.id.title_btn01);

		t_btn01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		rl01 = (RelativeLayout) findViewById(R.id.details_rl01);
		rl02 = (RelativeLayout) findViewById(R.id.details_rl02);
		kf01 = (KeywordsFlow) findViewById(R.id.details_kf01);
		btn01 = (Button) findViewById(R.id.details_btn01);
		if (getIntent().getStringExtra("type").equals("bs")) {
			t_btn01.setVisibility(View.VISIBLE);
			type = 1;
		} else if (getIntent().getStringExtra("type").equals("like")) {
			t_btn01.setVisibility(View.VISIBLE);
			type = 2;
		} else if (getIntent().getStringExtra("type").equals("study")) {
			type = 3;
			rl01.setVisibility(View.VISIBLE);
			rl02.setVisibility(View.GONE);
		}
		key = getIntent().getStringExtra("key");
		t_tv01.setText(key);

		if (getIntent().getStringExtra("type").equals("study")) {
			ChangeKeyWords();
			kf01.go2Show(KeywordsFlow.ANIMATION_OUT);
			kf01.setDuration(800l);
		} else {
			GetCategoryDreamInfo getCategoryDreamInfo = new GetCategoryDreamInfo();
			getCategoryDreamInfo.execute("");
		}

		kf01.setOnItemClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (view instanceof TextView) {
					String word = ((TextView) view).getText().toString();
					Intent intent = new Intent(Details.this, ZiDetails.class);
					intent.putExtra("word", word);
					startActivity(intent);
				}

			}
		});
		btn01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChangeKeyWords();
				kf01.go2Show(KeywordsFlow.ANIMATION_OUT);
				kf01.setDuration(800l);
			}
		});

	}

	private class GridViewAdapter extends BaseAdapter {

		Context context;
		ArrayList<Zi> ziList;

		public GridViewAdapter(Context context, ArrayList<Zi> ziList) {
			this.context = context;
			this.ziList = ziList;
		}

		@Override
		public int getCount() {

			return ziList.size();
		}

		@Override
		public Object getItem(int position) {

			return ziList.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater mInflater = LayoutInflater.from(context);
			convertView = mInflater.inflate(R.layout.item, null);
			AlwaysMarqueeTextView tv01 = (AlwaysMarqueeTextView) convertView
					.findViewById(R.id.item_tv01);
			TextView tv02 = (TextView) convertView.findViewById(R.id.item_tv02);
			TextView tv03 = (TextView) convertView.findViewById(R.id.item_tv03);
			RelativeLayout rl01 = (RelativeLayout) convertView
					.findViewById(R.id.item_rl01);
			tv01.setText(ziList.get(position).getPy());
			tv02.setText(ziList.get(position).getZi());
			tv03.setText(ziList.get(position).getBs());
			rl01.setBackgroundResource(R.drawable.bg_texture);
			return convertView;
		}
	}

	private void ChangeKeyWords() {
		ArrayList<String> temp = CommonUtils.TenRandom(20890);

		ArrayList<Zi> tempList = new ArrayList<Zi>();
		for (int i = 0; i < temp.size(); i++) {
			Zi zi = new Zi();
			zi = dda.QueryZiById(temp.get(i));
			tempList.add(zi);
		}
		kf01.rubKeywords();
		for (int i = 0; i < tempList.size(); i++) {
			kf01.feedKeyword(tempList.get(i).getZi());
		}
	}

	class GetCategoryDreamInfo extends AsyncTask<String, Integer, Integer> {

		/**
		 * 后台处理:获取软件列表
		 */
		protected Integer doInBackground(String... params) {
			int status = 0;
			ziList = new ArrayList<Zi>();
			switch (type) {
			case 1:
				ziList = dda.QueryZis("bs like '" + key + "%'", null);
				break;
			case 2:
				String[] like_items = null;
				if (spUtils.getVery_like().length() > 0) {
					like_items = spUtils.getVery_like().substring(1)
							.split("\\|");
					for (int i = 0; i < like_items.length; i++) {
						Zi zi = new Zi();
						zi = dda.QueryZi(like_items[i]);
						ziList.add(zi);
					}
				}
				break;
			default:
				break;
			}

			return status;

		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		protected void onPostExecute(Integer integer) {
			super.onPostExecute(integer);
			try {
				pb01.setVisibility(View.GONE);
				gridViewAdapter = new GridViewAdapter(Details.this, ziList);
				gv01.setAdapter(gridViewAdapter);
				if (ziList.size() == 0) {
					tv01.setText("什么没找到耶");
					tv01.setVisibility(View.VISIBLE);
				}

				gv01.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						Intent intent = new Intent(Details.this,
								ZiDetails.class);
						intent.putExtra("word", ziList.get(position).getZi());
						startActivity(intent);

					}
				});

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
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