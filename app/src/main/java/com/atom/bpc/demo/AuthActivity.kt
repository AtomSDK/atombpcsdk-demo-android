package com.atom.bpc.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atom.sdk.android.VPNCredentials
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    var bpcDemo: BPCDemo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        bpcDemo = application as BPCDemo
        btn_auth.setOnClickListener {
            authentication()
        }

    }

    private fun navigateToConnectScreen() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun authentication() {
        if (!txt_uuid.text.isNullOrEmpty()) {
            bpcDemo?.atomManager?.setUUID(txt_uuid.text.toString())
            navigateToConnectScreen()
        } else {
            if (txt_vpn_username.text.isNullOrEmpty() || txt_vpn_password.text.isNullOrEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Username or password is empty",
                    Toast.LENGTH_LONG
                ).show()
                return
            } else {
                bpcDemo?.atomManager?.setVPNCredentials(
                    VPNCredentials(
                        txt_vpn_username.text.toString(),
                        txt_vpn_password.text.toString()
                    )
                )

                navigateToConnectScreen()
            }

        }

    }
}
