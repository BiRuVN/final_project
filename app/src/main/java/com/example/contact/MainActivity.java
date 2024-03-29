package com.example.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    ListView lvContacts;
    FloatingActionButton btnAddContact;
    int index;

    ArrayList<Contact> contacts, tempArrayList;
    CustomAdapter customAdapter, mAdapter;
    private MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearch=findViewById(R.id.edt_search);
        lvContacts=findViewById(R.id.lv_contacts);
        btnAddContact=findViewById(R.id.btn_add);

        contacts=new ArrayList<>();

        db=new MyDatabase(this);
        contacts=db.getAllContact();
        customAdapter=new CustomAdapter(
                this, R.layout.row_listview, contacts);
        lvContacts.setAdapter(customAdapter);

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index=i;
                Bundle bundle = new Bundle();
                if(lvContacts.getAdapter()==customAdapter) bundle.putSerializable("contact3",contacts.get(i));
                else bundle.putSerializable("contact3",tempArrayList.get(i));
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

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                int textlength = cs.length();
                if(textlength==0){
                    lvContacts.setAdapter(customAdapter);
                }
                else {
                    tempArrayList = new ArrayList<>();
                    for (Contact c : contacts) {
                        if (textlength <= c.getName().length()) {
                            if (c.getName().toLowerCase().startsWith(cs.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    if (edtSearch.getText().toString() == "") {
                        lvContacts.setAdapter(customAdapter);
                    } else {
                        mAdapter = new CustomAdapter(getApplicationContext(), R.layout.row_listview, tempArrayList);
                        lvContacts.setAdapter(mAdapter);
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
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
//                customAdapter.notifyDataSetChanged();
            }
            if (resultCode == 123) {
                try {
                    Bundle bundle = intent.getBundleExtra("package1");
                    Contact contact = (Contact) bundle.getSerializable("contact1");
                    db.deleteContact(contact);
                    contacts.remove(index);
//                    customAdapter.notifyDataSetChanged();
                } catch (NullPointerException e) {

                }
            }
        }
        if (requestCode == 2) {
                if(resultCode == Activity.RESULT_OK) {
                try {
                    Bundle bundle = intent.getBundleExtra("package2");
                    Contact contact = (Contact) bundle.getSerializable("contact2");
                    db.addContact(contact);
                    contacts.add(contact);
//                    customAdapter.notifyDataSetChanged();
                } catch (NullPointerException e) {

                }
            }
        }
        customAdapter.notifyDataSetChanged();
        edtSearch.setText("");

    }
}
