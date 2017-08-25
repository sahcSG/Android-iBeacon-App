package com.smartahc.android.example.ibeacon.ibeacon

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.os.Build
import android.util.Log
import com.smartahc.android.example.ibeacon.ibeacon.scan.BeaconBleCallback
import com.smartahc.android.example.ibeacon.ibeacon.scan.BeaconLeCallback
import com.smartahc.android.example.ibeacon.ibeacon.scan.OnBeaconScanListener

/**
 * Created by yuan on 25/08/2017.
 * ibeacon 工具类
 * 1.ble 开始扫描和停止扫描
 */
class IbeaconTooth constructor(mContext: Context) {

    /**
     * init
     */
    private val cxt: Context = mContext
    private val manager: BluetoothManager = cxt.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val adapter: BluetoothAdapter = manager.adapter
    private var scanner: BluetoothLeScanner? = null
    private var isScan: Boolean = false
    private val TAG = "beacon"
    // scan callback
    private var bleCallback: BeaconBleCallback? = null
    private var leCallback: BeaconLeCallback? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scanner = adapter.bluetoothLeScanner
        }
    }

    companion object {

        private var instance: IbeaconTooth? = null

        fun init(context: Context) {
            if (instance == null) {
                synchronized(IbeaconTooth::class.java) {
                    if (instance == null) {
                        instance = IbeaconTooth(context)
                    }
                }
            }
        }

        fun getIbeacon(): IbeaconTooth {
            if (instance == null) {
                throw IllegalArgumentException("ibeantooth is null , please use init() method")
            }
            return instance as IbeaconTooth
        }

    }

    /**
     * 开始扫描
     */
    fun startBeacon(onBeaconScanListener: OnBeaconScanListener) {
        if (isScan) {
            Log.d(TAG, "already start scan")
            onBeaconScanListener.onScanErrorMsg("start scan already")
        } else {
            if (scanner == null) {
                bleCallback = BeaconBleCallback(onBeaconScanListener)
                adapter.startLeScan(bleCallback)
            } else {
                // android 5.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    leCallback = BeaconLeCallback(onBeaconScanListener)
                    scanner?.startScan(leCallback)
                }
            }
        }
    }

    /**
     * 停止扫描
     */
    fun stopBeacon() {
        if (isScan) {
            // stop scan
            if (scanner == null) {
                if (bleCallback != null) {
                    adapter.stopLeScan(bleCallback)
                }
            } else {
                if (leCallback != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        scanner?.stopScan(leCallback)
                    }
                }
            }
        } else {
            Log.d(TAG, "already stop scan")
        }
    }


}