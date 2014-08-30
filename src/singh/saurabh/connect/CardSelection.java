                                                                                                                                                                                                                   package singh.saurabh.connect;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CardSelection extends Activity {
	
	TextView n1, n2, n3, n4, n5, n6,
			 e1, e2, e3, e4, e5, e6, 
			 p1, p2, p3, p4, p5, p6;
	
	ImageView iv1, iv2, iv3, iv4, iv5, iv6;
	DBAdapter myDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_selection);
		
		//opening DB
		openDB();
		Cursor my_cursor2 = myDB.getRow(MainActivity.my_Id);
		final String content_n = my_cursor2.getString(DBAdapter.COL_NAME);
		final String content_e = my_cursor2.getString(DBAdapter.COL_EMAIL);
		final String content_p = my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER);
		final String content_fb_profile = my_cursor2.getString(DBAdapter.COL_FB_PROFILE);
		
		//name textView
		n1 = (TextView) findViewById(R.id.NameTextView1);
		n1.setText(my_cursor2.getString(DBAdapter.COL_NAME));
		n2 = (TextView) findViewById(R.id.NameTextView2);
		n2.setText(my_cursor2.getString(DBAdapter.COL_NAME));
		n3 = (TextView) findViewById(R.id.NameTextView3);
		n3.setText(my_cursor2.getString(DBAdapter.COL_NAME));
		n4 = (TextView) findViewById(R.id.NameTextView4);
		n4.setText(my_cursor2.getString(DBAdapter.COL_NAME));
		n5 = (TextView) findViewById(R.id.NameTextView5);
		n5.setText(my_cursor2.getString(DBAdapter.COL_NAME));
		n6 = (TextView) findViewById(R.id.NameTextView6);
		n6.setText(my_cursor2.getString(DBAdapter.COL_NAME));
		
		//email textView
		e1 = (TextView) findViewById(R.id.EmailTextView1);
		e1.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
		e2 = (TextView) findViewById(R.id.EmailTextView2);
		e2.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
		e3 = (TextView) findViewById(R.id.EmailTextView3);
		e3.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
		e4 = (TextView) findViewById(R.id.EmailTextView4);
		e4.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
		e5 = (TextView) findViewById(R.id.EmailTextView5);
		e5.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
		e6 = (TextView) findViewById(R.id.EmailTextView6);
		e6.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
		
		//phone number textView
		p1 = (TextView) findViewById(R.id.PhoneNumberTextView1);
		p1.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
		p2 = (TextView) findViewById(R.id.PhoneNumberTextView2);
		p2.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
		p3 = (TextView) findViewById(R.id.PhoneNumberTextView3);
		p3.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
		p4 = (TextView) findViewById(R.id.PhoneNumberTextView4);
		p4.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
		p5 = (TextView) findViewById(R.id.PhoneNumberTextView5);
		p5.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
		p6 = (TextView) findViewById(R.id.PhoneNumberTextView6);
		p6.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
		
		//imageView on click
		iv1 = (ImageView) findViewById(R.id.template1);
		iv2 = (ImageView) findViewById(R.id.template2);
		iv3 = (ImageView) findViewById(R.id.template3);
		iv4 = (ImageView) findViewById(R.id.template4);
		iv5 = (ImageView) findViewById(R.id.template5);
		iv6 = (ImageView) findViewById(R.id.template6);
		
		iv1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = "1";
				myDB.updateRow(MainActivity.my_Id, content_n, content_e, content_p, content_fb_profile, id);
				Toast.makeText(getApplicationContext(), 
						"Card Created", 
						Toast.LENGTH_LONG).show();
				Intent start_contact_activity = new Intent(CardSelection.this, Contacts_List.class);
				CardSelection.this.finish();
				CardSelection.this.startActivity(start_contact_activity);
			}
		});
		
		iv2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = "2";
				myDB.updateRow(MainActivity.my_Id, content_n, content_e, content_p, content_fb_profile, id);
				Toast.makeText(getApplicationContext(), 
						"Card Created", 
						Toast.LENGTH_LONG).show();
				Intent start_contact_activity = new Intent(CardSelection.this, Contacts_List.class);
				CardSelection.this.finish();
				CardSelection.this.startActivity(start_contact_activity);
			}
		});
		
		iv3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = "3";
				myDB.updateRow(MainActivity.my_Id, content_n, content_e, content_p, content_fb_profile, id);
				Toast.makeText(getApplicationContext(), 
						"Card Created", 
						Toast.LENGTH_LONG).show();
				Intent start_contact_activity = new Intent(CardSelection.this, Contacts_List.class);
				CardSelection.this.finish();
				CardSelection.this.startActivity(start_contact_activity);
			}
		});

		iv4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = "4";
				myDB.updateRow(MainActivity.my_Id, content_n, content_e, content_p, content_fb_profile, id);
				Toast.makeText(getApplicationContext(), 
						"Card Created", 
						Toast.LENGTH_LONG).show();
				Intent start_contact_activity = new Intent(CardSelection.this, Contacts_List.class);
				CardSelection.this.finish();
				CardSelection.this.startActivity(start_contact_activity);
			}
		});

		iv5.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = "5";
				myDB.updateRow(MainActivity.my_Id, content_n, content_e, content_p, content_fb_profile, id);
				Toast.makeText(getApplicationContext(), 
						"Card Created", 
						Toast.LENGTH_LONG).show();
				Intent start_contact_activity = new Intent(CardSelection.this, Contacts_List.class);
				CardSelection.this.finish();
				CardSelection.this.startActivity(start_contact_activity);
			}
		});

		iv6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = "6";
				myDB.updateRow(MainActivity.my_Id, content_n, content_e, content_p, content_fb_profile, id);
				Toast.makeText(getApplicationContext(), 
						"Card Created", 
						Toast.LENGTH_LONG).show();
				Intent start_contact_activity = new Intent(CardSelection.this, Contacts_List.class);
				CardSelection.this.finish();
				CardSelection.this.startActivity(start_contact_activity);
			}
		});

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDB();
	}
    
    private void openDB() {
		myDB = new DBAdapter(this);
		myDB.open();
	}
	
	private void closeDB() {
		myDB.close();
	}   
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@SuppressLint("InlinedApi")
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//        	startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
//            return true;
//        }
//        if (id == R.id.contacts_list) {
//        	Cursor my_cursor2 = myDB.getRow(MainActivity.my_Id);
//        	if (!my_cursor2.moveToFirst()) {
//				Toast.makeText(getApplicationContext(), 
//						"No profile created yet.\nCreate your profile first", 
//						Toast.LENGTH_SHORT).show();
//				Intent start_my_profile_activity = new Intent(this, Profile_Page.class);
//				CardSelection.this.startActivity(start_my_profile_activity);
//			}
//        	else {
//        		Intent contacts_list = new Intent(this, Contacts_List.class);
//        		CardSelection.this.startActivity(contacts_list);
//        	}
//            return true;
//        }
//        if (id == R.id.profile) {
//        	Intent profile_page = new Intent(this, Profile_Page.class);
//        	CardSelection.this.startActivity(profile_page);
//            return true;
//        }
//        if (id == R.id.help) {
//        	Intent about = new Intent(this, Info.class);
//        	CardSelection.this.startActivity(about);
//        	return true;
//        }
//        if (id == R.id.list_of_contacts) {
//        	Intent list_of_contacts = new Intent(this, List_of_Contacts.class);
//        	CardSelection.this.startActivity(list_of_contacts);
//        	return true;
//        }
//		return super.onOptionsItemSelected(item);
//	}

}
