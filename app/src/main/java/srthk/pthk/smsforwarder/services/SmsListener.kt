package srthk.pthk.smsforwarder.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage

class SmsListener : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return
        val bundle = intent.extras!!
        val pduObjects = bundle["pdus"] as Array<*>? ?: return
        val sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        for (messageObj in pduObjects) {
            val currentMessage = SmsMessage.createFromPdu(
                messageObj as ByteArray,
                bundle["format"] as String
            )
            val senderNumber = currentMessage.displayOriginatingAddress
            val forwardNumber = sharedPreferences.getString("phoneNumber", "0")
            val forwardPrefix = "Received SMS from $senderNumber :- \n"
            val forwardContent = currentMessage.displayMessageBody
            if (currentMessage.messageClass == SmsMessage.MessageClass.CLASS_0) return
            smsManager.sendTextMessage(
                forwardNumber,
                null,
                forwardPrefix + forwardContent,
                null,
                null
            )
        }
    }

    companion object {
        val smsManager = SmsManager.getDefault()!!
    }
}

