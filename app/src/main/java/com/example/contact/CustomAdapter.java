package com.example.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int resource;
    private ArrayList<Contact> arrContact;

    public CustomAdapter(Context context, int resource, ArrayList<Contact> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }
    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName =  convertView.findViewById(R.id.tv_name);
            viewHolder.imgPhone =  convertView.findViewById(R.id.img_phone);
            viewHolder.imgFav = convertView.findViewById(R.id.img_fav);
            viewHolder.prof_img = convertView.findViewById(R.id.profile_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Contact contact = arrContact.get(position);

        int fav = contact.getFav();
        if (fav == 1) {
            int color = Color.parseColor("#FFFF0000");  //red
            viewHolder.imgFav.setColorFilter(color);
        } else {
            int color = Color.parseColor("#FF000000");  //black
            viewHolder.imgFav.setColorFilter(color);
        }

        viewHolder.prof_img.setImageResource(contact.getImage());
        viewHolder.tvName.setText(contact.getName());

        viewHolder.imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=contact.getPhone();
                Intent intent= new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone,null));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        ImageView imgPhone;
        ImageView imgFav;
        CircleImageView prof_img;
    }
}
