package net.treelzebub.piholder.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toast
import net.treelzebub.knapsack.extensions.startActivity
import net.treelzebub.piholder.R
import net.treelzebub.piholder.env.PiHolderEnv
import net.treelzebub.piholder.kotterknife.bindView
import android.webkit.WebViewClient



class PiHoleAdminActivity : WebViewActivity() {

    companion object {
        fun get(c: Context, url: String): Intent {
            return Intent(c, PiHoleAdminActivity::class.java).putExtra("url", url)
        }
    }

    private val webview by bindView<WebView>(R.id.webview)
    private val url     by lazy { intent.getStringExtra("url") }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pihole_admin)
        webview.settings.javaScriptEnabled = true
        webview.setWebViewClient(WebViewClient())

        if (PiHolderEnv.env.isProd) supportActionBar!!.hide()

        if (url == null) {
            Toast.makeText(this, "URL cannot be blank.", Toast.LENGTH_SHORT).show()
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        webview.loadUrl(url)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (!PiHolderEnv.env.isProd) {
            menuInflater.inflate(R.menu.menu_debug, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.debug_reset) {
            startActivity(MainActivity.reset(this))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}