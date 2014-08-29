package com.example.ftcardreder;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b1 = (Button)findViewById(R.id.button1);


		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AssetManager assetManager = getAssets();
				TextView text = (TextView) findViewById(R.id.textView1);
				
			      InputStream input;



				try {

					input = assetManager.open("test.txt");
					int size = input.available();
					byte[] buffer = new byte[size];
					input.read(buffer);
					input.close();

					// byte buffer into a string
					String txt = new String(buffer);
					text.setText(txt);

				} catch (IOException e) {

					// TODO Auto-generated catch block

					e.printStackTrace();

				}

			}
		});


	}




}
