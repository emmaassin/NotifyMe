package org.cortelyoucollective.notifyme;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class MTAStatusItemList extends ArrayList<MTAStatusItem> implements Parcelable
{

	public MTAStatusItemList(){
		
	}
	
	public MTAStatusItemList(Parcel in)
	{
		readFromParcel(in);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public MTAStatusItemList createFromParcel(Parcel in) 
		{
			return new MTAStatusItemList(in);
		}
		
		public Object[] newArray(int arg0)
		{
			return null;
		}
	};
	
	private void readFromParcel(Parcel in)
	{
		this.clear();
		int size = in.readInt();
		
		for(int i = 0; i < size; i++)
		{
			MTAStatusItem msi = new MTAStatusItem(in.readString(), in.readString(), in.readString(), in.readString(), in.readString(), in.readString());
			this.add(msi);
		}
	}
	
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		int size = this.size();
		dest.writeInt(size);
		for(int i = 0; i < size; i++)
		{
			MTAStatusItem msi = this.get(i);
			dest.writeString(msi.getLine());
			dest.writeString(msi.getStatus());
			dest.writeString(msi.getStatusText());
			dest.writeString(msi.getDate());
			dest.writeString(msi.getTime());
			dest.writeString(msi.getTransitType());
		}
	}

}
