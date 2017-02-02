package net.treelzebub.piholder.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import net.treelzebub.piholder.R
import net.treelzebub.piholder.kotterknife.bindView

class PiHoleAdminActivity : WebViewActivity() {

    private val webview by bindView<WebView>(R.id.webview)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pihole_admin)
        webview.settings.javaScriptEnabled = true
    }

    override fun onResume() {
        super.onResume()
        webview.loadUrl("http://10.0.0.8/admin")
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