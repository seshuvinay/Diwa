package com.diwa.activities;

import com.diwa.db.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ListActivity extends Activity {
	ListView mListView;
	Button mAddButton;
	String[] catNames;
	String categoryName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		initUi();
		setUi();
		setListener();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.about_menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.info:
	            startActivity(new Intent(ListActivity.this,AboutActivity.class));
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	private void setListener() {
		mAddButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LayoutInflater li = LayoutInflater.from(ListActivity.this);
				View promptsView = li.inflate(R.layout.prompts, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ListActivity.this);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);

				// set dialog message
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										categoryName = userInput.getText()
												.toString();
										DBAdapter dba = new DBAdapter(
												ListActivity.this);
										dba.open();
										dba.addCategory(categoryName);
										dba.close();
										startActivity(new Intent(
												ListActivity.this,
												ListActivity.class));

									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				startActivity(new Intent(ListActivity.this,
						UploadStuffActivity.class).putExtra("cat_id", Integer.toString(arg2 + 1))
						.putExtra("cat_name", categoryName));

			}
		});
	}

	private void setUi() {
		DBAdapter dba = new DBAdapter(ListActivity.this);
		dba.open();
		Cursor cr = dba.getCategories();
		catNames = new String[cr.getCount()];
		cr.moveToFirst();
		for (int i = 0; !cr.isAfterLast(); i++) {
			catNames[i] = cr.getString(cr.getColumnIndex("category"));
			cr.moveToNext();
		}
		dba.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, catNames);
		mListView.setAdapter(adapter);
	}

	private void initUi() {
		mListView = (ListView) findViewById(R.id.list_categories);
		mAddButton = (Button) findViewById(R.id.add_cat_btn);
	}

}
