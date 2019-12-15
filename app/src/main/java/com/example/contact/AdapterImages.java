package com.example.contact;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class AdapterImages extends BaseAdapter {

    int [] imageId;
    Context context;

    public AdapterImages(int[] imageId, Context context) {
        this.imageId = imageId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageId.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView==null){
            imageView=new ImageView(context);
            imageView.setLayoutParams(new ListView.LayoutParams(300,300));
        }
        else{
            imageView= (ImageView) convertView;
        }
        imageView.setImageResource((imageId[position]));
        return imageView;
    }
}
