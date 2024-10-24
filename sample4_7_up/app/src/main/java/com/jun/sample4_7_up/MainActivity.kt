package com.jun.sample4_7_up

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jun.sample4_7_up.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val builder = AlertDialog.Builder(this@MainActivity)

        builder.setTitle(("타이틀"))
            .setMessage("메세지")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->

                    //'확인' 버튼을 누르면 실행되는 기능
                    Toast.makeText(this@MainActivity, "확인을 눌렀네 ?", Toast.LENGTH_SHORT).show()

                })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, id ->

                    //'취소' 버튼을 누르면 실행되는 기능
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://m.daum.net"))
                    startActivity(intent)

                })

        builder.show()




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}