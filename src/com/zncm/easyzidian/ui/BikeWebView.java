package com.zncm.easyzidian.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BikeWebView extends Activity {
	WebView mWebView = null;
	String word = "";
	TextView t_tv01;
	Button t_btn01;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		loadPage();
		super.onCreate(savedInstanceState);
	}

	private void loadPage() {
		word = getIntent().getExtras().getString("word");
		setContentView(R.layout.zi_webview);
		LinearLayout ll01 = (LinearLayout) findViewById(R.id.zi_webview_lay);
		t_tv01 = (TextView) ll01.findViewById(R.id.title_tv01);
		t_btn01 = (Button) ll01.findViewById(R.id.title_btn01);
		t_btn01.setVisibility(View.VISIBLE);
		t_btn01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		t_tv01.setText("百度百科-" + word);
		mWebView = ((WebView) findViewById(R.id.zi_webview_wv01));
		mWebView.loadUrl("http://wapbaike.baidu.com/search?word=" + word);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(false);
		mWebView.getSettings().setSupportZoom(false);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.getSettings().setJavaScriptEnabled(true);
				view.loadUrl(url);
				view.requestFocus();
				return true;
			}
		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
