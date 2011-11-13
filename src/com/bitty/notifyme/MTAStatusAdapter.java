package com.bitty.notifyme;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MTAStatusAdapter extends ArrayAdapter<MTAStatusItem>
{
	private static final String TAG = "StatusAdapter";

	int resource;

	public MTAStatusAdapter(Context context, int _resource, List<MTAStatusItem> objects)
	{
		super(context, _resource, objects);
		resource = _resource;
		// Log.w(TAG, "StatusAdapter init");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		MTAStatusItem item = getItem(position);

		if (convertView == null)
		{
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(resource, null);

			holder = new ViewHolder();
			//holder.lineTextView = (TextView) convertView.findViewById(R.id.line_id);
			holder.lineImageView = (ImageView) convertView.findViewById(R.id.line_id_image);
			holder.statusView = (TextView) convertView.findViewById(R.id.status_id);
			Typeface font2 = Typeface.createFromAsset(getContext().getAssets(), "fonts/DINEngschrift-Regular.ttf");
			holder.statusView.setTypeface(font2);
			holder.statusView.setTextSize(20);
			convertView.setTag(holder);

			holder.statusView.setTextColor(convertView.getResources().getColor(R.color.red));

		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		String line = item.getLine();
		String status = item.getStatus();
		initTransitItem(holder, item, line, status, convertView);

		return convertView;
	}


	private void initTransitItem(ViewHolder holder, MTAStatusItem item, String line, String status, View convertView)
	{
		Resources resource = convertView.getResources();

		String resID = "line_" + line.toLowerCase();
		int resources = resource.getIdentifier(resID, "drawable", "com.bitty.notifyme");
		holder.lineImageView.setImageResource(resources);
		holder.lineImageView.setVisibility(TextView.VISIBLE);

		holder.statusView.setVisibility(TextView.VISIBLE);
		holder.statusView.setText(status);

		// Set text color for different status
		if (status.equals(resource.getString(R.string.mta_status_good)))
			holder.statusView.setTextColor(resource.getColor(R.color.green));

		if (status.equals(resource.getString(R.string.mta_status_delay)) || status.equals(resource.getString(R.string.mta_status_planned)))
			holder.statusView.setTextColor(resource.getColor(R.color.red));

		if (status.equals(resource.getString(R.string.mta_status_service)))
			holder.statusView.setTextColor(resource.getColor(R.color.orange));
	}

	static class ViewHolder
	{
		TextView lineTextView;
		ImageView lineImageView;
		TextView statusView;
	}
}
