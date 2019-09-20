package com.atom.bpc.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.atom.bpc.AtomBPCManager
import com.atom.core.models.AtomConfiguration

class InitializeActivity : AppCompatActivity() {

    private val key: String = "2a525ded0421ddbb6e166344bd514c1b"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initialize)

        val atomConfiguration = AtomConfiguration.Builder(
            key
        ).build()

        findViewById<TextView>(R.id.txt_key).text = key
        findViewById<Button>(R.id.btn_init).setOnClickListener {

            AtomBPCManager.initialize(atomConfiguration, {
                val bpcDemo = application as BPCDemo
                bpcDemo.atomBpcManager = it
                startActivity(Intent(this, AuthActivity::class.java))

            }, {

            })

        }


    }
}
