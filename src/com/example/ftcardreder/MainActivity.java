package com.example.ftcardreder;




import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private static final String LOG_TAG = "FT Card Reader";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = this.getIntent();
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		
		TextView text = (TextView) findViewById(R.id.textView1);
//		for (int i = 0; i < tagFromIntent.getTechList().length; i++) {
//			Log.e(LOG_TAG, tagFromIntent.getTechList()[i]);
//		}
		text.setText(readTag(tagFromIntent));
//		dumpIntent(intent);


	}
//	 public static void dumpIntent(Intent i){
//
//		    Bundle bundle = i.getExtras();
//		    if (bundle != null) {
//		        Set<String> keys = bundle.keySet();
//		        Iterator<String> it = keys.iterator();
//		        Log.e(LOG_TAG,"Dumping Intent start");
//		        while (it.hasNext()) {
//		            String key = it.next();
//		            if(bundle.get(key).getClass().toString().equals("class android.nfc.Tag"))
//		            	Log.e(LOG_TAG, "[found a tag "+ bundle.get(key).getTechList() +"]");
//		            Log.e(LOG_TAG,"[" + key + "=" + bundle.get(key)+"]");
//		        }
//		        Log.e(LOG_TAG,"Dumping Intent end");
//		    }
//		}

	public String readTag(Tag tag) {
        MifareUltralight mifare = MifareUltralight.get(tag);
        try {
            mifare.connect();
            byte[] payload = mifare.readPages(4);
            return new String(payload, Charset.forName("US-ASCII"));
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException while writing MifareUltralightmessage...", e);
        } finally {
            if (mifare != null) {
               try {
                   mifare.close();
               }
               catch (IOException e) {
                   Log.e(LOG_TAG, "Error closing tag...", e);
               }
            }
        }
        return null;
    }

}
