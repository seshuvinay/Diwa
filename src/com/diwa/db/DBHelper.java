package com.diwa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "dressup_db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_USERNAME = "create table username(name text);";
	private static final String TABLE_CATEGORIES = "create table categories (id integer primary key autoincrement, category text);";
	private static final String TABLE_DRESSES = "create table dresses (id integer primary key autoincrement, cat_id integer,image_path text);";
	private static final String INSERT_CASUAL = "insert into categories(\'category\') values(\"Casual Wear\")";
	private static final String INSERT_FORMAL = "insert into categories(\'category\') values(\"Formal Wear\")";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(DBHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		onCreate(database);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_USERNAME);
		db.execSQL(TABLE_CATEGORIES);
		db.execSQL(TABLE_DRESSES);
		db.execSQL(INSERT_CASUAL);
		db.execSQL(INSERT_FORMAL);

	}
}
