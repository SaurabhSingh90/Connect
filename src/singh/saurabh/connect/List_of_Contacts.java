package singh.saurabh.connect;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class List_of_Contacts extends Activity {

	DBAdapter myDB;
	 private List<RelativeLayout> layoutContainers = new ArrayList<RelativeLayout>();
	 private List<TextView> textContainers = new ArrayList<TextView>();
	 private List<ImageView> imageContainers = new ArrayList<ImageView>();
	 private List<Button> buttonContainers = new ArrayList<Button>();
	 public static long temp = 2;
	 Contacts myContactAdaptor;
	 
	@SuppressWarnings("resource")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_contacts);
		
		//open DB
		openDB();
		
		Cursor my_cursor2 = myDB.getRow(temp);
		while (my_cursor2.isLast()) {
			temp++;
			my_cursor2 = myDB.getRow(temp);
		}
		temp--;
		Cursor my_cursor1 = myDB.getRow(MainActivity.receive_Id);
		if (!my_cursor1.moveToFirst()) {
			Toast.makeText(getApplicationContext(), 
					"No contacts received so far", 
					Toast.LENGTH_SHORT).show();
		}
		else {
			 
			populateView();
		}
	}
	
	
    private void populateView() {
    	final LinearLayout lv = (LinearLayout) findViewById(R.id.container_contactList);
    	
    	while (temp > 1) {
    		
    		//cursor to fetch row
    		final Cursor my_cursor = myDB.getRow(temp);
    		String design_id = my_cursor.getString(DBAdapter.COL_TEMPLATE_ID);
    		
        	final RelativeLayout rv2 = new RelativeLayout(getApplicationContext());
        	TextView tV1 = new TextView(getApplicationContext());
        	TextView tV2 = new TextView(getApplicationContext());
        	TextView tV3 = new TextView(getApplicationContext());
            ImageButton ib = new ImageButton(getApplicationContext()); 
            Button fb = new Button(getApplicationContext());

            //set ID for VAR
            ib.setId(1); tV1.setId(2); tV2.setId(3); tV3.setId(4); fb.setId(5);
            
            //padding for TextViews and RelativeLayout
            rv2.setPadding(0, 0, 0, 10);
            
            //add to layout
            rv2.addView(ib);
            rv2.addView(tV1);
            rv2.addView(tV2);
            rv2.addView(tV3);
            rv2.addView(fb);
            lv.addView(rv2);
            
            //VAR for parameters
            LinearLayout.LayoutParams layoutParms = (LinearLayout.LayoutParams)rv2.getLayoutParams();
            RelativeLayout.LayoutParams imageParms = (RelativeLayout.LayoutParams)ib.getLayoutParams();
            RelativeLayout.LayoutParams buttonParms = (RelativeLayout.LayoutParams)fb.getLayoutParams();
            RelativeLayout.LayoutParams textParms1 = (RelativeLayout.LayoutParams)tV1.getLayoutParams();
            RelativeLayout.LayoutParams textParms2 = (RelativeLayout.LayoutParams)tV2.getLayoutParams();
            RelativeLayout.LayoutParams textParms3 = (RelativeLayout.LayoutParams)tV3.getLayoutParams();
            
            //parameters for relative layout
            layoutParms.height = 450;
            layoutParms.width = 680;
            
            //parameters for image view
            imageParms.height = 450;
            imageParms.width = 680;
            if (design_id.compareTo("1") == 0) {
            	ib.setBackgroundResource(R.drawable.b1);
            	
                //parameter for fb button
                buttonParms.width = 80;
                buttonParms.height = 80;
                buttonParms.setMargins(0, 70, 21, 0);
                buttonParms.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                buttonParms.addRule(RelativeLayout.ALIGN_TOP, ib.getId());
                fb.setBackgroundResource(R.drawable.fb);
                
                // Text Styling for Name
                textParms1.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                textParms1.addRule(RelativeLayout.CENTER_VERTICAL);
                textParms1.setMargins(28, 0, 0, 0);
                tV1.setTextSize(30);
                tV1.setTextColor(Color.WHITE);
                tV1.setText("Name");
                
                
                // Text Styling for Email      
                textParms2.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                textParms2.addRule(RelativeLayout.ALIGN_BOTTOM, ib.getId());
                textParms2.setMargins(0, 0, 20, 100);
                tV2.setTextSize(19);
                tV2.setTextColor(Color.WHITE);
                tV2.setText("Email");
                
                // Text Styling for Ph No      
                textParms3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                textParms3.addRule(RelativeLayout.ALIGN_BOTTOM, ib.getId());
                textParms3.setMargins(0, 0, 20, 10);
                tV3.setTextSize(19);
                tV3.setTextColor(Color.WHITE);
                tV3.setText("Phone Number");
                
            } else if (design_id.compareTo("2") == 0) {
            	ib.setBackgroundResource(R.drawable.b2);
            	
            	//parameter for fb button
                buttonParms.width = 80;
                buttonParms.height = 80;
                buttonParms.setMargins(0, 258, 0, 0);
                buttonParms.addRule(RelativeLayout.CENTER_HORIZONTAL);
                buttonParms.addRule(RelativeLayout.ALIGN_TOP, ib.getId());
                fb.setBackgroundResource(R.drawable.fb);
                
                // Text Styling for Name
                textParms1.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                textParms1.addRule(RelativeLayout.ALIGN_TOP, ib.getId());
                textParms1.setMargins(0, 54, 0, 0);
                tV1.setTextSize(25);
                tV1.setTextColor(Color.BLACK);
                tV1.setText("Name");
                
                
                // Text Styling for Email      
                textParms2.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.setMargins(18, 23, 10, 0);
                textParms2.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                textParms2.addRule(RelativeLayout.BELOW, tV1.getId());
                tV2.setTextSize(19);
                tV2.setTextColor(Color.BLACK);
                tV2.setText("Email");
                
                // Text Styling for Ph No      
                textParms3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.setMargins(0, 23, 18, 0);
                textParms3.addRule(RelativeLayout.ALIGN_BASELINE, tV2.getId());
                textParms3.addRule(RelativeLayout.ALIGN_BOTTOM, tV2.getId());
                textParms3.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                tV3.setTextSize(19);
                tV3.setTextColor(Color.BLACK);
                tV3.setText("Phone Number");
                
            } else if (design_id.compareTo("3") == 0) {
            	ib.setBackgroundResource(R.drawable.b3);
            	
            	//parameter for fb button
                buttonParms.width = 80;
                buttonParms.height = 80;
                buttonParms.setMargins(0, 0, 20, 87);
                buttonParms.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                buttonParms.addRule(RelativeLayout.ALIGN_BOTTOM, ib.getId());
                fb.setBackgroundResource(R.drawable.fb);
                
                // Text Styling for Name
                textParms1.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                textParms1.addRule(RelativeLayout.ALIGN_TOP, ib.getId());
                textParms1.setMargins(20, 84, 0, 0);
                tV1.setTextSize(30);
                tV1.setTextColor(Color.rgb(242, 197, 18));
                tV1.setText("Name");
                
                
                // Text Styling for Email      
                textParms2.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.setMargins(20, 16, 0, 0);
                textParms2.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                textParms2.addRule(RelativeLayout.BELOW, tV1.getId());
                tV2.setTextSize(19);
                tV2.setTextColor(Color.rgb(242, 197, 18));
                tV2.setText("Email");
                
                // Text Styling for Ph No      
                textParms3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.setMargins(0, 220, 20, 0);
                textParms3.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                textParms3.addRule(RelativeLayout.ALIGN_TOP, ib.getId());
                tV3.setTextSize(19);
                tV3.setTextColor(Color.rgb(242, 197, 18));
                tV3.setText("Phone Number");
                
            } else if (design_id.compareTo("4") == 0) {
            	ib.setBackgroundResource(R.drawable.b4);
            	
            	//parameter for fb button
                buttonParms.width = 80;
                buttonParms.height = 80;
                buttonParms.setMargins(20, 10, 0, 0);
                buttonParms.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                buttonParms.addRule(RelativeLayout.BELOW, tV1.getId());
                fb.setBackgroundResource(R.drawable.fb);
                
                // Text Styling for Name
                textParms1.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                textParms1.addRule(RelativeLayout.CENTER_VERTICAL);
                textParms1.setMargins(20, 0, 0, 0);
                tV1.setTextSize(30);
                tV1.setTextColor(Color.WHITE);
                tV1.setText("Name");
                
                
                // Text Styling for Email      
                textParms2.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.setMargins(0, 0, 10, 0);
                textParms2.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                textParms2.addRule(RelativeLayout.ABOVE, tV3.getId());
                tV2.setTextSize(19);
                tV2.setTextColor(Color.WHITE);
                tV2.setText("Email");
                
                // Text Styling for Ph No      
                textParms3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.setMargins(0, 0, 10, 67);
                textParms3.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                textParms3.addRule(RelativeLayout.ALIGN_BOTTOM, ib.getId());
                tV3.setTextSize(19);
                tV3.setTextColor(Color.WHITE);
                tV3.setText("Phone Number");
                
            } else if (design_id.compareTo("5") == 0) {
            	ib.setBackgroundResource(R.drawable.b5);
            	
            	//parameter for fb button
                buttonParms.width = 80;
                buttonParms.height = 80;
                buttonParms.setMargins(0, 20, 21, 0);
                buttonParms.addRule(RelativeLayout.ALIGN_TOP, ib.getId());
                buttonParms.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                fb.setBackgroundResource(R.drawable.fb);
                
                // Text Styling for Name
                textParms1.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                textParms1.addRule(RelativeLayout.CENTER_VERTICAL);
                textParms1.setMargins(28, 0, 0, 0);
                tV1.setTextSize(30);
                tV1.setTextColor(Color.YELLOW);
                tV1.setText("Name");
                
                
                // Text Styling for Email      
                textParms2.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                textParms2.addRule(RelativeLayout.ALIGN_BOTTOM, ib.getId());
                textParms2.setMargins(28, 0, 0, 30);
                tV2.setTextSize(19);
                tV2.setTextColor(Color.BLACK);
                tV2.setText("Email");
                
                // Text Styling for Ph No      
                textParms3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                textParms3.addRule(RelativeLayout.ALIGN_BOTTOM, ib.getId());
                textParms3.setMargins(0, 0, 21, 30);
                tV3.setTextSize(19);
                tV3.setTextColor(Color.BLACK);
                tV3.setText("Phone Number");
                
            } else if (design_id.compareTo("6") == 0) {
            	ib.setBackgroundResource(R.drawable.b6);
            	
            	//parameter for fb button
                buttonParms.width = 80;
                buttonParms.height = 80;
                buttonParms.setMargins(28, 60, 0, 0);
                buttonParms.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                buttonParms.addRule(RelativeLayout.ALIGN_TOP, ib.getId());
                fb.setBackgroundResource(R.drawable.fb);
                
                // Text Styling for Name
                textParms1.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms1.addRule(RelativeLayout.ALIGN_RIGHT, ib.getId());
                textParms1.setMargins(0, 60, 40, 0);
                //textParms1.addRule(RelativeLayout.CENTER_VERTICAL);
                tV1.setTextSize(30);
                tV1.setTextColor(Color.WHITE);
                tV1.setText("Name");
                
                
                // Text Styling for Email      
                textParms2.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms2.setMargins(20, 0, 0, 0);
                textParms2.addRule(RelativeLayout.ALIGN_LEFT, ib.getId());
                textParms2.addRule(RelativeLayout.ABOVE, tV3.getId());
                tV2.setTextSize(19);
                tV2.setTextColor(Color.WHITE);
                tV2.setText("Email");
                
                // Text Styling for Ph No      
                textParms3.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
                textParms3.setMargins(20, 0, 0, 44);
                textParms3.addRule(RelativeLayout.ALIGN_BOTTOM, ib.getId());
                tV3.setTextSize(19);
                tV3.setTextColor(Color.WHITE);
                tV3.setText("Phone Number");
                
            }
            
            
            // set the params   
            tV1.setLayoutParams(textParms1);
            tV2.setLayoutParams(textParms2);
            tV3.setLayoutParams(textParms3);
            fb.setLayoutParams(buttonParms);
            ib.setLayoutParams(imageParms);
            rv2.setLayoutParams(layoutParms);
            
            // add them to the lists (created above)
            imageContainers.add(ib);
            buttonContainers.add(fb);
            textContainers.add(tV1);
            textContainers.add(tV2);
            textContainers.add(tV3);
            layoutContainers.add(rv2);
            
            //read and print data from database

    		if (my_cursor.moveToFirst()) {
    			tV1.setText(my_cursor.getString(DBAdapter.COL_NAME));
    			tV2.setText(my_cursor.getString(DBAdapter.COL_EMAIL));
    			tV3.setText(my_cursor.getString(DBAdapter.COL_PHONE_NUMBER));
    		}
    		
    		//Fb button press to go to fb link
    		fb.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					launchFacebook(my_cursor);
				}
			});
    		
    		//contact saving through business card press
            
