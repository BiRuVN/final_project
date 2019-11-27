package com.example.contact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity {
    EditText edtName,edtMobile;
    Button btnCancel,btnFinish;
    FloatingActionButton btnFav;
    Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        edtName=findViewById(R.id.edt_nameedit);
        edtMobile=findViewById(R.id.edt_mobileedit);
        btnCancel=findViewById(R.id.btn_canceledit);
        btnFinish=findViewById(R.id.btn_finishedit);
        btnFav = findViewById(R.id.btn_fav);

        Bundle bundle = getIntent().getBundleExtra("package3");
        contact = (Contact) bundle.getSerializable("contact3");
        edtName.setText(contact.getName());
        edtMobile.setText(contact.getPhone());

        int fav = contact.getFav();
        if (fav == 1) {
            int color = Color.parseColor("#FFFF0000");  //red
            btnFav.setColorFilter(color);
        } else {
            int color = Color.parseColor("#FF000000");  //black
            btnFav.setColorFilter(color);
        }

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int fav = contact.getFav();
                if (fav == 1) {
                    int color = Color.parseColor("#FF000000");  //black
                    btnFav.setColorFilter(color);
                    contact.setFav(0);
                } else {
                    int color = Color.parseColor("#FFFF0000");  //red
                    btnFav.setColorFilter(color);
                    contact.setFav(1);
                }
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtName = (edtName.getText().toString());
                String txtPhone = (edtMobile.getText().toString());
                if (txtName.length() == 0) {
                    Toast.makeText(EditContactActivity.this, "Insert Name", Toast.LENGTH_SHORT).show();
                } else if (txtPhone.length() == 0) {
                    Toast.makeText(EditContactActivity.this, "Insert Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    contact.setName(txtName);
                    contact.setPhone(txtPhone);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contact1", contact);
                    Intent intent = getIntent();
                    intent.putExtra("package1", bundle);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

    }
}
