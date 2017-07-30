package net.treelzebub.piholder

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import net.treelzebub.piholder.env.CurrentEnv
import net.treelzebub.piholder.env.PiHolderEnv

class PiHolderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PiHolderEnv.env = CurrentEnv(BuildConfig::class.java)
        if (PiHolderEnv.env.isProd) {
            Fabric.with(this, Crashlytics())
        }
    }
}