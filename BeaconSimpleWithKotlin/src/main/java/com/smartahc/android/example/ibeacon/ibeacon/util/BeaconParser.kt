package com.smartahc.android.example.ibeacon.ibeacon.util

import android.bluetooth.BluetoothDevice
import android.util.Log
import com.smartahc.android.example.ibeacon.ibeacon.bean.Beacon
import kotlin.experimental.and

/**
 * Created by yuan on 25/08/2017.
 * beacon 数据解析类
 */
class BeaconParser {

    /**
     * 静态函数
     */
    companion object {

        val TAG: String = "beacon"

        fun parse(device: BluetoothDevice?, rssi: Int, data: ByteArray): Beacon {
            val beacon = Beacon()
            beacon.rssi = rssi
            device.let {
                it?.name?.let {
                    // 设备名称
                    beacon.name = it
                }
                it?.address?.let {
                    // 设备 mac 地址
                    beacon.mac = it
                }
            }
            // parse data
            if (data.isNotEmpty()) {
                parseData(beacon, data)
            } else {
                Log.e(TAG, "data >> is null")
            }
            return beacon
        }

        /**
         * 解析 beacon 数据
         */
        private fun parseData(beacon: Beacon, scanData: ByteArray) {
            Log.e("beacon", "name >> ${beacon.name}")
            var startByte = 2
            while (startByte <= 5) {
                if (scanData[startByte + 2].toInt() and 0xff == 0x02 && scanData[startByte + 3].toInt() and 0xff == 0x15) {
                    // beacon 设备
                    val major = (scanData[startByte + 20] and 0xff.toByte()) * 0x100 + (scanData[startByte + 21] and 0xff.toByte())
                    beacon.major = major
                    val minor = (scanData[startByte + 22] and 0xff.toByte()) * 0x100 + (scanData[startByte + 23] and 0xff.toByte())
                    beacon.minor = minor
                    beacon.txPower = scanData[startByte + 24].toInt()
                    break
                }
                startByte++
            }
            val uuidBytes = ByteArray(16)
            System.arraycopy(scanData, startByte + 4, uuidBytes, 0, 16)
            parseUUID(beacon, uuidBytes)
        }

        /**
         * 解析 uuid 数据
         */
        private fun parseUUID(beacon: Beacon, uuidBytes: ByteArray) {
            val hexString = bytesToHexString(uuidBytes)
            Log.v(TAG, hexString)
            val sb = StringBuilder()
            if (hexString.isNullOrEmpty()) {
                hexString?.let {
                    sb.append(it.substring(0, 8))
                    sb.append("-")
                    sb.append(it.substring(8, 12))
                    sb.append("-")
                    sb.append(it.substring(12, 16))
                    sb.append("-")
                    sb.append(it.substring(16, 20))
                    sb.append("-")
                    sb.append(it.substring(20, 32))
                    beacon.uuid = sb.toString()
                }
                Log.v(TAG, beacon.uuid)
            }
        }

        /**
         * byte 转 16 进制字符串
         */
        private fun bytesToHexString(src: ByteArray?): String? {
            val stringBuilder = StringBuilder("")
            if (src == null || src.isEmpty()) {
                return stringBuilder.toString()
            }
            for (aSrc in src) {
                val v = aSrc and 0xFF.toByte()
                val hv = Integer.toHexString(v.toInt())
                if (hv.length < 2) {
                    stringBuilder.append(0)
                }
                stringBuilder.append(hv)
            }
            return stringBuilder.toString()
        }


    }


}