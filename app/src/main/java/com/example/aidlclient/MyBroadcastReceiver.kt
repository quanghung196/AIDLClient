package com.example.aidlclient

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private const val COLOR_VALUE = "color"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val color = intent?.getIntExtra(COLOR_VALUE, 0)
        ObservableObject.instance.updateValue(color);
    }
}