//    		ib.setOnLongClickListener(new View.OnLongClickListener() {
//				
//				@Override
//				public boolean onLongClick(View v) {
//					new AlertDialog.Builder(List_of_Contacts.this).setTitle("Save or Delete this contact?")
//		            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//		                @Override
//		                public void onClick(DialogInterface arg0, int arg1) {
//		                	String name = my_cursor.getString(DBAdapter.COL_NAME);
//		                	String phone = my_cursor.getString(DBAdapter.COL_PHONE_NUMBER);
//		                	String email = my_cursor.getString(DBAdapter.COL_EMAIL);
//		                	ContentValues values = new ContentValues();
//		                	
//		                	values.put(Data.DISPLAY_NAME, name);
//							Context c = getApplication();
//							Uri rawContactUri = c .getContentResolver().insert(RawContacts.CONTENT_URI, values);
//		                	long rawContactId = ContentUris.parseId(rawContactUri);
//		                	
//		                	values.clear();
//		                	values.put(Phone.NUMBER, phone);
//		                	values.put(Phone.TYPE, Phone.TYPE_WORK);
//		                	values.put(Phone.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
//		                	values.put(Data.RAW_CONTACT_ID, rawContactId);
//		                	c.getContentResolver().insert(Data.CONTENT_URI, values);
//
//		                	values.clear();
//		                	values.put(Email.ADDRESS, email);
//		                	values.put(Email.TYPE, Email.ADDRESS);
//		                	values.put(Email.MIMETYPE, Email.CONTENT_ITEM_TYPE);
//		                	values.put(Data.RAW_CONTACT_ID, rawContactId);
//		                	c.getContentResolver().insert(Data.CONTENT_URI, values);
//		                	
//		                	values.clear();
//		                	values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
//		                	values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name);
//		                	values.put(Data.RAW_CONTACT_ID, rawContactId);
//		                	c.getContentResolver().insert(Data.CONTENT_URI, values);
//		                	
//		                	Toast.makeText(getApplicationContext(), 
//		        					"Contact Saved", 
//		        					Toast.LENGTH_SHORT).show();
//
//		                }
//		            })
//		            .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
//		                @Override
//		                public void onClick(DialogInterface arg0, int arg1) {
//		                	new AlertDialog.Builder(List_of_Contacts.this).setTitle("Once deleted cannot be undone")
//		                	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//								
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//				                	myDB.deleteRow(temp+1);
//				                	lv.removeView(rv2);
////				                	recreate();
//								}
//							})
//							.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//								
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									// TODO Auto-generated method stub
//									
//								}
//							}).show();
//
//		                }
//		            }).show();
//					return true;
//				}
//			});
    		

    		ib.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new AlertDialog.Builder(List_of_Contacts.this).setTitle("Save this contact card into your phonebook?")
		            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		                @Override
		                public void onClick(DialogInterface arg0, int arg1) {
		                	String name = my_cursor.getString(DBAdapter.COL_NAME);
		                	String phone = my_cursor.getString(DBAdapter.COL_PHONE_NUMBER);
		                	String email = my_cursor.getString(DBAdapter.COL_EMAIL);
		                	ContentValues values = new ContentValues();
		                	
		                	values.put(Data.DISPLAY_NAME, name);
							Context c = getApplication();
							Uri rawContactUri = c .getContentResolver().insert(RawContacts.CONTENT_URI, values);
		                	long rawContactId = ContentUris.parseId(rawContactUri);
		                	
		                	values.clear();
		                	values.put(Phone.NUMBER, phone);
		                	values.put(Phone.TYPE, Phone.TYPE_WORK);
		                	values.put(Phone.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		                	values.put(Data.RAW_CONTACT_ID, rawContactId);
		                	c.getContentResolver().insert(Data.CONTENT_URI, values);

		                	values.clear();
		                	values.put(Email.ADDRESS, email);
		                	values.put(Email.TYPE, Email.ADDRESS);
		                	values.put(Email.MIMETYPE, Email.CONTENT_ITEM_TYPE);
		                	values.put(Data.RAW_CONTACT_ID, rawContactId);
		                	c.getContentResolver().insert(Data.CONTENT_URI, values);
		                	
		                	values.clear();
		                	values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		                	values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name);
		                	values.put(Data.RAW_CONTACT_ID, rawContactId);
		                	c.getContentResolver().insert(Data.CONTENT_URI, values);
		                	
		                	Toast.makeText(getApplicationContext(), 
		        					"Contact Saved", 
		        					Toast.LENGTH_SHORT).show();

		                }
		            })
		            .setNegativeButton("No", new DialogInterface.OnClickListener() {
		                @Override
		                public void onClick(DialogInterface arg0, int arg1) {
		                    
		                }
		            }).show();
					
				}
			});
    		
    		temp--;
    	}
	}
    

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}


	@Override
	public void recreate() {
		// TODO Auto-generated method stub
		super.recreate();
		Intent list_of_contacts = new Intent(this, List_of_Contacts.class);
		List_of_Contacts.this.finish();
    	List_of_Contacts.this.startActivity(list_of_contacts);
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
	
	@SuppressLint("UseValueOf")
	private void launchFacebook(Cursor cursor) {
		
		if (cursor.moveToFirst()) {
    		if (cursor.getString(DBAdapter.COL_FB_PROFILE).compareTo("") != 0) {
    			Intent facebookIntent = getOpenFacebookIntent(this, cursor);
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
