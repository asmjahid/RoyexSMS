package com.asmjahid.royexsms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;


public class SmsReceiver extends BroadcastReceiver{

    Context c;
    String senderNumber;
    SmsMessage receivedmessage;

    // SMS reply
    SmsManager manager = SmsManager.getDefault();
    PendingIntent sentPI;
    String SENT = "SMS_SENT";

    @Override
    public void onReceive(Context context, Intent intent) {

        c = context;

        // Message Details Bundle
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                // Something modified
                assert pdusObj != null;
                for (Object aPdusObj : pdusObj) {
                    receivedmessage = SmsMessage
                            .createFromPdu((byte[]) aPdusObj);
                }
            }

            // Sender Number
            senderNumber = receivedmessage.getDisplayOriginatingAddress();
            Log.d("SMS", "Number:" + senderNumber);
            // Message contains
            String msg = receivedmessage.getDisplayMessageBody();
            Log.d("SMS", "Message:" + msg);

            //assert secretNo != null;

            // Predefined pass and mgs
            String Sendmessage = "Hello User!!! This is a testing SMS from Royex";
            String message = "Royex";

            if (msg.contains("007")) {

                if (msg.contains(",")) {

                    if (msg.contains(message)) {

                        sentPI = PendingIntent.getBroadcast(c, 0,new Intent(SENT), 0);
                        manager.sendTextMessage(senderNumber, null, Sendmessage, sentPI, null);
                        //manager.sendTextMessage(senderNumber, null, Sendmessage, null, null);
                    }
                }
            } else {
                Log.d("X", "No Special Commands Found");
            }

        } catch (Exception e) {
            Log.e("SMS", "Exception SMSReceiver" + e.getMessage());
        }
    }
}