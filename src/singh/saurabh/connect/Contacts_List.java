package singh.saurabh.connect;

import com.facebook.AppEventsLogger;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class Contacts_List extends Activity {

	DBAdapter myDB;
	public String APP_ID = "1428657584078910";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//open DB
		openDB();
        
		TextView n1, n2, n3, n4, n5, n6,
		 e1, e2, e3, e4, e5, e6, 
		 p1, p2, p3, p4, p5, p6;
		
		//read and print data from database
		final Cursor my_cursor2 = myDB.getRow(MainActivity.my_Id);
		
		if (my_cursor2.moveToFirst()) {
			
			String design_id = my_cursor2.getString(DBAdapter.COL_TEMPLATE_ID);
			if (design_id.compareTo("1") == 0) {
				
				setContentView(R.layout.activity_contacts_list_1);
				
				//name textView
				n1 = (TextView) findViewById(R.id.NameTextView1);
				n1.setText(my_cursor2.getString(DBAdapter.COL_NAME));
				
				//email textView
				e1 = (TextView) findViewById(R.id.EmailTextView1);
				e1.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
				
				//phone number textView
				p1 = (TextView) findViewById(R.id.PhoneNumberTextView1);
				p1.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
				
			} else if (design_id.compareTo("2") == 0) {
				
				setContentView(R.layout.activity_contacts_list_2);
				
				//name textView
				n2 = (TextView) findViewById(R.id.NameTextView2);
				n2.setText(my_cursor2.getString(DBAdapter.COL_NAME));
				
				//email textView
				e2 = (TextView) findViewById(R.id.EmailTextView2);
				e2.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
				
				//phone number textView
				p2 = (TextView) findViewById(R.id.PhoneNumberTextView2);
				p2.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
				
			} else if (design_id.compareTo("3") == 0) {

				setContentView(R.layout.activity_contacts_list_3);
				
				//name textView
				n3 = (TextView) findViewById(R.id.NameTextView3);
				n3.setText(my_cursor2.getString(DBAdapter.COL_NAME));
				
				//email textView
				e3 = (TextView) findViewById(R.id.EmailTextView3);
				e3.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
				
				//phone number textView
				p3 = (TextView) findViewById(R.id.PhoneNumberTextView3);
				p3.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
				
			} else if (design_id.compareTo("4") == 0) {
				
				setContentView(R.layout.activity_contacts_list_4);
			
				//name textView
				n4 = (TextView) findViewById(R.id.NameTextView4);
				n4.setText(my_cursor2.getString(DBAdapter.COL_NAME));
				
				//email textView
				e4 = (TextView) findViewById(R.id.EmailTextView4);
				e4.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
				
				//phone number textView
				p4 = (TextView) findViewById(R.id.PhoneNumberTextView4);
				p4.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
			
			} else if (design_id.compareTo("5") == 0) {
				
				setContentView(R.layout.activity_contacts_list_5);
				
				//name textView
				n5 = (TextView) findViewById(R.id.NameTextView5);
				n5.setText(my_cursor2.getString(DBAdapter.COL_NAME));
				
				//email textView
				e5 = (TextView) findViewById(R.id.EmailTextView5);
				e5.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
				
				//phone number textView
				p5 = (TextView) findViewById(R.id.PhoneNumberTextView5);
				p5.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
				
			} else if (design_id.compareTo("6") == 0) {
				
				setContentView(R.layout.activity_contacts_list_6);
				
				//name textView
				n6 = (TextView) findViewById(R.id.NameTextView6);
				n6.setText(my_cursor2.getString(DBAdapter.COL_NAME));
				
				//email textView
				e6 = (TextView) findViewById(R.id.EmailTextView6);
				e6.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
				
				//phone number textView
				p6 = (TextView) findViewById(R.id.PhoneNumberTextView6);
				p6.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
				
			}

		}

	}
	
	@Override
    protected void onResume() {
        super.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this, APP_ID);
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
    
    
    public final void onFaceBookClick(View view) {
    	Cursor my_cursor = myDB.getRow(MainActivity.my_Id);
    	if (my_cursor.moveToFirst()) {
    		if (my_cursor.getString(DBAdapter.COL_FB_PROFILE).compareTo("") != 0) {
    			Intent facebookIntent = getOpenFacebookIntent(this, my_cursor);
    			startActivity(facebookIntent);
        	}
        	else 
        		Toast.makeText(getApplicationContext(), 
    					"Facebook profile not attached", 
    					Toast.LENGTH_SHORT).show();
    	}
    	else
    		Toast.makeText(getApplicationContext(), 
					"Facebook profile not attached", 
					Toast.LENGTH_SHORT).show();
    	
        
    }
    
	public static Intent getOpenFacebookIntent(Context context, Cursor my_cursor) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            Toast.makeText(context, 
            		"Opening facebook profile...", 
					Toast.LENGTH_SHORT).show();
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/app_scoped_user_id/"+my_cursor.getString(DBAdapter.COL_FB_PROFILE))); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/app_scoped_user_id/"+my_cursor.getString(DBAdapter.COL_FB_PROFILE))); //catches and opens a url to the desired page
        }
    }
    

}
