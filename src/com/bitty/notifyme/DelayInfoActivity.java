package com.bitty.notifyme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DelayInfoActivity extends Activity{
	
	private Button homeBtn;
	private TextView delayTextView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delay_info);
        
        homeBtn = (Button) findViewById(R.id.home_btn);
        delayTextView = (TextView) findViewById(R.id.delay_info_text);
        
        // set the text
        
        homeBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();
				Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
			}
		});
	}
}
