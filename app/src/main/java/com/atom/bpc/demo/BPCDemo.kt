package com.atom.bpc.demo

import android.app.Application
import com.atom.bpc.AtomBPCManager
import com.atom.bpc.BPCInitProvider
import com.atom.core.models.AtomConfiguration
import com.atom.sdk.android.AtomManager


class BPCDemo : Application() {

    companion object {
        var SECRET_KEY: String = ""

    }

    var atomManager: AtomManager? = null
    var atomBpcManager: AtomBPCManager? = null


    lateinit var atomConfiguration: AtomConfiguration

    override fun onCreate() {
        super.onCreate()

        // Start provider dependency for BPC SDK
        BPCInitProvider.start(this)

        atomConfiguration = AtomConfiguration.Builder(
            SECRET_KEY
        ).build()


        //Initialize Atom Manager
        AtomManager.initialize(this, atomConfiguration) {
            atomManager = it
        }

        //Initialize Atom BPC Manager
        atomBpcManager = AtomBPCManager.initialize(atomConfiguration)
    }


}