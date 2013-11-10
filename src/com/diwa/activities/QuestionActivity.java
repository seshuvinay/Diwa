package com.diwa.activities;

import com.diwa.db.DBAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		TextView txt=(TextView)findViewById(R.id.question_tv);
		Button nextBtn = (Button) findViewById(R.id.next_btn2);
		DBAdapter dba=new DBAdapter(QuestionActivity.this);
		dba.open();
		Cursor cr=dba.fetchUserName();
		cr.moveToFirst();
		String name=cr.getString(cr.getColumnIndex("name"));
		txt.setText(getResources().getString(R.string.question,name));
		dba.close();
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(QuestionActivity.this,
						ListActivity.class));
			}
		});

	}

}
