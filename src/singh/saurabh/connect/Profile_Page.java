package singh.saurabh.connect;

import com.facebook.*;
import com.facebook.model.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Steps to using the DB:
 * 1. Instantiate the DB Adapter
 * 2. Open the DB
 * 3. Use get, insert, delete, .. to change data.
 * 4. Close the DB
 */

public class Profile_Page extends Activity {
	
	DBAdapter myDB;
	public String user_fb_link;
	
	//VAR for EditText
	public EditText name;
	public EditText phone_number;
	public EditText email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_page);
		
		//opening DB
			openDB();
			//testing to change name of save button to next button
			final Button save_to_next_button = (Button) findViewById(R.id.save);
			save_to_next_button.setText("Next");
			
			name = (EditText) findViewById(R.id.person_name);
			phone_number = (EditText) findViewById(R.id.person_number);
			email = (EditText) findViewById(R.id.person_email);
			
			//To Display the last entered TextView as text
			Cursor my_cursor2 = myDB.getRow(MainActivity.my_Id);
			if (my_cursor2.moveToFirst()) {
				name.setText(my_cursor2.getString(DBAdapter.COL_NAME));
				phone_number.setText(my_cursor2.getString(DBAdapter.COL_PHONE_NUMBER));
				email.setText(my_cursor2.getString(DBAdapter.COL_EMAIL));
			}

			
		Button fb_button = (Button) findViewById(R.id.fb);
		fb_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Profile_Page.this).setTitle("Link your Facebook Profile with your Business Card?")
	            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface arg0, int arg1) {

	                	//Facebook login here
	                	Session.openActiveSession(Profile_Page.this, true, new Session.StatusCallback() {
	                		// callback when session changes state
	                	    @Override
	                	    public void call(Session session, SessionState state, Exception exception) {
	                	    	if (session.isOpened()) {
	                	    		// make request to the /me API
	                	    		Request.newMeRequest(session, new Request.GraphUserCallback() {
	                	    			// callback after Graph API response with user object
	                	    			@Override
	                	    			public void onCompleted(GraphUser user, Response response) {
	                	    				//Retrieve user's profile id and store it into database
	                	    				if (user != null) {
	                	    					user_fb_link = user.getId();
	                	    					Toast.makeText(getBaseContext(),"Greetings "+user.getFirstName()+"! \nYour profile is attached", Toast.LENGTH_SHORT).show();
	                	    					}
	                	    				else
	                	    					Toast.makeText(getApplicationContext(),"No profile attached", Toast.LENGTH_SHORT).show();
	                	    				}
	                	    			}).executeAsync();
	                	    		}
	                	    	}
	                	    });
	                	}
	                })
	                .setNegativeButton("No", new DialogInterface.OnClickListener() {
	                	@Override
	                	public void onClick(DialogInterface arg0, int arg1) {
	                		
	                	}
	                	}).show();
				}
			});
		}
	
	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
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
    
    
	public void onSaveClicked(View view) {
		
		if (name.length() == 0 || phone_number.length() == 0 || email.length() == 0)
			Toast.makeText(getApplicationContext(), "Please enter all the fields!!", Toast.LENGTH_SHORT).show();
		else {
			String content_n = name.getText().toString();
			String content_e = email.getText().toString();
			String content_p = phone_number.getText().toString();
			
			//write data from edit text into database
			Cursor my_cursor2 = myDB.getRow(MainActivity.my_Id);
			if (my_cursor2.moveToFirst()) {
				myDB.updateRow(MainActivity.my_Id, content_n, content_e, content_p, user_fb_link, my_cursor2.getString(DBAdapter.COL_TEMPLATE_ID));
				new AlertDialog.Builder(Profile_Page.this).setTitle("Update Card Design?")
	            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface arg0, int arg1) {
	                	Intent start_design_activity = new Intent(Profile_Page.this, CardSelection.class);
	                	Profile_Page.this.finish();
	    				Profile_Page.this.startActivity(start_design_activity);
	                }
	            })
	            .setNegativeButton("No", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface arg0, int arg1) {
	                	Intent start_contact_activity = new Intent(Profile_Page.this, Contacts_List.class);
	                	Profile_Page.this.finish();
	    				Profile_Page.this.startActivity(start_contact_activity);
	                }
	            }).show();
			}
			else {
				MainActivity.my_Id = myDB.insertRow(content_n, content_e, content_p, user_fb_link, "1");
				Toast.makeText(getApplicationContext(),"Select Card Design", Toast.LENGTH_SHORT).show();
				Intent start_design_activity = new Intent(this, CardSelection.class);
				Profile_Page.this.finish();
				Profile_Page.this.startActivity(start_design_activity);
			}
		}
	}
	
	public void onClearClicked(View view) {
		name.setText("");
		email.setText("");
		phone_number.setText("");
		
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
//				Profile_Page.this.startActivity(start_my_profile_activity);
//			}
//        	else {
//        		Intent contacts_list = new Intent(this, Contacts_List.class);
//        		Profile_Page.this.startActivity(contacts_list);
//        	}
//        	return true;
//        }
//        if (id == R.id.profile) {
//        	Intent profile_page = new Intent(this, Profile_Page.class);
//        	Profile_Page.this.startActivity(profile_page);
//            return true;
//        }
//        if (id == R.id.help) {
//        	Intent about = new Intent(this, Info.class);
//        	Profile_Page.this.startActivity(about);
//        	return true;
//        }
//        if (id == R.id.list_of_contacts) {
//        	Intent list_of_contacts = new Intent(this, List_of_Contacts.class);
//        	Profile_Page.this.startActivity(list_of_contacts);
//        	return true;
//        }
//		return super.onOptionsItemSelected(item);
//	}

}
