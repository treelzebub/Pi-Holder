package net.treelzebub.piholder

import android.app.Application
import net.treelzebub.piholder.env.CurrentEnv
import net.treelzebub.piholder.env.PiHolderEnv

class PiHolderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PiHolderEnv.env = CurrentEnv(BuildConfig::class.java)
    }
}