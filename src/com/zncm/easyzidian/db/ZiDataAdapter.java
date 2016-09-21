package com.zncm.easyzidian.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.zncm.easyzidian.pojo.Zi;

public class ZiDataAdapter {

	public Context context;
	private static SqlHelper sqlHelper = null;

	public ZiDataAdapter(Context context) {
		sqlHelper = new SqlHelper();
		this.context = context;
	}

	public ArrayList<Zi> QueryZis(String selection, String[] selectionArgs) {
		Cursor cursor = sqlHelper.queryData(context, sqlHelper.TB_ZI, null,
				selection, selectionArgs, null, null, null);
		ArrayList<Zi> zis = new ArrayList<Zi>();

		if (cursor.moveToFirst()) {
			do {
				Zi zi = new Zi();
				zi.setId(cursor.getString(0));
				zi.setZi(cursor.getString(1));
				zi.setBh(cursor.getString(2));
				zi.setPy(cursor.getString(3));
				zi.setPy2(cursor.getString(4));
				zi.setBs(cursor.getString(5));
				zi.setJs(cursor.getString(6));
				zis.add(zi);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return zis;

	}

	public Zi QueryZiById(String zi_id) {
		Cursor cursor = sqlHelper.queryData(context, sqlHelper.TB_ZI, null,
				"id = '" + zi_id + "'", null, null, null, null);
		Zi zi = null;
		if (cursor.moveToFirst()) {
			do {
				zi = new Zi();
				zi.setId(cursor.getString(0));
				zi.setZi(cursor.getString(1));
				zi.setBh(cursor.getString(2));
				zi.setPy(cursor.getString(3));
				zi.setPy2(cursor.getString(4));
				zi.setBs(cursor.getString(5));
				zi.setJs(cursor.getString(6));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return zi;

	}

	public Zi QueryZi(String zi_show) {
		Cursor cursor = sqlHelper.queryData(context, sqlHelper.TB_ZI, null,
				"zi = '" + zi_show + "'", null, null, null, null);
		Zi zi = null;
		if (cursor.moveToFirst()) {
			do {
				zi = new Zi();
				zi.setId(cursor.getString(0));
				zi.setZi(cursor.getString(1));
				zi.setBh(cursor.getString(2));
				zi.setPy(cursor.getString(3));
				zi.setPy2(cursor.getString(4));
				zi.setBs(cursor.getString(5));
				zi.setJs(cursor.getString(6));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return zi;

	}
	
	
}
