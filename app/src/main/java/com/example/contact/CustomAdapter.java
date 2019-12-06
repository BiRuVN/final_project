package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class CustomAdapter extends ArrayAdapter<Contact> {

    Context context;
    int resource;
    ArrayList<Contact> arrContact,tempItems, suggestions;

    public CustomAdapter(Context context, int resource, ArrayList<Contact> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
        tempItems = new ArrayList<>(arrContact);
        suggestions = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName =  convertView.findViewById(R.id.tv_name);
            viewHolder.imgPhone =  convertView.findViewById(R.id.img_phone);
            viewHolder.imgFav = convertView.findViewById(R.id.img_fav);

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
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return contactFilter;
    }
    private Filter contactFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Contact contact = (Contact) resultValue;
            return contact.getName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (Contact contact: tempItems) {
                    if (contact.getName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(contact);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Contact> tempValues = (ArrayList<Contact>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Contact contactObj : tempValues) {
                    add(contactObj);
                    notifyDataSetChanged();
                }
            }
            else {
                clear();
//                for (Contact contactObj : arrContact) {
//                    add(contactObj);
                    notifyDataSetChanged();
//                }
            }
        }
    };
}
