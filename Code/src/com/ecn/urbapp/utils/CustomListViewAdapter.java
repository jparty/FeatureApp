package com.ecn.urbapp.utils;

import java.util.List;
import com.ecn.urbapp.R;
import com.ecn.urbapp.utils.RowItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CustomListViewAdapter extends ArrayAdapter<RowItem> {
 
    Context context;
 
    public CustomListViewAdapter(Context context, int resourceId,
            List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }
 
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_photolistview, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.photolistview_description);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.photolistview_titre);
            holder.imageView = (ImageView) convertView.findViewById(R.id.photolistview_img);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
 
        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        
        /**
         * Load Pictures from local data
         */
    	Bitmap myBitmap = BitmapFactory.decodeFile(rowItem.getImagePath());
    	holder.imageView.setImageBitmap(myBitmap);
	     
        return convertView;
    }
}