package net.treelzebub.piholder.activity

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

open class PiHolderActivity : AppCompatActivity() {

    private var taps = 0
    override fun onBackPressed() {
        if (++taps == 1) {
            Toast.makeText(this, "Tap back again to exit.", Toast.LENGTH_SHORT).show()
        } else {
            finish()
            super.onBackPressed()
        }
    }
}