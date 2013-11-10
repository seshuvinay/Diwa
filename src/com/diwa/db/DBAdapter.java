package com.diwa.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	Context context;
	private SQLiteDatabase database;
	private DBHelper dbHelper;

	public DBAdapter(Context context) {
		this.context = context;
	}

	public DBAdapter open() throws SQLException {
		dbHelper = new DBHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public void saveUser(String userName) {
		ContentValues values = new ContentValues();
		values.put("name", userName);
		database.insert("username", null, values);
	}

	public Cursor fetchUserName() {
		// TODO Auto-generated method stub
		return database.query("username", null, null, null, null, null, null);
	}

	public Cursor getCategories() {

		return database.query("categories", null, null, null, null, null, null);
	}

	public void addCategory(String categoryName) {
		ContentValues values = new ContentValues();
		values.put("category", categoryName);
		database.insert("categories", null, values);

	}

	public Cursor getImagesByCatId(String id) {
		return database.query("dresses", null, null, null, null,
				null, null);//"cat_id = " + id
	}

	public void insertPic(String categoryId, String imagePath) {
		ContentValues values = new ContentValues();
		values.put("cat_id", categoryId);
		values.put("image_path", imagePath);
		database.insert("dresses", null, values);

	}

	public void choose(String id,String imagePath,String catId) {
		database.delete("dresses", "id='"+id+"'", null);
		insertPic(catId,imagePath);
		
	}
}
