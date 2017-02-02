package net.treelzebub.piholder.env

import android.app.ActivityManager
import kotlin.properties.Delegates

interface Env {

    val flavor: String
    val isDebug: Boolean
    val isBeta: Boolean
    val isProd: Boolean
    val isTest: Boolean
}

class CurrentEnv(clazz: Class<*>) : Env {

    override val flavor  = clazz.getField("FLAVOR").get(null) as String
    override val isDebug = clazz.getField("DEBUG").get(null) as Boolean
    override val isBeta  = "beta" in flavor
    override val isProd  = !isDebug && !isBeta
    override val isTest  = ActivityManager.isRunningInTestHarness()
}

object PiHolderEnv {
    var env by Delegates.notNull<Env>()
}