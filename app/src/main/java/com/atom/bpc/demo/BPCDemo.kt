package com.atom.bpc.demo

import android.app.Application
import com.atom.bpc.AtomBPCManager
import com.atom.core.models.AtomConfiguration
import com.atom.sdk.android.AtomManager

class BPCDemo : Application() {


    var atomManager: AtomManager? = null
    var atomBpcManager: AtomBPCManager? = null
    lateinit var atomConfiguration: AtomConfiguration
    override fun onCreate() {
        super.onCreate()

        val atomConfiguration = AtomConfiguration.Builder(
            "2a525ded0421ddbb6e166344bd514c1b"
        ).build()

        AtomManager.initialize(this, atomConfiguration) {
            atomManager = it

        }

    }

}