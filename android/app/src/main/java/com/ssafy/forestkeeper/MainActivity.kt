package com.ssafy.forestkeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var webView: WebView = findViewById(R.id.webview)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.google.com/")
    }

    override fun onBackPressed() {
        var webView: WebView = findViewById(R.id.webview)
        if (webView.canGoBack()){
            webView.goBack()
        }
        else{
            finish()
        }
    }
}