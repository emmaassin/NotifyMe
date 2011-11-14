package com.bitty.notifyme;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SettingsDialog extends Dialog
{
	private TextView dialogText;
	private CheckBox toneBox, vibrateBox;
	public Button deleteAllButton, saveButton;
	private SharedPreferences settings;
	private SharedPreferences.Editor prefEditor;
	
	public SettingsDialog(Context context) {
		super(context, R.style.FullscreenDialogTheme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings_dialog);
		
		settings = context.getSharedPreferences("NotifyMeSettings", context.MODE_PRIVATE);
		prefEditor = settings.edit();
		
		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/VarelaRound-Regular.ttf");
		Typeface font2 = Typeface.createFromAsset(context.getAssets(), "fonts/DINEngschrift-Regular.ttf");
		
		dialogText = (TextView) findViewById(R.id.settings_header);
		dialogText.setTypeface(font);
		
		deleteAllButton = (Button) findViewById(R.id.delete_all);
		deleteAllButton.setTypeface(font2);
		
		saveButton = (Button) findViewById(R.id.save_button);
		saveButton.setTypeface(font2);
		
		toneBox = (CheckBox) findViewById(R.id.toneCheckBox);
		vibrateBox = (CheckBox) findViewById(R.id.vibrateCheckBox);
		toneBox.setTypeface(font);
		vibrateBox.setTypeface(font);
		
		if(settings.contains("tone") == false)
		{
			toneBox.setChecked(true);
		} else {
			if(settings.getBoolean("tone", true))
			{
				toneBox.setChecked(true);
			} else { 
				toneBox.setChecked(false);
			}
		}
		
		if(settings.contains("vibration") == false)
		{
			vibrateBox.setChecked(true);
		} else {
			if(settings.getBoolean("vibration", true))
			{
				vibrateBox.setChecked(true);
			} else { 
				vibrateBox.setChecked(false);
			}
		}
		
		saveButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				prefEditor.putBoolean("tone", toneBox.isChecked());
				prefEditor.putBoolean("vibration", vibrateBox.isChecked());
				prefEditor.commit();
				cancel();
			}
		});
	}

}
