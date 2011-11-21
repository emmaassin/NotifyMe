package org.cortelyoucollective.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;

public class Serializer
{
	public static String serializeArray(List<String> arr, Context context)
	{
		// serialize subwaySelected
		Calendar calendar = Calendar.getInstance();
		long time = calendar.getTimeInMillis();
		String subwaySerialized = "subway" + Long.toString(time)  + ".ser";
		try
		{
			FileOutputStream fos = context.openFileOutput(subwaySerialized, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(arr);
			out.close();
			fos.close();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		return subwaySerialized;
	}
	
	public static ArrayList<String> deSerializeArray(String serialString, Context context){
		ArrayList<String> array = null;
		try
		{
			FileInputStream fis = context.openFileInput(serialString);
			ObjectInputStream in = new ObjectInputStream(fis);
			array = (ArrayList<String>) in.readObject();
			in.close();
			fis.close();
			
		} catch (IOException ex)
		{
			ex.printStackTrace();
		} catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		
		return array;
	}
	
}
