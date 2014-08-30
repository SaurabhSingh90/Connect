package singh.saurabh.connect;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Info extends Activity {
	
	DBAdapter myDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container_contactList, new PlaceholderFragment()).commit();
		}
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
//				Info.this.startActivity(start_my_profile_activity);
//			}
//        	else {
//        		Intent contacts_list = new Intent(this, Contacts_List.class);
//        		Info.this.startActivity(contacts_list);
//        	}
//            return true;
//        }
//        if (id == R.id.profile) {
//        	Intent profile_page = new Intent(this, Profile_Page.class);
//        	Info.this.startActivity(profile_page);
//            return true;
//        }
//        if (id == R.id.help) {
//        	Intent about = new Intent(this, Info.class);
//        	Info.this.startActivity(about);
//        	return true;
//        }
//        if (id == R.id.list_of_contacts) {
//        	Intent list_of_contacts = new Intent(this, List_of_Contacts.class);
//        	Info.this.startActivity(list_of_contacts);
//        	return true;
//        }
//		return super.onOptionsItemSelected(item);
//	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_info, container,
					false);
			return rootView;
		}
	}

}
