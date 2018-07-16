package com.example.abhimangalms.phone;

/**
 * Created by Abhimangal on 16/04/2018.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Pattern;


public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;
    public static String testKey;

    public void onReceive(Context context, Intent intent) {

        sh = context.getSharedPreferences("myprefe", 0);
        editor = sh.edit();

        //key stored while registering
        testKey = sh.getString("userKey", null);

        Toast.makeText(context, "testKey is" + testKey, Toast.LENGTH_SHORT).show();



        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    //sendData(senderNum);


                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                    if(!Pattern.matches("[a-zA-Z]+", message)){

                        Toast.makeText(context, "Look here" + message, Toast.LENGTH_SHORT).show();
                        intent = new Intent(context, Contact.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("message", message); //use message instead str[0]
                        //intent.putExtra("sender", senderNum);
                        context.startActivity(intent);

                    }

                    String dbdata = message;
                    String str[]=dbdata.split(" * ");

                    str[0] = str[0].trim();
                    str[1] = str[1].trim();
                    Toast.makeText(context, "key is "+ str[1], Toast.LENGTH_SHORT).show();

                    //check message is phone number or requestString

                    // if (str[0].matches("^[0-9]+$")){
                     if ((!Pattern.matches("[a-zA-Z]+", str[1]))){

                         Toast.makeText(context, "Phone number is " + str[1], Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Setting incoming", Toast.LENGTH_SHORT).show();
                        intent = new Intent(context, Contact.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("message", message); //use message instead str[0]
                        //intent.putExtra("sender", senderNum);
                        context.startActivity(intent);

                    }


                    if (str[1].equals(testKey)) {


                        Intent intent1 = new Intent(context, MainActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.putExtra("message", str[0]); //use message instead str[0]
                        intent1.putExtra("sender", senderNum);

                        //Toast.makeText(context, "This is second loop", Toast.LENGTH_SHORT).show();
                        context.startActivity(intent1);

                     }


                     else
                         {

                        Toast.makeText(context, "Someone tries to access your device", Toast.LENGTH_SHORT).show();


                    }



                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }



}
