package com.ssafy.forestkeeper

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.webkit.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    var cameraPath = ""
    var mWebViewImageUpload: ValueCallback<Array<Uri>>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        }
        else {
            val permissions: Array<String> = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
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

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                try{ mWebViewImageUpload = filePathCallback!!
                    var takePictureIntent : Intent?
                    takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if(takePictureIntent.resolveActivity(packageManager) != null){
                        var photoFile : File?
                        photoFile = createImageFile()
                        takePictureIntent.putExtra("PhotoPath",cameraPath)
                        if(photoFile != null){
                            cameraPath = "file:${photoFile.absolutePath}"
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile))
                        }
                        else takePictureIntent = null
                    }
                    val contentSelectionIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    contentSelectionIntent.type = "image/*"
                    var intentArray: Array<Intent?>
                    if(takePictureIntent != null) intentArray = arrayOf(takePictureIntent)
                    else intentArray = takePictureIntent?.get(0)!!
                    val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                    chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                    chooserIntent.putExtra(Intent.EXTRA_TITLE,"사용할 앱을 선택해주세요.")
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
                    launcher.launch(chooserIntent)
                } catch (e : Exception){ }
                return true
            }

            fun createImageFile(): File? {
                @SuppressLint("SimpleDateFormat")
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val imageFileName = "img_" + timeStamp + "_"
                val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                return File.createTempFile(imageFileName, ".jpg", storageDir)
            }

            val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intent = result.data
                    if(intent == null){ //바로 사진을 찍어서 올리는 경우
                        val results = arrayOf(Uri.parse(cameraPath))
                        mWebViewImageUpload!!.onReceiveValue(results!!)
                    } else{ //사진 앱을 통해 사진을 가져온 경우
                        val results = intent!!.data!!
                        mWebViewImageUpload!!.onReceiveValue(arrayOf(results!!))
                    }
                } else{ //취소 한 경우 초기화
                    mWebViewImageUpload!!.onReceiveValue(null)
                    mWebViewImageUpload = null
                }
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