package com.atom.bpc.demo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.atom.bpc.demo.BPCDemo.Companion.SECRET_KEY
import kotlinx.android.synthetic.main.activity_initialize.*

class InitializeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initialize)

        findViewById<TextView>(R.id.txt_key).text = SECRET_KEY

        btn_init.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }
}
