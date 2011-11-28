package org.cortelyoucollective.notifyme;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import org.cortelyoucollective.notifyme.R;

public class TrainTypeDialog extends Dialog
{
	private TextView dialogText;
	public Button subwayBtn;
	public Button LIRRBtn;
	public Button MNBtn;
	
	public TrainTypeDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.train_type_dialog);
		
		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/VarelaRound-Regular.ttf");
		
		dialogText = (TextView) findViewById(R.id.settings_header);
		dialogText.setTypeface(font);
		subwayBtn = (Button) findViewById(R.id.subway_btn);
		subwayBtn.setTypeface(font);
		LIRRBtn = (Button) findViewById(R.id.LIRR_btn);
		LIRRBtn.setTypeface(font);
		MNBtn = (Button) findViewById(R.id.MN_btn);
		MNBtn.setTypeface(font);
	}

}
