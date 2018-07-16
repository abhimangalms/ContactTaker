package com.example.abhimangalms.phone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Contact extends Activity {

    boolean doubleBackToExitPressedOnce = false;

    EditText mobileno,message;
    Button sendsms, mlogoutButton;
    TextView mContactView, mrequestedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mobileno=(EditText)findViewById(R.id.editText1);
        message=(EditText)findViewById(R.id.editText2);
        sendsms=(Button)findViewById(R.id.button1);
        mlogoutButton = (Button) findViewById(R.id.logoutButton);
        mContactView =(TextView) findViewById(R.id.contactView);
        mrequestedContact = (TextView) findViewById(R.id.requestedContact);
        String name = message.getText().toString();


        Intent i = getIntent();
        String message1 = i.getStringExtra("message");
        //String sendto = i.getStringExtra("sender");
        Toast.makeText(Contact.this, message1, Toast.LENGTH_SHORT).show();
        mContactView.setText(message1);
      // mrequestedContact.setText(name);

        mlogoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(Contact.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Logout ?")
                        .setMessage("Are you sure you want to logout ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent i = new Intent(Contact.this, LoginActivity.class);

                                Toast.makeText(Contact.this, "Logout successfull", Toast.LENGTH_SHORT).show();

                                startActivity(i);
                                finish();
                                //finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();


            }
        });


        sendsms.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (mobileno.getText().toString().length()!= 10 ){

                    mobileno.setError("Invalid phone number");

                }
                else{
                    String no=mobileno.getText().toString();
                    String msg=message.getText().toString();
                    Intent intent=new Intent(getApplicationContext(),Contact.class);
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(no, null, msg, pi,null);

                    Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();

                }


            }
        });
    }


    //Shows alert dialog to exit.
        @Override
        public void onBackPressed() {
            new AlertDialog.Builder(Contact.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit ?")
                    .setMessage("Are you sure you want to close ContactTaker ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }


}