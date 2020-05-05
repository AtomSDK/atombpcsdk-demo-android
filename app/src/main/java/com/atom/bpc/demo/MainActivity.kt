package com.atom.bpc.demo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.ViewAnimator
import androidx.appcompat.app.AppCompatActivity
import com.atom.bpc.demo.logger.Log
import com.atom.bpc.demo.logger.LogFragment
import com.atom.bpc.demo.logger.LogWrapper
import com.atom.bpc.demo.logger.MessageOnlyLogFilter
import com.atom.core.exceptions.AtomException
import com.atom.core.models.Country
import com.atom.core.models.Package
import com.atom.core.models.Protocol
import com.atom.sdk.android.AtomManager
import com.atom.sdk.android.ConnectionDetails
import com.atom.sdk.android.VPNProperties
import com.atom.sdk.android.VPNStateListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), VPNStateListener {
    lateinit var logWrapper: LogWrapper

    private var logFragment: LogFragment? = null
    override fun onConnecting() {
        runOnUiThread {
            connect.text = "Connecting"
        }

    }

    override fun onPacketsTransmitted(p0: String?, p1: String?) {

    }

    override fun onConnected() {
        runOnUiThread {
            connect.text = "Connected"
        }
    }

    override fun onConnected(p0: ConnectionDetails?) {

    }

    override fun onDialError(p0: AtomException?, p1: ConnectionDetails?) {
        Log.d("UTC", p0?.errorMessage)
    }

    override fun onDisconnected(p0: Boolean) {
        runOnUiThread {
            connect.text = "Connect"
        }

    }

    override fun onDisconnected(p0: ConnectionDetails?) {

    }

    override fun onRedialing(p0: AtomException?, p1: ConnectionDetails?) {

    }

    override fun onStateChange(p0: String?) {
        Log.d("state", p0)

    }

    override fun onUnableToAccessInternet(p0: AtomException?, p1: ConnectionDetails?) {
        Log.d("UTB", "internet connection is not working")
    }

    var countryList: MutableList<Country>? = null
    var protocolList: MutableList<Protocol>? = null
    var packageList: MutableList<Package>? = null
    var bpcDemo: BPCDemo? = null
    var packagesName = mutableListOf("Select Package")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bpcDemo = application as BPCDemo
        AtomManager.addVPNStateListener(this)

        initializeLogging()

        //populateProtocols()
        getPackages()

        connect.setOnClickListener {
            val currentVpnStatus = bpcDemo?.atomManager?.getCurrentVpnStatus(this)
            if (currentVpnStatus != null && currentVpnStatus.equals(
                    AtomManager.VPNStatus.CONNECTED,
                    true
                )
            ) {
                bpcDemo?.atomManager?.disconnect(this)
            } else {
                connectVpn()
            }
        }
    }

    private fun initializeLogging() {
        // Wraps Android's native log framework.
        logWrapper = LogWrapper()
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper)


        // Filter strips out everything except the message text.
        val msgFilter = MessageOnlyLogFilter()
        logWrapper.setNext(msgFilter)

        // On screen logging via a fragment with a TextView.

        logFragment = supportFragmentManager
            .findFragmentById(R.id.log_fragment) as LogFragment
        if (logFragment != null)
            msgFilter.setNext(logFragment?.getLogView())

        val output = findViewById<ViewAnimator>(R.id.sample_output)
        output.displayedChild = 1

    }

    private fun connectVpn() {
        val country = countryList?.get(countries.selectedItemPosition)
        val protocol = protocolList?.get(protocols.selectedItemPosition)
        if (country != null && protocol != null) {
            val vpnProperties: VPNProperties.Builder =
                VPNProperties.Builder(country, protocol)

            bpcDemo?.atomManager?.connect(this, vpnProperties.build())
        } else {
            Toast.makeText(
                applicationContext,
                "Country or protocol is missing",
                Toast.LENGTH_LONG
            ).show()
        }

    }


    private fun getPackages() {
        /*
        This function provides you the list of all packages from your inventory
         */
        packageList
        bpcDemo?.atomBpcManager?.getPackages({
            packages.visibility = View.VISIBLE
            packageList = it
            val names = it.map {
                it.name
            }.filterNotNull()
            packagesName.addAll(names)

            val adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, packagesName)


            packages.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0)
                        packageList?.get(position - 1)?.apply {

                            Log.e("time","time")
                            populateProtocolsByPackage(this)
                        }

                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // your code here
                }

            }
            packages.adapter = adapter

        }, {

        })


    }

    private fun populateProtocols() {
        bpcDemo?.atomBpcManager?.getProtocols({
            protocolList = it
            updateProtocolSpinner()
        }, {

        })

        protocols.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val protocol = protocolList?.get(position)
                if (packages.selectedItemPosition > 0) {
                    protocol?.apply {
                        packageList?.get(packages.selectedItemPosition - 1)
                            ?.let { selectedPackage ->
                                populateCountriesByPackageAndProtocol(
                                    selectedPackage,
                                    this
                                )

                            }
                    }
                } else {
                    val objectOfProtocol = protocolList?.first {
                        it.protocol == "UDP"
                    }
                    objectOfProtocol?.apply {
                        populateCountriesByProtocol(this)
                    }

                }


            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }

        }
    }

    private fun populateCountriesByProtocol(objectOfProtocol: Protocol) {
        objectOfProtocol.apply {
            bpcDemo?.atomBpcManager?.getCountriesByProtocol(objectOfProtocol, {
                countryList = it
                updateCountrySpinner()

            }, {
                Log.e("exc",it.errorMessage)
            })

        }


    }

    private fun updateProtocolSpinner() {
        val names = protocolList?.map {
            protocols.visibility = View.VISIBLE
            it.protocol
        }
        val adapter =
            ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_dropdown_item,
                names
            )
        protocols.adapter = adapter
    }


    private fun updateCountrySpinner() {
        countries.visibility = View.VISIBLE
        val names = countryList?.map {
            it.name
        }
        val adapter =
            ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_dropdown_item,
                names
            )
        countries.adapter = adapter
    }

    fun populateProtocolsByPackage(objectOfPackage: Package) {
        objectOfPackage.apply {
            bpcDemo?.atomBpcManager?.getProtocolsByPackage(objectOfPackage, {
                protocolList = it
                Log.e("time2","time2")
                updateProtocolSpinner()

            }, {
                Log.e("exc",it.errorMessage)

            })
        }

    }

    fun populateCountriesByPackageAndProtocol(
        objectOfPackage: Package,
        objectOfProtocol: Protocol
    ) {
        bpcDemo?.atomBpcManager?.getCountriesByPackageAndProtocol(objectOfPackage,
            objectOfProtocol, {
                countryList = it
                updateCountrySpinner()
            }, {
                Log.e("exc",it.errorMessage)
            })


    }

    override fun onDestroy() {
        AtomManager.removeVPNStateListener(this)
        super.onDestroy()
    }
}
