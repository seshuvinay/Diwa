package com.diwa.adapters;

import com.diwa.activities.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter {
	Context context;
	Bitmap[] bitmaps;

	public MyAdapter(Context context, Bitmap[] bitmaps) {
		this.context = context;
		this.bitmaps = bitmaps;

	}

	@Override
	public int getCount() {
		return bitmaps.length;
	}

	@Override
	public Object getItem(int position) {
		return bitmaps[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		{
			View v = convertView;
			MyHolder holder = new MyHolder();
			if (convertView == null) {
				// This a new view we inflate the new layout
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.grid_item, null);
				// Now we can fill the layout with the right values

				ImageView img = (ImageView) v.findViewById(R.id.image_gridview);

				img.setImageBitmap(bitmaps[position]);

				holder.gridIV = img;

				v.setTag(holder);

			} else {
				holder = (MyHolder) v.getTag();

				System.out.println("Position [" + position + "]");

				return v;
			}

			return v;
		}
	}

}
