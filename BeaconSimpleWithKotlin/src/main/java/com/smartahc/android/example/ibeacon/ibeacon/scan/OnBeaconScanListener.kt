package com.smartahc.android.example.ibeacon.ibeacon.scan

import com.smartahc.android.example.ibeacon.ibeacon.bean.Beacon
import com.smartahc.android.example.ibeacon.ibeacon.bean.BeaconFilter

/**
 * Created by yuan on 25/08/2017.
 * beacon 扫描监听
 */
interface OnBeaconScanListener {

    /**
     * 回调结果
     */
    fun OnScanResult(beacon: Beacon)


    /**
     * 扫描时，消息回调
     */
    fun onScanErrorMsg(msg: String)

    /**
     * 过滤条件
     */
    fun getScanFilter(): BeaconFilter
}
