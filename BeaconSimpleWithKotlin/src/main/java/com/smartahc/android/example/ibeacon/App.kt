package com.smartahc.android.example.ibeacon

import android.app.Application
import com.smartahc.android.example.ibeacon.ibeacon.IbeaconTooth

/**
 * Created by yuan on 25/08/2017.
 * 初始化 ibeacon
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        IbeaconTooth.init(this)
    }
}