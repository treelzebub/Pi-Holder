package net.treelzebub.piholder

import android.app.Application

class PiHolderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PiHolderInit.init()

        //Firebase
        //...
    }
}