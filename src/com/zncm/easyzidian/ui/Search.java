package com.zncm.easyzidian.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.umeng.analytics.MobclickAgent;
import com.zncm.easyzidian.db.ZiDataAdapter;
import com.zncm.easyzidian.pojo.Zi;
import com.zncm.utils.CommonUtils;

public class Search extends Activity implements OnCheckedChangeListener {

	ZiDataAdapter zda = new ZiDataAdapter(Search.this);
	ArrayList<Zi> ziList = null;
	String search_key = "";
	int search_type = 0;

	ListView lv01 = null;
	ProgressBar pb01 = null;
	EditText et01 = null;
	ImageView iv01 = null;

	TextView t_tv01 = null;
	Button t_btn01 = null;

	GridView gv01 = null;
	GridViewAdapter gridViewAdapter = null;

	RadioButton rb01 = null;
	RadioButton rb02 = null;
	RadioButton rb03 = null;
	TextView tv01 = null;
	Drawable t_scDrawable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		gv01 = (GridView) findViewById(R.id.search_gv01);
		pb01 = (ProgressBar) findViewById(R.id.search_pb01);
		et01 = (EditText) findViewById(R.id.search_et01);
		iv01 = (ImageView) findViewById(R.id.search_iv01);

		rb01 = (RadioButton) findViewById(R.id.search_rb01);
		rb02 = (RadioButton) findViewById(R.id.search_rb02);
		rb03 = (RadioButton) findViewById(R.id.search_rb03);
		rb01.setOnCheckedChangeListener(this);
		rb02.setOnCheckedChangeListener(this);
		rb03.setOnCheckedChangeListener(this);
		tv01 = (TextView) findViewById(R.id.search_tv01);
		tv01.setVisibility(View.GONE);

		LinearLayout ll01 = (LinearLayout) findViewById(R.id.search_lay);
		t_tv01 = (TextView) ll01.findViewById(R.id.title_tv01);
		t_btn01 = (Button) ll01.findViewById(R.id.title_btn01);
		t_btn01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// search_key = getIntent().getStringExtra("search_key");

		pb01.setVisibility(View.GONE);
		search_key = "搜索";
		t_tv01.setGravity(Gravity.CENTER);
		t_tv01.setText(search_key);

		rb01.setChecked(true);
		search_type = 0;
		iv01.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv01.setVisibility(View.VISIBLE);
				if (!et01.getText().toString().trim().equals("")) {
					if (search_type == 0) {
						search_key = et01.getText().toString().trim();
						if (CommonUtils.isChinese(search_key)) {
							GetSearchDreamInfo searchDreamInfo = new GetSearchDreamInfo();
							searchDreamInfo.execute("");
							pb01.setVisibility(View.VISIBLE);
						} else {
							tv01.setText("请输入中文字");
						}
					} else {
						search_key = et01.getText().toString().trim();
						GetSearchDreamInfo searchDreamInfo = new GetSearchDreamInfo();
						searchDreamInfo.execute("");
						pb01.setVisibility(View.VISIBLE);
					}

				} else {
					switch (search_type) {
					case 0:
						tv01.setText("请输入要查的字");
						break;
					case 1:
						tv01.setText("	请输入要查字的拼音");
						break;
					case 2:
						tv01.setText("	请输入要查字的笔画");

						break;

					default:
						break;
					}

				}

			}
		});

		et01.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView textView, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					search_key = et01.getText().toString().trim();
					GetSearchDreamInfo searchDreamInfo = new GetSearchDreamInfo();
					searchDreamInfo.execute("");
					pb01.setVisibility(View.VISIBLE);
				}
				return false;
			}
		});

		t_scDrawable = getResources().getDrawable(R.drawable.txt_search_clear);
		et01.addTextChangedListener(SearchTextChanged);
		// 左边显示搜索图标
		et01.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					int curX = (int) event.getX();
					if (curX > view.getWidth() - 38
							&& !TextUtils.isEmpty(et01.getText())) {
						et01.setText("");
						int cacheInputType = et01.getInputType();
						et01.setInputType(InputType.TYPE_NULL);
						et01.onTouchEvent(event);
						et01.setInputType(cacheInputType);
						return true;
					}
					break;
				}
				return false;
			}
		});

	}

	private class GridViewAdapter extends BaseAdapter {

		Context context;// 上下文
		ArrayList<Zi> ziList;

		/**
		 * 
		 * 构造函
		 * 
		 * @param context
		 * @param ziList
		 * 
		 */
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
			TextView tv01 = (TextView) convertView.findViewById(R.id.item_tv01);
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

	class GetSearchDreamInfo extends AsyncTask<String, Integer, Integer> {

		/**
		 * 后台处理:获取软件列表
		 */
		protected Integer doInBackground(String... params) {
			int status = 0;
			switch (search_type) {
			case 0:
				ziList = zda.QueryZis("zi = '" + search_key + "'", null);
				break;
			case 1:
				ziList = zda.QueryZis("py = '" + search_key + "'", null);
				break;
			case 2:
				ziList = zda.QueryZis("bh = '" + search_key + "'", null);
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

				if (ziList != null) {
					if (ziList.size() > 0) {
						tv01.setText("找到" + ziList.size() + "条记录.");
					} else {
						tv01.setText("什么也没有耶.");
					}
				} else {
					tv01.setText("什么也没有耶.");
				}

				gridViewAdapter = new GridViewAdapter(Search.this, ziList);
				gv01.setAdapter(gridViewAdapter);

				gv01.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						Intent intent = new Intent(Search.this, ZiDetails.class);
						intent.putExtra("word", ziList.get(position).getZi());
						startActivity(intent);

					}
				});

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.search_rb01:
			if (isChecked) {
				search_type = 0;
				et01.setText("");
				InputFilter[] filters = { new InputFilter.LengthFilter(1) };
				et01.setFilters(filters);
				et01.setInputType(InputType.TYPE_CLASS_TEXT);
				et01.setHint("请输入要查的字");
				rb02.setChecked(false);
				rb03.setChecked(false);
			}
			break;
		case R.id.search_rb02:
			if (isChecked) {
				search_type = 1;
				et01.setText("");
				InputFilter[] filters = { new InputFilter.LengthFilter(8) };
				et01.setFilters(filters);
				et01.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				et01.setHint("请输入要查字的拼音");
				rb01.setChecked(false);
				rb03.setChecked(false);
			}
			break;
		case R.id.search_rb03:
			if (isChecked) {
				search_type = 2;
				et01.setText("");
				InputFilter[] filters = { new InputFilter.LengthFilter(2) };
				et01.setFilters(filters);
				et01.setMaxWidth(2);
				et01.setInputType(InputType.TYPE_CLASS_NUMBER);
				et01.setHint("请输入要查字的笔画");
				rb01.setChecked(false);
				rb02.setChecked(false);
			}
			break;

		default:
			break;
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

	private TextWatcher SearchTextChanged = new TextWatcher() {
		private boolean isnull = true;

		@Override
		public void afterTextChanged(Editable s) {
			if (TextUtils.isEmpty(s)) {
				if (!isnull) {
					isnull = true;
				}
			} else {
				if (isnull) {
					et01.setCompoundDrawablesWithIntrinsicBounds(null, null,
							t_scDrawable, null);
					isnull = false;
				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}
	};

}