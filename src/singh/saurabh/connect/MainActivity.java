package singh.saurabh.connect;

import java.nio.charset.Charset;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.database.Cursor;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.provider.Settings;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("InlinedApi")
public class MainActivity extends Activity {
	
	DBAdapter myDB;
	NdefMessage mNdefMessage;
	NfcAdapter mNFCAdapter;
	PendingIntent mNfcPendingIntent;
	IntentFilter[] mNdefExchangeFilters;
	public static String p_name, p_email, p_number, fb_profile, template_id;
	public static long 	receive_Id = 2, my_Id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //opening DB
      		openDB();
      		
      		mNFCAdapter = NfcAdapter.getDefaultAdapter(this);
	      	mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	      	// Intent filters for exchanging over p2p.
	      	IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
	      		
	      	try {
					ndefDetected.addDataType("text/plain");
			} catch (MalformedMimeTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      	
	      	mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
    }
    
    
    @Override
    protected void onResume() {
        super.onResume();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            NdefMessage[] msgs = getNdefMessages(getIntent());
            byte[] payload1 = msgs[0].getRecords()[0].getPayload(), 
	        		payload2 = msgs[0].getRecords()[1].getPayload(), 
	        		payload3 = msgs[0].getRecords()[2].getPayload(),
            		payload4 = msgs[0].getRecords()[3].getPayload(),
            		payload5 = msgs[0].getRecords()[4].getPayload();
            setCardValue(new String(payload1), new String(payload2), new String(payload3), new String(payload4), new String(payload5));
            setIntent(new Intent()); // Consume this intent.
      	}
        mNFCAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
    }

    @Override
    public void onBackPressed() {
    	super.finish();
    	super.onBackPressed();
    }
    
    public void onPaus() {
    	super.onPause();
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
	
	@SuppressLint({ "NewApi", "SdCardPath" })
	public void onClickedExchange(View view) {
		
		NfcAdapter nfc = NfcAdapter.getDefaultAdapter(getApplicationContext());
		
		if (!nfc.isEnabled() && !nfc.isNdefPushEnabled()) {
			Toast.makeText(getApplicationContext(), 
					"Please activate NFC and Android Beam", 
					Toast.LENGTH_SHORT).show();
			startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
		}
		else if (nfc.isEnabled() && !nfc.isNdefPushEnabled()) {
			Toast.makeText(getApplicationContext(), 
					"Please activate Android Beam", 
					Toast.LENGTH_SHORT).show();
			startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
		}
		else if (nfc.isEnabled() && nfc.isNdefPushEnabled()) {
			Cursor my_cursor2 = myDB.getRow(my_Id);
			if (!my_cursor2.moveToFirst()) {
				Toast.makeText(getApplicationContext(), 
						"Enter your info first", 
						Toast.LENGTH_SHORT).show();
				Intent start_my_profile_activity = new Intent(this, Profile_Page.class);
				MainActivity.this.startActivity(start_my_profile_activity);
			}
			else {
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.toast_view, 
						(ViewGroup) findViewById(R.id.toast_layout_root));
				Toast toast = new Toast(getApplicationContext());
				
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.setView(layout);
				toast.show();
				
				enableNdefExchangeMode();
			}
	    }
	}
	
	public static NdefRecord newTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length]; 
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }
	
	private NdefMessage getNoteAsNdef() {
		Cursor my_cursor = myDB.getRow(my_Id);
		p_name = my_cursor.getString(DBAdapter.COL_NAME);
		p_email = my_cursor.getString(DBAdapter.COL_EMAIL);
		p_number = my_cursor.getString(DBAdapter.COL_PHONE_NUMBER);
		fb_profile = my_cursor.getString(DBAdapter.COL_FB_PROFILE);
		template_id = my_cursor.getString(DBAdapter.COL_TEMPLATE_ID);
		
        byte[] textBytes1 = p_name.getBytes();
        byte[] textBytes2 = p_email.getBytes();
        byte[] textBytes3 = p_number.getBytes();
        byte[] textBytes4 = fb_profile.getBytes();
        byte[] textBytes5 = template_id.getBytes();
        NdefRecord textRecord1 = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),new byte[] {}, textBytes1);
        NdefRecord textRecord2 = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),new byte[] {}, textBytes2);
        NdefRecord textRecord3 = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),new byte[] {}, textBytes3);
        NdefRecord textRecord4 = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),new byte[] {}, textBytes4);
        NdefRecord textRecord5 = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),new byte[] {}, textBytes5);

        return new NdefMessage(new NdefRecord[] {
            textRecord1, textRecord2, textRecord3, textRecord4, textRecord5
        });
    }
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void enableNdefExchangeMode() {
		
		mNFCAdapter.enableForegroundNdefPush(MainActivity.this, getNoteAsNdef());
		mNFCAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// NDEF exchange mode
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
	        NdefMessage[] msgs = getNdefMessages(intent);
	        promptForContent(msgs[0]);
	    }
	}
	
	public void promptForContent(NdefMessage msg) {
		String str1 = null, str2 = null, str3 = null, str4 = null, str5 = null;
		str1 = new String(msg.getRecords()[0].getPayload());
		str2 = new String(msg.getRecords()[1].getPayload());
		str3 = new String(msg.getRecords()[2].getPayload());
		str4 = new String(msg.getRecords()[3].getPayload());
		str5 = new String(msg.getRecords()[4].getPayload());
				
		setCardValue(str1, str2, str3, str4, str5);
		
    }
	
	public void setCardValue(String s1, String s2, String s3, String s4, String s5) {
		//receive id updated
		receive_Id = myDB.insertRow(s1 ,s2 ,s3, s4, s5);
        Intent start_contact_list_activity = new Intent(this, List_of_Contacts.class);
		MainActivity.this.startActivity(start_contact_list_activity);
	}
	
	public NdefMessage[] getNdefMessages(Intent intent) {
	    // Parse the intent
	    NdefMessage[] msgs = null;
	    String action = intent.getAction();
	    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
	        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	        if (rawMsgs != null) {
	            msgs = new NdefMessage[rawMsgs.length];
	            for (int i = 0; i < rawMsgs.length; i++) {
	                msgs[i] = (NdefMessage) rawMsgs[i];
	            }
	        } else {
	            // Unknown tag type
	            byte[] empty = new byte[] {};
	            NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
	            NdefMessage msg = new NdefMessage(new NdefRecord[] {record});
	            msgs = new NdefMessage[] {msg};
	        }
	    } else {
	        finish();
	    }
	    return msgs;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            return true;
        }
        if (id == R.id.contacts_list) {
        	Cursor my_cursor2 = myDB.getRow(my_Id);
        	if (!my_cursor2.moveToFirst()) {
				Toast.makeText(getApplicationContext(), 
						"No profile created yet.\nCreate your profile first", 
						Toast.LENGTH_SHORT).show();
				Intent start_my_profile_activity = new Intent(this, Profile_Page.class);
				MainActivity.this.startActivity(start_my_profile_activity);
			}
        	else {
        		Intent contacts_list = new Intent(this, Contacts_List.class);
        		MainActivity.this.startActivity(contacts_list);
        	}
            return true;
        }
        if (id == R.id.profile) {
        	Intent profile_page = new Intent(this, Profile_Page.class);
        	MainActivity.this.startActivity(profile_page);
            return true;
        }
        if (id == R.id.help) {
        	Intent about = new Intent(this, Info.class);
        	MainActivity.this.startActivity(about);
        	return true;
        }
        if (id == R.id.list_of_contacts) {
        	Intent list_of_contacts = new Intent(this, List_of_Contacts.class);
        	MainActivity.this.startActivity(list_of_contacts);
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
