package com.ssafy.forestkeeper

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    val MY_PERMISSION_ACCESS_ALL = 100

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        }
        else {
            val permissions: Array<String> = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, permissions, 0)
        }

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_DENIED) { //백그라운드 위치 권한 확인
            //위치 권한 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                0
            )
        }

        var webView: WebView = findViewById(R.id.webview)
        val webSettings = webView.settings

        webSettings.javaScriptEnabled = true
        webSettings.loadsImagesAutomatically = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.useWideViewPort = true
        webSettings.safeBrowsingEnabled = false
        webSettings.setGeolocationEnabled(true)
        webSettings.domStorageEnabled = true
        webView.webViewClient = SslWebViewConnect()

        //ssl 인증이 없는 경우 해결을 위한 부분
        webView.webChromeClient = object : WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onPermissionRequest(request: PermissionRequest) {
                request.grant(request.resources)
            }

            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                callback: GeolocationPermissions.Callback
            ) {
                super.onGeolocationPermissionsShowPrompt(origin, callback)
                callback.invoke(origin, true, false)
            }
        }
        webView.loadUrl("https://k6a306.p.ssafy.io/")
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