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
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
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
		TextView text = (TextView) findViewById(R.id.textView1);
		
		
//		for (int i = 0; i < tagFromIntent.getTechList().length; i++) {
//			Log.e(LOG_TAG, tagFromIntent.getTechList()[i]);
//		}
		
		dumpIntent(intent, text);


		


	}
	 public static void dumpIntent(Intent i, TextView text){

		    Bundle bundle = i.getExtras();
		    if (bundle != null) {
		        Set<String> keys = bundle.keySet();
		        Iterator<String> it = keys.iterator();
		        //Log.e(LOG_TAG,"Dumping Intent start");
		        while (it.hasNext()) {
		            String key = it.next();
		            if(bundle.get(key).getClass().toString().equals("class android.nfc.Tag"))
		            	tagwork((Tag) bundle.get(key), text);
		         //   Log.e(LOG_TAG,"[" + key + "=" + bundle.get(key)+"]");
		        }
		       // Log.e(LOG_TAG,"Dumping Intent end");
		    }
		}
	 public static void tagwork(Tag tag, TextView view){

		 String[] words = tag.getTechList();
			for (int i = 0; i < words.length; i++) {
				view.append(words[i] + "\n");
			}
			IsoDep deep = IsoDep.get(tag);
			try {
				deep.connect();
				view.append("Connected \n");
				printHisotry(deep, view);
				printMaxTransceiveLength(deep, view);
				printId(deep, view);
				printTimeOut(deep, view);
				deep.close();
			} catch (Exception e) {
				
			}
	 }
	private static void printTimeOut(IsoDep deep, TextView view) {
		String str = "Time out: ";
		str += String.format("%S MS\n", deep.getTimeout());
		view.append(str);
	}
	private static void printId(IsoDep deep, TextView view) {
		Tag tag = deep.getTag();
		String str = "Tag ID: ";
		for (byte b: tag.getId()){
			str += String.format("%02X:",b);
			}
		view.append(str.substring(0,str.length()-1)+"\n");
		
	}
	private static void printMaxTransceiveLength(IsoDep deep, TextView text) {
		String str = "Max transceive length: ";
		str += String.format("%S", deep.getMaxTransceiveLength());
		text.append(str+ " bits\n");
	}
	private static void printHisotry(IsoDep deep, TextView view) {
		String str = "Historical bytes: ";
		for (byte b: deep.getHistoricalBytes()){
			str += String.format("0x%X%n",b);
			}
		view.append(str);
	}
	 

}