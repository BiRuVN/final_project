package com.example.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView tvSearch;
    ListView lvContacts;
    FloatingActionButton btnAddContact;
    int index;

    ArrayList<Contact> contacts;
    CustomAdapter customAdapter, adapterSearch;
    private MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSearch=findViewById(R.id.tv_search);
        lvContacts=findViewById(R.id.lv_contacts);
        btnAddContact=findViewById(R.id.btn_add);

        contacts=new ArrayList<>();

        db=new MyDatabase(this);
        contacts=db.getAllContact();
        customAdapter=new CustomAdapter(
                this, R.layout.row_listview, contacts);
        lvContacts.setAdapter(customAdapter);

        adapterSearch = new CustomAdapter(this, R.layout.row_listview, contacts);
        tvSearch.setAdapter(adapterSearch);
        tvSearch.setThreshold(1);

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index=i;
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact3",contacts.get(i));
                Intent intent= new Intent(MainActivity.this, EditContactActivity.class);
                intent.putExtra("package3",bundle);
                startActivityForResult(intent,1);
            }
        });

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent,2);
            }
        });
        tvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int index=contacts.indexOf(((TextView)view).getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact3",contacts.get(index));
                Intent intent= new Intent(MainActivity.this,EditContactActivity.class);
                intent.putExtra("package3",bundle);
                startActivityForResult(intent,1);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = intent.getBundleExtra("package1");
                Contact contact = (Contact) bundle.getSerializable("contact1");
                db.updateContact(contact);
                contacts.set(index, contact);
                customAdapter.notifyDataSetChanged();
//                adapterSearch.notifyDataSetChanged();
            }
            if (resultCode == 123) {
                try{
                    Bundle bundle = intent.getBundleExtra("package1");
                    Contact contact = (Contact) bundle.getSerializable("contact1");
                    db.deleteContact(contact);
                    contacts.remove(index);
                    customAdapter.notifyDataSetChanged();
//                    adapterSearch.notifyDataSetChanged();
                }
                catch (NullPointerException e){

                }
        }
        if (requestCode == 2) {
                if(resultCode == Activity.RESULT_OK) {
                try {
                    Bundle bundle = intent.getBundleExtra("package2");
                    Contact contact = (Contact) bundle.getSerializable("contact2");
                    db.addContact(contact);
                    contacts.add(contact);
                    customAdapter.notifyDataSetChanged();
                } catch (NullPointerException e) {
                }
            }
        }


        }

    }
}
