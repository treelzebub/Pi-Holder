package net.treelzebub.piholder.prefs

import android.content.Context
import net.treelzebub.knapsack.sharedprefs.KPrefs
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T: Any> Context.withPref(key: String? = null) = PrefDelegate<T>(this, key)

class PrefDelegate<T: Any>(
        context: Context,
        private val key: String?
) : ReadWriteProperty<Any, T?> {

    private val prefs by lazy {
        context.applicationContext.getSharedPreferences("piholder", Context.MODE_PRIVATE)
    }

    private fun key(thisRef: Any, property: KProperty<*>): String {
        return key ?: "pref_${thisRef.javaClass.simpleName}_${property.name}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return prefs.all[key(thisRef, property)] as T?
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        KPrefs.put(prefs, key(thisRef, property), value as Any)
    }
}