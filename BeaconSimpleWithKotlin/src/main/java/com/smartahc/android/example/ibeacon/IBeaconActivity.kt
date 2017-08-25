package com.smartahc.android.example.ibeacon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import com.smartahc.android.example.ibeacon.ibeacon.IbeaconTooth
import com.smartahc.android.example.ibeacon.ibeacon.bean.Beacon
import com.smartahc.android.example.ibeacon.ibeacon.bean.BeaconFilter
import com.smartahc.android.example.ibeacon.ibeacon.scan.OnBeaconScanListener

class IBeaconActivity : AppCompatActivity(), OnBeaconScanListener {


    /**
     * 扫描到的结果
     */
    override fun OnScanResult(beacon: Beacon) {
        Log.v("beacon", "result > $beacon")
    }

    /**
     * 错误消息
     */
    override fun onScanErrorMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 过滤条件
     */
    override fun getScanFilter(): BeaconFilter = BeaconFilter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ibeacon)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = getString(R.string.ibeacon_title)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        // 开始扫描
        IbeaconTooth.getIbeacon().startBeacon(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        // 停止扫描
        IbeaconTooth.getIbeacon().stopBeacon()
    }

    override fun onBackPressed() {
        finish()
    }

}
