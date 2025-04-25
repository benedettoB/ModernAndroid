package com.benedetto.modernandroid.ui.view

import android.os.Build
import androidx.compose.runtime.Composable
import com.benedetto.modernandroid.ble.BluetoothScanner
import com.benedetto.modernandroid.ble.FindBluetoothDevices

@Composable
internal fun BleScreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        BluetoothScanner()
    } else {
        FindBluetoothDevices()
    }
}