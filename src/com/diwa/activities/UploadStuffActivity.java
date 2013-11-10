package com.diwa.activities;

import java.io.File;
import java.util.Date;

import com.diwa.adapters.MyAdapter;
import com.diwa.db.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class UploadStuffActivity extends Activity {
	private GridView mGridView;
	private Button addButton;
	final int CAMERA_REQUEST = 101;
	final int GALLERY_REQUEST = 102;
	String imagePath;
	String categoryName;
	String catId;
	String[] ids;
	String[] imagePaths;
	Bitmap[] bitmaps;

	@Override
	public void onBackPressed() {
		android.os.Process.killProcess(android.os.Process.myPid());
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_stuff);
		try {
			getIntentValues();
			initUi();
			setUi();
			setUiListener();
		} catch (Exception e) {
		}
	}

	private void setUi() {

		try {
			DBAdapter dba = new DBAdapter(UploadStuffActivity.this);
			dba.open();
			Cursor cr = dba.getImagesByCatId(catId);
			cr.moveToFirst();
			bitmaps = new Bitmap[cr.getCount()+1];
			imagePaths = new String[cr.getCount()];
			ids = new String[cr.getCount()];
			for (int i = 0; i < cr.getCount(); i++) {
				imagePaths[i] = cr.getString(cr.getColumnIndex("image_path"));
				ids[i] = cr.getString(cr.getColumnIndex("id"));

				String filePath = imagePaths[i];
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				bitmaps[i] = BitmapFactory.decodeFile(filePath, options);

				cr.moveToNext();
			}
			if (cr.getCount()>0) {
				bitmaps[cr.getCount() ] = BitmapFactory.decodeResource(
						getResources(), R.drawable.zovilogo);
			}
			/*
			 * bitmaps[cr.getCount()]=immutableBitmap.copy(Bitmap.Config.ARGB_8888
			 * , true);
			 */
			dba.close();
			MyAdapter adapter = new MyAdapter(UploadStuffActivity.this, bitmaps);
			mGridView.setAdapter(adapter);
		} catch (Exception e) {

		}
	}

	private void getIntentValues() {
		categoryName = getIntent().getExtras().getString("cat_name");
		catId = getIntent().getExtras().getString("cat_id");

	}

	private void setUiListener() {
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						UploadStuffActivity.this);
				final CharSequence[] items = { "Camera", "Gallery" };
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent cameraIntent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
							File out = Environment
									.getExternalStorageDirectory();
							out = new File(out, "DiWa" + new Date().getTime()
									+ ".jpg");
							imagePath = out.getPath();
							cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(out));
							startActivityForResult(cameraIntent, CAMERA_REQUEST);

							break;
						case 1:
							startActivityForResult(
									new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
									GALLERY_REQUEST);

							break;
						}
					}
				});
				AlertDialog dialog = builder.create();
				dialog.show();

			}
		});
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DBAdapter dba = new DBAdapter(UploadStuffActivity.this);
				dba.open();
				dba.choose(ids[arg2], imagePaths[arg2], catId);
				dba.close();
				UploadStuffActivity.this.finish();
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (requestCode == CAMERA_REQUEST
					&& resultCode == Activity.RESULT_OK) {
				// imagePath already saved
			} else if (requestCode == GALLERY_REQUEST
					&& resultCode == Activity.RESULT_OK) {
				Uri imageUri = data.getData();
				imagePath = getRealPathFromURI(imageUri);
			} else if (resultCode == Activity.RESULT_CANCELED) {
				imagePath = "";
			}
			DBAdapter dba = new DBAdapter(UploadStuffActivity.this);
			dba.open();
			dba.insertPic(catId, imagePath);
			dba.close();

			startActivity(new Intent(UploadStuffActivity.this,
					UploadStuffActivity.class).putExtra("cat_id", catId)
					.putExtra("cat_name", categoryName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

	}

	private void initUi() {
		mGridView = (GridView) findViewById(R.id.image_grid);
		addButton = (Button) findViewById(R.id.add_btn);

	}

	public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = UploadStuffActivity.this.managedQuery(contentUri, proj,
				null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
