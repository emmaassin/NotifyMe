package com.bitty.notifyme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MTAInfoActivity extends Activity{
	
	private Button backButton;
	private TextView delayTextView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delay_info);
        
        backButton = (Button) findViewById(R.id.home_btn);
        delayTextView = (TextView) findViewById(R.id.delay_info_text);
        
        TextView title = (TextView) findViewById(R.id.title);
        TextView infoHeader = (TextView) findViewById(R.id.info_header);
		TextView infoBody = (TextView) findViewById(R.id.info_body);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/VarelaRound-Regular.ttf");
		Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/DINEngschrift-Regular.ttf");
		
		title.setTypeface(font);
		delayTextView.setTypeface(font);
		infoHeader.setTypeface(font);
		infoBody.setTypeface(font);
        backButton.setTypeface(font2);
		
        // set the text
        Intent intent = getIntent();
        String status_txt = intent.getStringExtra("status_txt");
        delayTextView.setText(Html.fromHtml(status_txt));
        
        backButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();
				//Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivityForResult(myIntent, 0);
			}
		});
	}
}
