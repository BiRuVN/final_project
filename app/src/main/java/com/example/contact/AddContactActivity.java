package com.example.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    EditText edtName,edtMobile,edtEmail;
    Button btnCancel,btnFinish;
    ImageView imgAva;
    int ava = R.drawable.img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        edtName=findViewById(R.id.edt_name);
        edtMobile=findViewById(R.id.edt_mobile);
        edtEmail=findViewById(R.id.edt_email);
        btnCancel=findViewById(R.id.btn_cancel);
        btnFinish=findViewById(R.id.btn_finish);
        imgAva=findViewById(R.id.profile_image);

        imgAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddContactActivity.this, ImagesActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtName = (edtName.getText().toString());
                String txtPhone = (edtMobile.getText().toString());
                String txtEmail = (edtEmail.getText().toString());
                if (txtName.length() == 0) {
                    Toast.makeText(AddContactActivity.this, "Insert Name", Toast.LENGTH_SHORT).show();
                } else if (txtPhone.length() == 0) {
                    Toast.makeText(AddContactActivity.this, "Insert Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    Contact contact = new Contact(txtName, txtPhone, ava, txtEmail);
                    bundle.putSerializable("contact2", contact);
                    intent.putExtra("package2", bundle);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ava = intent.getExtras().getInt("img",1);
                imgAva.setImageResource(ava);
            }
        }
    }
}
