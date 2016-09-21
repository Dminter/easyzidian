package com.zncm.easyzidian.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class BSGrid extends Activity {
	String[] bu_shou_items_text = null;
	GridView gridView = null;
	GridAdapter gridAdapter = null;
	TextView t_tv01 = null;
	Button t_btn01 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);
		bu_shou_items_text = getResources().getStringArray(
				R.array.bu_shou_items);
		LinearLayout ll01 = (LinearLayout) findViewById(R.id.grid_lay);
		t_tv01 = (TextView) ll01.findViewById(R.id.title_tv01);
		t_btn01 = (Button) ll01.findViewById(R.id.title_btn01);
		t_btn01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		t_tv01.setText("偏旁");
		gridView = (GridView) findViewById(R.id.grid_gv01);
		gridAdapter = new GridAdapter(BSGrid.this);
		gridView.setAdapter(gridAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent(BSGrid.this, Details.class);
				intent.putExtra("type", "bs");
				intent.putExtra("key", bu_shou_items_text[position]);
				startActivity(intent);
			}
		});

	}

	class GridAdapter extends BaseAdapter {
		Context context = null;

		public GridAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {

			return bu_shou_items_text.length;
		}

		@Override
		public Object getItem(int position) {

			return bu_shou_items_text[position];
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(context);
			textView.setText(bu_shou_items_text[position]);
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(20);
			textView.setGravity(Gravity.CENTER);
			textView.setBackgroundResource(R.drawable.bg_texture);
			return textView;
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