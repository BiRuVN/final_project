package com.example.contact;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class EditContactActivity extends AppCompatActivity {
    EditText edtName,edtMobile, edtSMS;
    Button btnCancel,btnFinish, btnSMS, btnSendSMS;
    FloatingActionButton btnFav, btnDelete;
    Contact contact;
    boolean invisible = true;

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        edtName=findViewById(R.id.edt_nameedit);
        edtMobile=findViewById(R.id.edt_mobileedit);
        btnCancel=findViewById(R.id.btn_canceledit);
        btnFinish=findViewById(R.id.btn_finishedit);
        btnDelete = findViewById(R.id.btn_del);
        btnFav = findViewById(R.id.btn_fav);
        btnSendSMS = findViewById(R.id.btn_sendsms);
        btnSMS = findViewById(R.id.btn_sms);
        edtSMS = findViewById(R.id.textsms);

        Bundle bundle = getIntent().getBundleExtra("package3");
        contact = (Contact) bundle.getSerializable("contact3");
        edtName.setText(contact.getName());
        edtMobile.setText(contact.getPhone());
        edtSMS.setVisibility(View.INVISIBLE);
        btnSendSMS.setVisibility(View.INVISIBLE);

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
                if(invisible){
                    edtSMS.setVisibility(View.VISIBLE);
                    btnSendSMS.setVisibility(View.VISIBLE);
                    invisible = false;
                }
                else{
                    edtSMS.setVisibility(View.INVISIBLE);
                    edtSMS.setText("");
                    btnSendSMS.setVisibility(View.INVISIBLE);
                    invisible = true;
                }
            }
        });
        btnSendSMS.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)){
            btnSendSMS.setEnabled(true);
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSend();
            }
        });
    }

    public void onSend(){
        String phoneNum = edtMobile.getText().toString();
        String mess = edtSMS.getText().toString();
        if(phoneNum == null || phoneNum.length() == 0 || mess == null || mess.length() == 0){
            return;
        }

        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, mess, null, null);
            Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
            edtSMS.setVisibility(View.INVISIBLE);
            edtSMS.setText("");
            btnSendSMS.setVisibility(View.INVISIBLE);
            invisible = true;
        }
        else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    public  boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
