package com.bitty.notifyme;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class SelectSubwayButton extends Button
{

	private int image;
	private int selectedImage;
	
	public String id;

	public boolean selected = false;
	
	public SelectSubwayButton(Context context) {
		super(context);
		
	}
	
	public SelectSubwayButton(Context context, AttributeSet attr) {
		super(context, attr);
		
	}

	public void setImages(int lineImage, int lineSelectedImage, String theId)
	{
		image = lineImage;
		selectedImage = lineSelectedImage;
		id = theId;
		setBackgroundResource(image);
	}
	

	
	public void setSelected()
	{
		selected = true;
		setBackgroundResource(selectedImage);
	}
	
	public void setDeSelected()
	{
		selected = false;
		setBackgroundResource(image);
	}
	
}
