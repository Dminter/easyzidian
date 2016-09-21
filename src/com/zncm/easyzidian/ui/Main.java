package com.zncm.easyzidian.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zncm.easyzidian.db.SqlHelper;

public class Main extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		File file = new File("data/data/com.zncm.easyzidian.ui/database");
		if (!file.exists()) {
			file.mkdir();
		}
		if (!(new File(SqlHelper.DB_NAME).exists())) {
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(SqlHelper.DB_NAME);
				byte[] buf = new byte[1024];
				int count = 0;
				InputStream inputStream = getResources().openRawResource(
						R.raw.zd);
				while ((count = inputStream.read(buf)) > 0) {
					outputStream.write(buf, 0, count);
				}
				outputStream.close();
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		Intent intent = new Intent(Main.this, Menu.class);
		startActivity(intent);
		finish();
	}

}