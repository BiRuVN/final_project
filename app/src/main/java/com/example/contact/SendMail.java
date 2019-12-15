package com.example.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SendMail extends AppCompatActivity {

    private TextView eTo;
    private EditText eSubject, eMsg;
    private Button btnsend, btnquit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        eTo = findViewById(R.id.txtTo);
        eSubject = findViewById(R.id.txtSub);
        eMsg = findViewById(R.id.txtMsg);
        btnsend = findViewById(R.id.btnSend);
        btnquit = findViewById(R.id.btnQuit);

        Intent i = getIntent();
        String eaddress = i.getStringExtra("mailaddress");
        eTo.append(eaddress);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{eTo.getText().toString()});
                it.putExtra(Intent.EXTRA_SUBJECT,eSubject.getText().toString());
                it.putExtra(Intent.EXTRA_TEXT,eMsg.getText());
                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it,"Choose Mail App"));
            }
        });
        btnquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
