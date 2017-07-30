package net.treelzebub.piholder.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.treelzebub.piholder.R
import net.treelzebub.piholder.prefs.withPref

class MainActivity : PiHolderActivity() {

    companion object {
        fun reset(c: Context): Intent {
            return Intent(c, MainActivity::class.java).putExtra("reset", true)
        }
    }

    private var piholeUrl by withPref<String>("pihole_admin_panel_url")
    private val reset     by lazy { intent.getBooleanExtra("reset", false) }

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
        val click = {
            val txt = url.text.toString().trim()
            val toast: String
            if (txt.isNotBlank()) {
                piholeUrl = txt.maybePrependScheme()
                toast = piholeUrl!!
                go()
            } else {
                toast = "URL cannot be blank."
            }
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
        }

        url.setOnEditorActionListener {
            _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_GO
                    || (keyEvent?.action == KeyEvent.ACTION_DOWN
                    && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {
                click()
                true
            } else false
        }
        button.setOnClickListener { click() }
    }

    private fun String.maybePrependScheme(): String {
        val secure = "http://"
        return if(!startsWith(secure)) secure + this else this
    }
}
