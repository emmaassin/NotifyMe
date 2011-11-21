package org.cortelyoucollective.notifyme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.cortelyoucollective.notifyme.R;

public class MTAInfoActivity extends Activity{
	
	private Button backButton;
	private TextView delayTextView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delay_info);
        
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/VarelaRound-Regular.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/DINEngschrift-Regular.ttf");
        
        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(font);

        TextView infoHeader = (TextView) findViewById(R.id.info_header);
        infoHeader.setTypeface(font);

        TextView infoBody = (TextView) findViewById(R.id.info_body);
		infoBody.setTypeface(font);

        // set the text
        Intent intent = getIntent();
        String status_txt = intent.getStringExtra("status_txt");

        delayTextView = (TextView) findViewById(R.id.delay_info_text);
        delayTextView.setTypeface(font);
        delayTextView.setText(Html.fromHtml(status_txt));
        
        backButton = (Button) findViewById(R.id.home_btn);
        backButton.setTypeface(font2);
        backButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();
				//Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivityForResult(myIntent, 0);
			}
		});
	}
}
