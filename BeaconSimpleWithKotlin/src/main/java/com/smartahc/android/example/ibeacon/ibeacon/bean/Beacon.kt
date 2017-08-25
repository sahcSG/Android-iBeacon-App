package com.smartahc.android.example.ibeacon.ibeacon.bean

/**
 * Created by yuan on 25/08/2017.
 * beacon 实体
 */
data class Beacon constructor(var name: String = "",
                              var major: Int = 0,
                              var minor: Int = 0,
                              var uuid: String = "",
                              var mac: String = "",
                              var txPower: Int = 0,
                              var rssi: Int = 0)

/**
 * beacon filter
 */
data class BeaconFilter(var name: String = "",
                        var mac: String = "")