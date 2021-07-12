package com.example.mechaniconway1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmailAdmin extends AppCompatActivity {

    private EditText mEditTextSubject;
    private EditText mEditTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_admin);

        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);

        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
            private void sendMail() {
//                String recipientList = mEditTextTo.getText().toString();
//                String[] recipients = recipientList.split(",");
                String[] recipients = {"jdjahangir446@gmail.com"};
                String subject = mEditTextSubject.getText().toString();
                String message = mEditTextMessage.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                //intent.putExtra(Intent.EXTRA_EMAIL, "jdjahangir446@gmail.com");
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an email client"));
            }
        });

    }
    }
