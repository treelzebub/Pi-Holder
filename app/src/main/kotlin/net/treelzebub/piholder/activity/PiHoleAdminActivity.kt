package net.treelzebub.piholder.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_pihole_admin.*
import net.treelzebub.knapsack.extensions.startActivity
import net.treelzebub.piholder.R
import net.treelzebub.piholder.env.PiHolderEnv

class PiHoleAdminActivity : PiHolderActivity() {

    companion object {
        fun get(c: Context, url: String): Intent {
            return Intent(c, PiHoleAdminActivity::class.java).putExtra("url", url)
        }
    }

    private val url by lazy { intent.getStringExtra("url") }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pihole_admin)
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClient()

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
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (!PiHolderEnv.env.isProd) {
            menuInflater.inflate(R.menu.menu_debug, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.debug_reset -> {
                startActivity(MainActivity.reset(this))
                finish()
            }
            R.id.crash -> throw RuntimeException("Purposeful crash!")
        }
        return super.onOptionsItemSelected(item)
    }
}