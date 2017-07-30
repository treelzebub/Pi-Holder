package net.treelzebub.piholder

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import net.treelzebub.piholder.env.CurrentEnv
import net.treelzebub.piholder.env.PiHolderEnv
import net.treelzebub.piholder.prefs.withPref
import java.util.*

class PiHolderApp : Application() {

    private var userId by withPref<String>("piholder_anonymous_user_identifier")

    override fun onCreate() {
        super.onCreate()
        PiHolderEnv.env = CurrentEnv(BuildConfig::class.java)
        if (PiHolderEnv.env.isProd) {
            initFabric()
        }
    }

    private fun initFabric() {
        Fabric.with(this, Crashlytics())
        if (userId == null) {
            userId = UUID.randomUUID().toString()
        }
        Crashlytics.setUserIdentifier(userId)
    }
}