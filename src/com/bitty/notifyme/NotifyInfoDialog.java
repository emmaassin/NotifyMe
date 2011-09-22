package com.bitty.notifyme;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class NotifyInfoDialog extends Dialog{

	public Button editButton;
	public Button deleteButton;
	public Button cancelButton;
	
	private ListView notificationList;
	
	public NotifyInfoDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.notify_info_dialog);
        
        editButton = (Button) findViewById(R.id.edit_button);
        deleteButton = (Button) findViewById(R.id.delete_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        notificationList = (ListView) findViewById(R.id.notification_list);
        
        //set all the data from the database into this list]
       
		cancelButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				cancel();
			}
		});
	}
}
