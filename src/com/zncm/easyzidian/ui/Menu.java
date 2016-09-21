package com.zncm.easyzidian.ui;

import java.util.ArrayList;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * 主界面底部菜单
 * 
 * @author 浙水之南
 * @2013-1-14 下午4:47:42
 */
public class Menu extends TabActivity {

	TabHost tabHost;
	private String menu_items_title[] = null;
	TabWidget tabWidget = null;
	ArrayList<TextView> textViews;
	LinearLayout temp_layout = null;
	ArrayList<LinearLayout> temp_layouts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tabs);

		menu_items_title = getResources().getStringArray(
				R.array.bottom_menu_items);
		tabHost = getTabHost();
		tabHost.setup();
		textViews = new ArrayList<TextView>();
		temp_layouts = new ArrayList<LinearLayout>();
		Intent intent0 = new Intent(this, Search.class);//
		createTab(0, intent0);
		Intent intent1 = new Intent(this, BSGrid.class);//
		createTab(1, intent1);
		Intent intent2 = new Intent(this, Details.class);//
		intent2.putExtra("type", "study");
		intent2.putExtra("key", "习字");
		createTab(2, intent2);
		Intent intent3 = new Intent(this, More.class);// 更多
		createTab(3, intent3);
		tabWidget = tabHost.getTabWidget();
		tabHost.setCurrentTab(0);
		tabWidget.getChildAt(0).setBackgroundResource(R.drawable.tab_bg);
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				for (int i = 0; i < tabWidget.getChildCount(); i++) {
					View view = tabWidget.getChildAt(i);
					if (tabHost.getCurrentTab() == i) {
						view.setBackgroundResource(R.drawable.tab_bg);
					} else {
						view.setBackgroundResource(R.drawable.mmfooter_bg);

					}
				}
			}
		});
	}

	private void createTab(int index, Intent intent) {
		tabHost.addTab(tabHost.newTabSpec(index + "")
				.setIndicator(createTabView(index)).setContent(intent));
	}

	private View createTabView(int index) {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels / 4;
		int height = 60;
		TextView textView = new TextView(this);
		textView.setText(menu_items_title[index]);
		textView.setTextSize(20);
		textView.setTextColor(getResources().getColor(R.color.white));
		textView.setWidth(width);
		textView.setHeight(height);
		textView.setGravity(Gravity.CENTER);
		textViews.add(textView);
		temp_layout = new LinearLayout(this);
		temp_layout.setOrientation(LinearLayout.VERTICAL);
		temp_layout.addView(textView);
		temp_layouts.add(temp_layout);
		return temp_layout;
	}

}