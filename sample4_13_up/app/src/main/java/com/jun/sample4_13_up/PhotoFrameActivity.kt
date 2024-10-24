package com.jun.sample4_13_up

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PhotoFrameActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Uri>()
    private var currentPosition = 0
    private var timerHandler: Handler? = null

    private lateinit var photoImageView: ImageView
    private lateinit var backgroundPhotoImageView: ImageView

    /*
private val photoList = mutableListOf<Uri>(): photoList는 Uri 객체를 담는 가변 리스트입니다.
이 리스트는 액티비티에 표시될 사진들의 URI를 저장합니다.

private var currentPosition = 0: currentPosition은 현재 표시되고 있는 사진의 인덱스를 나타냅니다.
초기값은 0이며, photoList에서 현재 사진을 선택하는 데 사용됩니다.

private var timerHandler: Handler? = null: timerHandler는 Handler 객체를 담는 변수입니다.
이 변수는 타이머 기능을 제어하는 데 사용됩니다. 초기값은 null로 설정되어 있습니다.

private lateinit var photoImageView: ImageView: photoImageView는 ImageView 객체를 나타냅니다.
이 변수는 액티비티에서 현재 사진을 표시하는 데 사용됩니다.

private lateinit var backgroundPhotoImageView: ImageView: backgroundPhotoImageView는
ImageView 객체를 나타냅니다. 액티비티에서 배경으로 사용될 사진을 표시하는 데 사용됩니다.

*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_photo_frame)

        photoImageView = findViewById(R.id.ImageView)
        backgroundPhotoImageView = findViewById(R.id.PhotoImageView)

        getPhotoUriFromIntent()



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /*
super.onCreate(savedInstanceState): 부모 클래스의 onCreate() 메서드를 호출하여 초기 설정을 수행합니다.

setContentView(R.layout.activity_photoframe): 액티비티에 표시될 레이아웃을 설정합니다.
activity_photoframe 레이아웃이 사용됩니다.

photoImageView = findViewById(R.id.ImageView): photoImageView 변수에 액티비티에서 R.id.ImageView로
정의된 이미지뷰를 연결합니다.

backgroundPhotoImageView = findViewById(R.id.PhotoImageView): backgroundPhotoImageView 변수에
액티비티에서 R.id.PhotoImageView로 정의된 이미지뷰를 연결합니다.

getPhotoUriFromIntent(): getPhotoUriFromIntent() 메서드를 호출하여 인텐트로부터 사진 URI(경로)를 가져옵니다.

*/



    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0 until size) {
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it))
            }

        }
    }


    /*
val size = intent.getIntExtra("photoListSize", 0): 인텐트에서 "photoListSize"라는 이름의 정수형(extra)
값을 가져옵니다. 이 값은 photoList에 저장된 사진 URI의 개수를 나타냅니다.
가져오는 과정에서 값이 존재하지 않으면 기본값으로 0을 사용합니다.

for (i in 0 until size): 0부터 size 직전까지의 범위를 반복합니다. size는 photoList에 저장될 사진 URI의 개수입니다.

intent.getStringExtra("photo$i")?.let { ... }: 인텐트에서 "photo$i"라는 이름의 문자열(extra) 값을 가져옵니다.
여기서 $i는 반복문에서 현재의 인덱스를 나타냅니다. 가져온 값이 null이 아닐 경우에만 수행합니다.

photoList.add(Uri.parse(it)): 가져온 문자열 값을 Uri.parse() 메서드를 사용하여 Uri 객체로 변환하고,
이를 photoList에 추가합니다.
*/


    private val timerRunnable = object : Runnable {
        override fun run() {
            val current = currentPosition
            val next = if (photoList.size <= currentPosition + 1) 0 else currentPosition + 1

            backgroundPhotoImageView.setImageURI(photoList[current])

            photoImageView.alpha = 0f
            photoImageView.setImageURI(photoList[next])

            photoImageView.animate()
                .alpha(1.0f)
                .setDuration(1000)
                .start()

            currentPosition = next

            startTimer()
        }
    }

    /*
    run() 메서드: Runnable 인터페이스를 구현한 익명 클래스로, run() 메서드를 오버라이드하여 구현합니다.
    이 메서드는 타이머에 의해 주기적으로 실행됩니다.

    val current = currentPosition: 현재 사진의 인덱스를 저장합니다.

    val next = if (photoList.size <= currentPosition + 1) 0 else currentPosition + 1: 다음 사진의
    인덱스를 계산합니다. 만약 현재 사진이 마지막 사진이라면 다음 사진은 첫 번째 사진이 되도록 설정합니다.

    backgroundPhotoImageView.setImageURI(photoList[current]): backgroundPhotoImageView에 현재 사진의
    URI를 설정하여 배경 이미지를 변경합니다.

    photoImageView.alpha = 0f: photoImageView의 투명도를 0으로 설정하여 페이드 인 효과를 준비합니다.

    photoImageView.setImageURI(photoList[next]): photoImageView에 다음 사진의 URI를 설정하여
    이미지를 변경합니다.

    photoImageView.animate()...: photoImageView를 페이드 인 애니메이션과 함께 보여줍니다.
    애니메이션의 지속 시간은 1초로 설정되어 있습니다.
    currentPosition = next: currentPosition을 다음 사진의 인덱스로 업데이트합니다.

    startTimer(): 다음 사진 전환을 위해 startTimer() 메서드를 호출하여 타이머를 다시 시작합니다.
    */


    private fun startTimer() {
        timerHandler?.removeCallbacks(timerRunnable)
        timerHandler = Handler(Looper.getMainLooper())
        timerHandler?.postDelayed(timerRunnable, 3000)
    }

    /*
    timerHandler?.removeCallbacks(timerRunnable): 이전에 실행되었던 타이머 작업을 제거합니다.
    이전 작업이 남아 있다면 제거하여 중복 실행을 방지합니다.

    timerHandler = Handler(Looper.getMainLooper()): 메인 스레드의 루퍼(Looper)를 사용하여
    새로운 핸들러(Handler) 인스턴스를 생성합니다. 이 핸들러를 통해 타이머 작업을 스케줄링할 수 있습니다.

    timerHandler?.postDelayed(timerRunnable, 3000): timerRunnable을 3초 후에 실행하도록
    타이머 작업을 예약합니다. 이를 통해 사진 전환과 애니메이션을 주기적으로 수행할 수 있습니다.
    */


    override fun onStart() {
        super.onStart()
        startTimer()
    }

    /*
    startTimer() 메서드를 호출하는 부분은 액티비티가 화면에 표시되고 사용자와 상호작용하기 직전에
    타이머를 시작하도록 하는 역할을 합니다. 이로써 액티비티가 시작될 때마다 사진 전환 타이머가 실행되고,
    사진이 주기적으로 전환되는 동작이 시작됩니다. */


    override fun onStop() {
        super.onStop()
        timerHandler?.removeCallbacks(timerRunnable)
    }

    /*
    timerHandler?.removeCallbacks(timerRunnable)은 현재 실행 중인 타이머 작업을 제거하는 역할을 합니다.
    이를 통해 액티비티가 화면에서 사라질 때 타이머 작업을 중지시킵니다. 타이머 작업을 중지함으로써
    사진 전환과 애니메이션이 일시적으로 멈추게 되는 것입니다. */



    override fun onDestroy() {
        super.onDestroy()
        timerHandler?.removeCallbacks(timerRunnable)
    }

    /*
timerHandler?.removeCallbacks(timerRunnable)은 현재 실행 중인 타이머 작업을 제거하는 역할을 합니다.
이를 통해 액티비티가 완전히 소멸될 때 타이머 작업을 중지시킵니다.
타이머 작업을 중지함으로써 메모리 누수를 방지하고 액티비티와 관련된 리소스를 정리하는 것입니다. */












}