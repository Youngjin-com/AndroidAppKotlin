package com.jun.sample4_4_up

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jun.sample4_4_up.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

   val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val web1=binding.web
        web1.webViewClient = WebViewClient()
        web1.loadUrl("https://m.daum.net")
        web1.settings.javaScriptEnabled = true


        // 뒤로 가기 버튼 설정
        binding.back.setOnClickListener {
            web1.goBack()
        }

        // 홈으로 가기 버튼 설정
        binding.home.setOnClickListener {
            web1.loadUrl("https://m.daum.net")
        }

        // 새로고침 버튼 설정
        binding.reload.setOnClickListener {
            web1.reload()
        }

        // 앞으로 가기 버튼 설정
        binding.go.setOnClickListener {
            web1.goForward()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}