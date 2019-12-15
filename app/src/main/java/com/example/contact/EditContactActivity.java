package com.example.contact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity {
    EditText edtName,edtMobile, edtEmail;
    Button btnCancel,btnFinish;
    FloatingActionButton btnFav, btnDelete, btnSMS, btnEmail;
    Contact contact;
    ImageView imgAva;
    int ava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        edtName=findViewById(R.id.edt_nameedit);
        edtMobile=findViewById(R.id.edt_mobileedit);
        edtEmail = findViewById(R.id.edt_emailedit);
        imgAva = findViewById(R.id.profile_image);
        btnCancel=findViewById(R.id.btn_canceledit);
        btnFinish=findViewById(R.id.btn_finishedit);
        btnDelete = findViewById(R.id.btn_del);
        btnFav = findViewById(R.id.btn_fav);
        btnSMS = findViewById(R.id.btn_sms);
        btnEmail = findViewById(R.id.btn_email);

        Bundle bundle = getIntent().getBundleExtra("package3");
        contact = (Contact) bundle.getSerializable("contact3");
        edtName.setText(contact.getName());
        edtMobile.setText(contact.getPhone());
        edtEmail.setText(contact.getEmail());
        ava = contact.getImage();
        imgAva.setImageResource(ava);

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
                String txtEmail = (edtEmail.getText().toString());
                if (txtName.length() == 0) {
                    Toast.makeText(EditContactActivity.this, "Insert Name", Toast.LENGTH_SHORT).show();
                } else if (txtPhone.length() == 0) {
                    Toast.makeText(EditContactActivity.this, "Insert Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    contact.setName(txtName);
                    contact.setPhone(txtPhone);
                    contact.setEmail(txtEmail);
                    contact.setImage(ava);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contact1", contact);
                    Intent intent = getIntent();
                    intent.putExtra("package1", bundle);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact1", contact);
                Intent intent = getIntent();
                intent.putExtra("package1", bundle);
                setResult(123, intent);
                finish();
            }
        });
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SendSMS.class);
                intent.putExtra("phone", edtMobile.getText().toString());
                startActivity(intent);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SendMail.class);
                intent.putExtra("mailaddress", edtEmail.getText().toString());
                startActivity(intent);
            }
        });
        imgAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditContactActivity.this, ImagesActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ava = intent.getExtras().getInt("img",1);
                imgAva.setImageResource((ava));
            }
        }
    }
}
