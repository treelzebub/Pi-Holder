package net.treelzebub.piholder.activity

import android.os.Bundle
import net.treelzebub.knapsack.extensions.startActivity

class MainActivity : PiHolderActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<PiHoleAdminActivity>()
        finish()
    }
}