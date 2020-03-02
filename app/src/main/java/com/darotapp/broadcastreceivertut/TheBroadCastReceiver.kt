package com.darotapp.broadcastreceivertut

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

class TheBroadCastReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {

//        if(ConnectivityManager.EXTRA_NETWORK.equals(intent?.action)){
//            Toast.makeText(context, "Extra Network", Toast.LENGTH_LONG).show()
//        }

        Toast.makeText(context, "BroadCast Received", Toast.LENGTH_LONG).show()


//        if(MainActivity().checkStatus()){
//            val connMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            MainActivity().stayOMobile()
//        }
//        else{
//            Toast.makeText(context, "No mobile data", Toast.LENGTH_LONG).show()
//        }

    }
}