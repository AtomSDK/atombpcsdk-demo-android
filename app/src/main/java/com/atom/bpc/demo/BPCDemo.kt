package com.atom.bpc.demo

import android.app.Application
import com.atom.bpc.AtomBPCManager
import com.atom.core.models.AtomConfiguration
import com.atom.sdk.android.AtomManager

class BPCDemo : Application() {


    companion object {
        var SECRET_KEY:String = "YOUR SECRET KEY"
    }
    var atomManager: AtomManager? = null
    var atomBpcManager: AtomBPCManager? = null

    lateinit var atomConfiguration: AtomConfiguration
    override fun onCreate() {
        super.onCreate()

        val atomConfiguration = AtomConfiguration.Builder(
            SECRET_KEY
        ).build()

        AtomManager.initialize(this, atomConfiguration) {
            atomManager = it

        }


    }

}