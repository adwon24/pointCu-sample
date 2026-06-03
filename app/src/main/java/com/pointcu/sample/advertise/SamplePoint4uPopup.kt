package com.pointcu.sample.advertise

import androidx.fragment.app.FragmentManager
import com.adwon.pointcu.Point4u
import com.adwon.pointcu.Point4uAd

object SamplePoint4uPopup {
    fun show(
        fragmentManager: FragmentManager,
        adType: Point4uAd,
        listener: Point4u.OnPoint4UAdListener
    ) {
        AdvertiseDialogFragment
            .newInstance(adType)
            .apply {
                setOnPoint4UAdListener(listener)
            }
            .show(
                fragmentManager,
                "point4u_ad"
            )
    }
}