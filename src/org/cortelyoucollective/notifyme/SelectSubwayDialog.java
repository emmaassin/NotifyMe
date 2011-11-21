package org.cortelyoucollective.notifyme;

import java.util.ArrayList;
import java.util.List;
import org.cortelyoucollective.notifyme.R;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectSubwayDialog extends Dialog
{
	private static final String TAG = "SelectSubwayDialog";
	
	private TextView dialogText;
	private LinearLayout subwayLinesHolder;
	public Button saveButton;
	public Button cancelButton;
	private static int[] lineImageArray = {R.drawable.line_123, R.drawable.line_456, R.drawable.line_7, R.drawable.line_ace, R.drawable.line_bdfm, R.drawable.line_g, R.drawable.line_jz, R.drawable.line_l, R.drawable.line_nqr, R.drawable.line_s, R.drawable.line_sir};
	private static int[] lineImageSelectedArray = {R.drawable.line_123_s, R.drawable.line_456_s, R.drawable.line_7_s, R.drawable.line_ace_s, R.drawable.line_bdfm_s, R.drawable.line_g_s, R.drawable.line_jz_s, R.drawable.line_l_s, R.drawable.line_nqr_s, R.drawable.line_s_s, R.drawable.line_sir_s};
	private static int[] lineIdArray = {R.id.l123, R.id.l456, R.id.l7, R.id.lace, R.id.lbdfm, R.id.lg, R.id.ljz, R.id.ll, R.id.lnqr, R.id.ls, R.id.lsir};
	private List<SelectSubwayButton> lineButtonArray = new ArrayList<SelectSubwayButton>();
	private List<String> checkedLinesArray = new ArrayList<String>();

	public SelectSubwayDialog(Context context)
	{
		super(context, R.style.FullscreenDialogTheme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.train_select_dialog);
		
		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/VarelaRound-Regular.ttf");
		Typeface font2 = Typeface.createFromAsset(context.getAssets(), "fonts/DINEngschrift-Regular.ttf");

		saveButton = (Button) findViewById(R.id.save_button);
		saveButton.setTypeface(font2);
		
		cancelButton = (Button) findViewById(R.id.cancel_button);
		cancelButton.setTypeface(font2);
		
		dialogText = (TextView) findViewById(R.id.other_lines_text);
		dialogText.setTypeface(font);
		subwayLinesHolder = (LinearLayout) findViewById(R.id.subway_lines_holder);

		cancelButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				cancel();
			}
		});
		
		Resources res = context.getResources();
		String[] trainLines = res.getStringArray(R.array.trains_array);

		for (int i = 0; i < trainLines.length; i++)
		{
			final SelectSubwayButton tlb = (SelectSubwayButton) findViewById(lineIdArray[i]);
			lineButtonArray.add(tlb);
			tlb.setImages(lineImageArray[i], lineImageSelectedArray[i], trainLines[i]);
			tlb.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					SelectSubwayButton tb = (SelectSubwayButton) v;
					if(tb.selected)
					{
						tb.setDeSelected();
						checkedLinesArray.remove(tb.id);
					} else {
						tb.setSelected();
						checkedLinesArray.add(tb.id);
					}
				}
			});
		}
	}

	public ArrayList getCheckedLinesArray()
	{
		return (ArrayList) checkedLinesArray;
	}

	public void setAlreadyChecked(ArrayList<String> trainLinesArray)
	{
		for (int i = 0; i < lineButtonArray.size(); i++)
		{
			if (trainLinesArray.contains(lineButtonArray.get(i).id))
			{
				lineButtonArray.get(i).setSelected();
			}
			checkedLinesArray = trainLinesArray;
		}
	}
}
