package com.darotapp.broadcastreceivertut

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    val theBroadCastReceiver = TheBroadCastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val filter = IntentFilter(ConnectivityManager.EXTRA_NETWORK)
        registerReceiver(theBroadCastReceiver, filter)

        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netRequest = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        val pend = PendingIntent.getBroadcast(applicationContext, 200, Intent(this, TheBroadCastReceiver::class.java), PendingIntent.FLAG_ONE_SHOT)
        connMgr.requestNetwork(netRequest.build(), object:NetworkCallback(){
            override fun onLost(network: Network) {
                super.onLost(network)
                Log.i("Network", "not available")
                Toast.makeText(applicationContext, "network not available", Toast.LENGTH_LONG).show()
            }

            override fun onAvailable(network: Network) {
//                super.onAvailable(network)

                Log.i("Network", "available")
                Toast.makeText(applicationContext, "network available", Toast.LENGTH_LONG).show()
            }

        })
        val h = connMgr.isActiveNetworkMetered


//        val cm: ConnectivityManager
//        cm =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE)
//        val req = NetworkRequest.Builder()
//        req.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//        req.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
//        cm.requestNetwork(req.build(), object : NetworkCallback() {
//            override fun onAvailable(network: Network) { //here you can use bindProcessToNetwork
//            }
//        })

//        Toast.makeText(this, h.toString(), Toast.LENGTH_LONG).show()


    }


    fun sendBroadCastToo(view:View){
        val intent = Intent(this, TheBroadCastReceiver::class.java)
        sendBroadcast(intent)
    }

    fun getConnectionType(context: Context): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = 2
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = 1
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = 2
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = 1
                    }
                }
            }
        }
        return result
    }

        fun stayOMobile(){
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netRequest = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        val pend = PendingIntent.getBroadcast(applicationContext, 200, Intent(this, MainActivity::class.java), PendingIntent.FLAG_ONE_SHOT)
        connMgr.requestNetwork(netRequest.build(), object:NetworkCallback(){
            override fun onLost(network: Network) {
                super.onLost(network)
                Log.i("Network", "not available")
                Toast.makeText(applicationContext, "network not available", Toast.LENGTH_LONG).show()
            }

            override fun onAvailable(network: Network) {
//                super.onAvailable(network)
                ConnectivityManager.setProcessDefaultNetwork(network)

                Log.i("Network", "available")
                Toast.makeText(applicationContext, "network available", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun checkStatus():Boolean{

        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connMgr.isActiveNetworkMetered

    }


    override fun onStop() {
        super.onStop()
        unregisterReceiver(theBroadCastReceiver)
    }
}
