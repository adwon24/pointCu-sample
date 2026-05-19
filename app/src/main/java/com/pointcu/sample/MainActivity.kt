package com.pointcu.sample

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adwon.pointcu.Point4u
import com.pointcu.sample.common.BaseActivity
import com.pointcu.sample.fragment.SamplePoint4UFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED // 전체 화면 사용 안할 경우
            //insets // 전체 화면 사용 안할 경우 주석 처리
        }

        setTitle("MAIN")
        replaceFragment(containerViewId = R.id.container, fragment = SamplePoint4UFragment.newInstance(), backStack = false)

        // Point4u 포그라운드 노티피케이션 오른쪽 클릭 시 이동하기 위한 인텐트 검사
        val extraNavPoint4u = intent.getBooleanExtra(EXTRA_NAV_POINT4U, false)
        if (extraNavPoint4u) {
            Point4u.startPointCU(this@MainActivity, object : Point4u.OnPoint4UFinishListener {
                override fun onMoveInventory() {
                    Log.d(TAG, "onMoveInventory()")
                }
            })
        }
    }

    fun setTitle(title: String) {
        val tvTitle = findViewById<AppCompatTextView>(R.id.tv_title)
        tvTitle.text = title
    }

    companion object {
        const val EXTRA_NAV_POINT4U = "navigate_point4u"
    }
}