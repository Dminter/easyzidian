package com.zncm.easyzidian.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlHelper {

	public static String DB_NAME = "data/data/com.zncm.easyzidian.ui/database/zd.db";
	public static String TB_ZI = "zi_dian_db";

	public Cursor queryData(Context context, String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_NAME, null);
		Cursor cursor = null;
		try {

			cursor = db.query(table, columns, selection, selectionArgs,
					groupBy, having, orderBy);
			cursor.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close();
		return cursor;

	}

}
