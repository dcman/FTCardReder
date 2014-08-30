package com.example.ftcardreder;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = this.getIntent();
		TextView text = (TextView) findViewById(R.id.textView1);
		text.setText(dumpIntent(intent));

	}
	
	public boolean readTag(Tag tag){
		NfcA nfc = NfcA.get(tag);
		boolean reply =false;
		try {
			nfc.connect();
			reply = nfc.isConnected();
			nfc.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return reply;
	}
	 public  boolean dumpIntent(Intent i){

		    Bundle bundle = i.getExtras();
		    if (bundle != null) {
		        Set<String> keys = bundle.keySet();
		        Iterator<String> it = keys.iterator();
		        while (it.hasNext()) {
		            String key = it.next();
		            if(bundle.get(key).getClass().toString().equals("class android.nfc.Tag"))
		            	return readTag((Tag) bundle.get(key));
		            	
		        }
		    }
			return false;
		}
}
