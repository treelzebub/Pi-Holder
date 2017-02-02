package net.treelzebub.piholder

import android.content.Context
import kotlin.properties.Delegates


object ContextHolder {

    var context by Delegates.notNull<Context>()
}