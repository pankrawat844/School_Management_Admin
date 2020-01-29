package com.app.schoolmanagementteacher

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_webview.*

class WebviewActivity : AppCompatActivity() {
    internal var progressBar: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        progressBar = ProgressDialog(this)
        // progressBar.setTitle("Loading");
        progressBar!!.setMessage("Please Wait...")
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()

        webView.settings.setAppCacheEnabled(false)
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.pluginState = WebSettings.PluginState.ON
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.webViewClient = Webviewclient()
        webView.loadUrl(intent.getStringExtra("url"))
    }

    inner class Webviewclient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            progressBar?.show()

        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            super.onReceivedSslError(view, handler, error)
            progressBar?.dismiss()

        }

        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
            super.onReceivedHttpError(view, request, errorResponse)
            progressBar?.dismiss()

        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar?.dismiss()

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun setUpWebViewDefaults(webView: WebView) {
        val settings = webView.settings
        // Enable Javascript
        settings.javaScriptEnabled = true
        // Use WideViewport and Zoom out if there is no viewport defined
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        // Enable pinch to zoom without the zoom buttons
        settings.builtInZoomControls = true
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.displayZoomControls = false
        }
        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        // We set the WebViewClient to ensure links are consumed by the WebView rather
        // than passed to a browser if it can
        webView.webViewClient = WebViewClient()
    }
    // @Override
    // public void onBackPressed() {
    // if(webView.canGoBack())
    // webView.goBack();
    // else
    // super.onBackPressed();
    // }

}
