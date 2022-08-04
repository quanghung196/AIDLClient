package com.example.aidlclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.aidlclient.databinding.ActivityMainBinding
import com.example.aidlserver.IAIDLColorInterface
import java.util.*


class MainActivity : AppCompatActivity(), Observer {

    private var iADILColorService: IAIDLColorInterface? = null
    private lateinit var mBinding: ActivityMainBinding

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            iADILColorService = IAIDLColorInterface.Stub.asInterface(iBinder)
            Log.d(TAG, "Remote config Service Connected!!")
        }

        override fun onServiceDisconnected(componentName: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.activity = this

        bindService()
        addObserver()
    }

    private fun addObserver() {
        ObservableObject.instance.addObserver(this)
    }

    private fun bindService() {
        val intent = Intent(INTENT_SERVICE)
        intent.setPackage(PACKAGE_NAME)
        bindService(intent, mConnection, BIND_AUTO_CREATE)
        Log.d(TAG, "bind service called")
    }

    private fun getColor(view: View) {
        try {
            val color = iADILColorService!!.color
            view.setBackgroundColor(color)
        } catch (e: RemoteException) {
        }
    }

    fun onButtonClicked(view: View) {
        getColor(view = view)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val INTENT_SERVICE = "AIDLColorService"
        private const val PACKAGE_NAME = "com.example.aidlserver"
    }

    override fun update(p0: Observable?, data: Any?) {
        mBinding.button.setBackgroundColor(data as Int)
    }
}