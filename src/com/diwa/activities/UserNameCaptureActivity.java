package com.diwa.activities;

import com.diwa.db.DBAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class UserNameCaptureActivity extends Activity {
	private EditText userNameEdt;
	private Button nextBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suit_up);
		initUi();
		setListener();
	}

	private void setListener() {
		nextBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Onclick Next button
				if(!userNameEdt.getText().toString().equals(" "))
				{
					DBAdapter dba=new DBAdapter(UserNameCaptureActivity.this);
					dba.open();
					dba.saveUser(userNameEdt.getText().toString());
					dba.close();
					startActivity(new Intent(UserNameCaptureActivity.this,ListActivity.class));
				}else{
					userNameEdt.setError("Enter your name");
				}
			}
		});
		
	}

	private void initUi() {
		//Initialize UI
		userNameEdt=(EditText)findViewById(R.id.input_name_edt);
		nextBtn=(Button)findViewById(R.id.next_btn);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suit_up, menu);
		return true;
	}

}
