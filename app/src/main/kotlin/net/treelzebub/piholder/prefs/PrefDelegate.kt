package net.treelzebub.piholder.prefs

import android.content.Context
import android.content.SharedPreferences
import net.treelzebub.knapsack.sharedprefs.KPrefs
import net.treelzebub.piholder.ContextHolder
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


inline fun <reified T: Any> withPref(key: String? = null) = PrefDelegate(T::class.java, key)

class PrefDelegate<T: Any>(
        private val clazz: Class<T>,
        private val key: String?
) : ReadWriteProperty<Any, T?> {

    private val prefs = ContextHolder.context.getSharedPreferences("piholder", Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor
        get() = prefs.edit()

    private fun key(thisRef: Any, property: KProperty<*>): String {
        return key ?: "pref_${thisRef.javaClass.simpleName}_${property.name}"
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return prefs.all[key(thisRef, property)] as T?
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        KPrefs.put(prefs, key(thisRef, property), value as Any)
    }
}