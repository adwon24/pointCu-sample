package com.pointcu.sample.advertise

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.adwon.pointcu.Point4u
import com.adwon.pointcu.Point4uAd
import com.adwon.pointcu.advertise.AdWonAdView
import com.pointcu.sample.R

class AdvertiseDialogFragment : DialogFragment() {

    private lateinit var advertiseView: AdWonAdView
    private lateinit var loadingContainer: View

    private var listener: Point4u.OnPoint4UAdListener? = null

    private val adType: Point4uAd by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(
                ARG_AD_TYPE,
                Point4uAd::class.java
            )!!
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getSerializable(
                ARG_AD_TYPE
            ) as Point4uAd
        }
    }

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_advertise, null)

        // 광고 뷰 설정
        advertiseView = view.findViewById(R.id.advertiseView)
        advertiseView.setWindowProvider { // TODO 정확한 광고 로딩 시점을 알기 위한 필수 세팅 - 주석 참조
            dialog?.window
        }
        // 로딩
        loadingContainer = view.findViewById(R.id.loadingContainer)


        return AlertDialog.Builder(requireContext())
            .setView(view)
            .create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            resources.getDimensionPixelSize(R.dimen.ad_dialog_width), // 300dp
            resources.getDimensionPixelSize(R.dimen.ad_dialog_height) // 250dp
        )

        // 광고 리스너 등록
        advertiseView.showAdvertise(
            adType,
            object : Point4u.OnPoint4UAdListener {

                override fun onPoint4uAdShow(type: Point4uAd?) {
                    // 광고가 실제 표시되는 순간 로딩 제거
                    loadingContainer.visibility = View.GONE

                    listener?.onPoint4uAdShow(type)
                }

                override fun onPoint4uAdFail(type: Point4uAd?) {
                    loadingContainer.visibility = View.GONE

                    listener?.onPoint4uAdFail(type)
                    dismissAllowingStateLoss()
                }

                override fun onPoint4uAdClose(type: Point4uAd?) {
                    loadingContainer.visibility = View.GONE

                    listener?.onPoint4uAdClose(type)
                    dismissAllowingStateLoss()
                }

                override fun onPoint4uAdEarned(type: Point4uAd?) {
                    listener?.onPoint4uAdEarned(type)
                }

                override fun onPoint4uAdClick(type: Point4uAd?) {
                    listener?.onPoint4uAdClick(type)

                    // TODO 필요 시 닫기
                    dismissAllowingStateLoss()
                }
            }
        )
    }

    fun setOnPoint4UAdListener(
        listener: Point4u.OnPoint4UAdListener
    ) {
        this.listener = listener
    }

    companion object {

        private const val ARG_AD_TYPE = "ARG_AD_TYPE"

        fun newInstance(
            adType: Point4uAd
        ): AdvertiseDialogFragment {

            return AdvertiseDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(
                        ARG_AD_TYPE,
                        adType
                    )
                }
            }
        }
    }
}