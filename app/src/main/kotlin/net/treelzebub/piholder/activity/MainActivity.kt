package net.treelzebub.piholder.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import net.treelzebub.piholder.R
import net.treelzebub.piholder.prefs.withPref

class MainActivity : PiHolderActivity() {

    companion object {
        fun reset(c: Context): Intent {
            return Intent(c, MainActivity::class.java).putExtra("reset", true)
        }
    }

    private var piholeUrl by withPref<String>("pihole_admin_panel_url")

    private val reset by lazy { intent.getBooleanExtra("reset", false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (reset) piholeUrl = ""
        if (piholeUrl != null && !reset) {
            go()
        } else {
            setContentView(R.layout.activity_main)
            initViews()
        }
    }

    private fun go() {
        startActivity(PiHoleAdminActivity.get(this, piholeUrl!!))
        finish()
    }

    private fun initViews() {
        val urlEt = findViewById(R.id.url) as EditText
        val button = findViewById(R.id.button)

        fun click() {
            val txt = urlEt.text.toString().trim()
            if (txt.isNotBlank()) {
                piholeUrl = txt.maybePrepend()
                Toast.makeText(this, piholeUrl, Toast.LENGTH_SHORT).show()
                go()
            } else {
                Toast.makeText(this, "URL cannot be blank.", Toast.LENGTH_SHORT).show()
            }
        }

        urlEt.setOnEditorActionListener { editText, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_GO
                    || (keyEvent?.action == KeyEvent.ACTION_DOWN
                    && keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER)) {
                click()
                true
            } else false
        }
        button.setOnClickListener {
            click()
        }
    }

    private fun String.maybePrepend(): String {
        val scheme = "http://"
        return if (!this.startsWith(scheme)) {
            scheme + this
        } else this
    }

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
