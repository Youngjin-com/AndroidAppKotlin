package com.jun.sample4_13_up

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jun.sample4_13_up.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var addPhotoButton: Button
    private lateinit var startPhotoButton: Button
    private lateinit var imageViewList: List<ImageView>
    private val imageList: MutableList<Uri> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

    //    addPhotoButton = findViewById(R.id.addPhotoButton)  <--- 뷰바인인 처리로 삭제
     //   startPhotoButton = findViewById(R.id.startPhotoButton) <--- 뷰바인인 처리로 삭제
        /* 이 화면에 나타나는 버튼의 버튼 ID와 연결하는 코드입니다. */

        imageViewList = listOf(
            findViewById(R.id.imageView1),
            findViewById(R.id.imageView2),
            findViewById(R.id.imageView3)
        )
        /* ID를 가진 이미지뷰들을 찾아 할당하는 작업을 하는 코드입니다. */


        binding.addPhotoButton.setOnClickListener {
            navigatePhotos()
        }
        /* addPhotoButton 버튼을 누르면 코드 하단에 있는
        navigatePhotos() 함수를 실행 하라는 것입니다.
        이미지 갤러리로 이동합니다. */



        binding.startPhotoButton.setOnClickListener {
            val intent = Intent(this, PhotoFrameActivity::class.java)
            imageList.forEachIndexed { index, uri ->
                intent.putExtra("photo$index", uri.toString())
            }
            intent.putExtra("photoListSize", imageList.size)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 500)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            500 -> {
                val selectedImageUri: Uri? = data?.data

                if (selectedImageUri != null) {
                    if (imageList.size == 3) {
                        Toast.makeText(this, "3장의 사진 선택이 완료 되었습니다.", Toast.LENGTH_LONG).show()
                        return
                    }

                    imageList.add(selectedImageUri)
                    imageViewList[imageList.size - 1].setImageURI(selectedImageUri)
                }
            }
        }
    }


}

/*

/* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): onActivityResult() 함수를
오버라이드하여 액티비티의 결과를 처리합니다.
requestCode는 요청 코드, resultCode는 액티비티의 실행 결과 코드, data는 결과 데이터를 나타냅니다

super.onActivityResult(requestCode, resultCode, data): 부모 클래스의 onActivityResult() 함수를
호출하여 기본 동작을 수행합니다.

if (resultCode != Activity.RESULT_OK) { return }: resultCode가 Activity.RESULT_OK가
아닌 경우 함수를 종료합니다.
이는 사용자가 액티비티에서 취소 또는 오류로 인해 선택을 취소한 경우를 처리하는 부분입니다.

when (requestCode) { 500 -> { ... } }: requestCode에 따라 분기하여 처리합니다.
여기서는 500인 경우를 처리합니다.

val selectedImageUri: Uri? = data?.data: data 인텐트에서 선택된 이미지의 URI를 가져옵니다.
선택된 이미지가 없는 경우 null일 수 있습니다.

if (selectedImageUri != null) { ... }: 선택된 이미지의 URI가 null이 아닌 경우를 처리합니다.

if (imageList.size == 3) { ... }: 이미지 리스트(imageList)의 크기가 3인 경우를 처리합니다.
이미지 리스트에 이미 3장의 사진이 있는 경우를 의미합니다.

imageList.add(selectedImageUri): 이미지 리스트에 선택된 이미지의 URI를 추가합니다.
imageViewList[imageList.size - 1].setImageURI(selectedImageUri): 이미지뷰 리스트(imageViewList)에서
마지막 위치의 이미지뷰에 선택된 이미지를 설정합니다.   */
 */

