package com.atom.bpc.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.atom.bpc.AtomBPCManager
import com.atom.bpc.demo.BPCDemo.Companion.SECRET_KEY
import com.atom.core.models.AtomConfiguration

class InitializeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initialize)

        val atomConfiguration = AtomConfiguration.Builder(
            SECRET_KEY
        ).build()

        findViewById<TextView>(R.id.txt_key).text = SECRET_KEY
        findViewById<Button>(R.id.btn_init).setOnClickListener {
            val bpcManager = AtomBPCManager.initialize(atomConfiguration)
            val bpcDemo = application as BPCDemo
            bpcDemo.atomBpcManager = bpcManager
            startActivity(Intent(this, AuthActivity::class.java))

        }


    }
}
