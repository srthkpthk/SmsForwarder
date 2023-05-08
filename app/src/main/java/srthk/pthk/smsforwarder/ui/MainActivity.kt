package srthk.pthk.smsforwarder.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import srthk.pthk.smsforwarder.R
import srthk.pthk.smsforwarder.services.ServiceBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(
            arrayOf(
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS
            ), 0
        )
        val sharedPreferences = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        phoneNumber(sharedPreferences)
    }

    private fun phoneNumber(sharedPreferences: SharedPreferences) {
        phoneNumberInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                sharedPreferences.edit().putString("phoneNumber", s.toString()).apply()
                startService(Intent(applicationContext, ServiceBinding::class.java))
            }
        })
    }
}
