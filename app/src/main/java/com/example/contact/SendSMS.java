package com.example.contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SendSMS extends AppCompatActivity {

    Button btnSendSMS;
    EditText edtSMS;
    String phone;

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        Intent i = getIntent();
        phone = i.getStringExtra("phone");

        btnSendSMS = findViewById(R.id.btn_sendsms);
        edtSMS = findViewById(R.id.textsms);

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
        String mess = edtSMS.getText().toString();
        if(phone.length() == 0 || mess.length() == 0){
            return;
        }

        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, mess, null, null);
            Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
            edtSMS.setVisibility(View.INVISIBLE);
            edtSMS.setText("");
            btnSendSMS.setVisibility(View.INVISIBLE);
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
