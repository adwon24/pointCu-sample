package com.pointcu.sample.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.edit
import com.adwon.pointcu.Point4UConfig
import com.adwon.pointcu.Point4u
import com.adwon.pointcu.Point4uAd
import com.adwon.pointcu.Point4uException
import com.adwon.pointcu.Point4uGender
import com.pointcu.sample.MainActivity
import com.pointcu.sample.MainActivity.Companion.EXTRA_NAV_POINT4U
import com.pointcu.sample.R
import com.pointcu.sample.common.BaseFragment
import com.pointcu.sample.common.TestUser

class SamplePoint4UFragment : BaseFragment(R.layout.fragment_sample_point4u) {
    private var user: TestUser = TestUser.USER_04

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 선택된 사용자 세팅
        activity?.let { act -> getUser(act) }?.let { user = it }
        findViewById<AppCompatTextView>(R.id.tv_user)?.text = user.code

        // Point4u 시작
        findViewById<AppCompatButton>(R.id.btn_start)?.setOnClickListener {
            activity?.let { act ->
                try {
                    Point4u.initialize(
                        Point4UConfig.Builder(activity = act as AppCompatActivity)
                            .setMemberId(user.code) // 사용자 id (필수)
                            .setAge(user.age.toInt()) // 나이 (필수)
                            .setEventIntent(Intent(act, MainActivity::class.java).apply {
                                putExtra(EXTRA_NAV_POINT4U, true)
                            })
                            .setGender(Point4uGender.parse(user.gender))
                            .build()
                    )

                    Point4u.startPointCU(act, object : Point4u.OnPoint4UFinishListener {
                        override fun onMoveInventory() {
                            Log.e("adwonSDK.sample", "[adwon sample] onMoveInventory()")
                        }
                    })
                } catch (e: Point4uException) {
                    Toast.makeText(act, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 사용자 선택
        findViewById<AppCompatButton>(R.id.btn_user)?.setOnClickListener {
            selectUser()
        }

        // activity 기반 룰렛 게임 구돋
        findViewById<AppCompatButton>(R.id.btn_start_roulette_act)?.setOnClickListener {
            activity?.let { act ->
                try {
                    Point4u.startGameRouletteActivity(act as AppCompatActivity, object : Point4u.OnPoint4UGameListener {
                        override fun onGameLoadFail(errorCode: Int, errorMessage: String) {
                            Log.d("adwon.sample", "[adwon sample] onGameLoadFail() errorCode : $errorCode, errorMessage : $errorMessage")
                        }

                        override fun onGameComplete(winPoint: Int) {
                            Log.d("adwon.sample", "[adwon sample] onGameComplete() winPoint : $winPoint")
                        }

                        override fun onGameClose() {
                            Log.d("adwon.sample", "[adwon sample] onGameClose()")
                        }
                    })
                } catch (e: Point4uException) {
                    Toast.makeText(act, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // fragment 기반 룰렛 게임 구동
        findViewById<AppCompatButton>(R.id.btn_start_roulette_frag)?.setOnClickListener {
            activity?.let { act ->
                try {
                    Point4u.startGameRouletteFragment(act as AppCompatActivity, R.id.container, object : Point4u.OnPoint4UGameListener {
                        override fun onGameLoadFail(errorCode: Int, errorMessage: String) {
                            Log.d("adwon.sample", "onGameLoadFail() errorCode : $errorCode, errorMessage : $errorMessage")
                        }

                        override fun onGameComplete(winPoint: Int) {
                            Log.d("adwon.sample", "onGameComplete() winPoint : $winPoint")
                        }

                        override fun onGameClose() {
                            Log.d("adwon.sample", "onGameClose()")
                        }
                    })
                } catch (e: Point4uException) {
                    Toast.makeText(act, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 오늘 뭐먹지 광고 구동
        findViewById<AppCompatButton>(R.id.btn_ad_eat)?.setOnClickListener {
            activity?.let { act ->
                Point4u.startPoint4uAdvertise(act as AppCompatActivity, Point4uAd.P4U_AD_EAT, adListener)
            }
        }
        // 재고 조회 광고 구동
        findViewById<AppCompatButton>(R.id.btn_ad_inventory)?.setOnClickListener {
            activity?.let { act ->
                Point4u.startPoint4uAdvertise(act as AppCompatActivity, Point4uAd.P4U_AD_INVENTORY, adListener)
            }
        }
        // 신상품 광고 구동
        findViewById<AppCompatButton>(R.id.btn_ad_new_product)?.setOnClickListener {
            activity?.let { act ->
                Point4u.startPoint4uAdvertise(act as AppCompatActivity, Point4uAd.P4U_AD_NEW_PRODUCT, adListener)
            }
        }
        // 예약 주문 광고 구동
        findViewById<AppCompatButton>(R.id.btn_ad_pre_order)?.setOnClickListener {
            activity?.let { act ->
                Point4u.startPoint4uAdvertise(act as AppCompatActivity, Point4uAd.P4U_AD_PRE_ORDER, adListener)
            }
        }
    }

    val adListener = object : Point4u.OnPoint4UAdListener {
        override fun onPoint4uAdShow(type: Point4uAd?) {
            Log.d("adwon.sample", "[adwon sample] onPoint4uAdShow() $type")
        }

        override fun onPoint4uAdFail(type: Point4uAd?) {
            Log.d("adwon.sample", "[adwon sample] onPoint4uAdFail() $type")
        }

        override fun onPoint4uAdClose(type: Point4uAd?) {
            Log.d("adwon.sample", "[adwon sample] onPoint4uAdClose() $type")
        }

        override fun onPoint4uAdEarned(type: Point4uAd?) {
            Log.d("adwon.sample", "[adwon sample] onPoint4uAdEarned() $type")
        }

        override fun onPoint4uAdClick(type: Point4uAd?) {
            Log.d("adwon.sample", "[adwon sample] onPoint4uAdClick() $type")
        }
    }

    private fun selectUser() {
        activity?.let {
            val dialog = AlertDialog.Builder(it)
            dialog.setTitle("테스트 로그인 사용자 선택")
            val arrayIds = arrayOf(
                TestUser.USER_01.code, TestUser.USER_02.code, TestUser.USER_03.code,
                TestUser.USER_04.code, TestUser.USER_05.code, TestUser.USER_06.code,
                TestUser.USER_07.code, TestUser.USER_08.code, TestUser.USER_09.code,
                TestUser.USER_10.code, TestUser.USER_11.code, TestUser.USER_12.code,
                TestUser.USER_13.code, TestUser.USER_14.code,
            )
            dialog.setItems(
                arrayIds
            ) { dialog, index ->
                dialog.dismiss()

                val newUser = TestUser.parse(arrayIds[index])
                if (newUser != user) {
                    activity?.let { act ->
                        setUser(act, newUser)
                        Point4u.clearUserData(act as AppCompatActivity) // TODO 사용자 변경 시 Point4u 로컬 데이터 초기화
                        Toast.makeText(act, "사용자 정보가 초기화됐습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                user = newUser
                findViewById<AppCompatTextView>(R.id.tv_user)?.text = user.code
            }
            dialog.show()
        }
    }

    companion object {
        const val PREF_NAME = "p4u.pref"
        const val KEY_USER = "userCode"

        var sharedPref: SharedPreferences? = null
        fun initPref(context: Context) {
            if (sharedPref == null) {
                sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            }
        }

        fun setUser(context: Context, user: TestUser) {
            initPref(context)
            sharedPref?.edit {
                putString(KEY_USER, user.code)
            }
        }

        fun getUser(context: Context): TestUser? {
            initPref(context)
            val value = sharedPref?.getString(KEY_USER, "")
            return TestUser.parse(value ?: "")
        }

        fun newInstance() = SamplePoint4UFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}