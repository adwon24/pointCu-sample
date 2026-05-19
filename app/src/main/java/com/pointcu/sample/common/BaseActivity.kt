package com.pointcu.sample.common

import android.app.Activity
import android.os.Bundle
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

abstract class BaseActivity : AppCompatActivity() {
    val TAG = "adwon.sample"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun replaceFragment(@IdRes containerViewId: Int, fragment: Fragment, backStack: Boolean) {
        supportFragmentManager.commit {
            if (backStack) {
                addToBackStack(fragment.javaClass.name)
            }
            setReorderingAllowed(backStack)
            replace(containerViewId, fragment, fragment.javaClass.name)
        }
    }

    fun finishFragment(fragment: Fragment, popupTo: Fragment? = null) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
        if (supportFragmentManager.isStateSaved.not() && popupTo != null) {
            supportFragmentManager.popBackStack(popupTo.javaClass.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun popupTo(popupTo: Fragment) {
        if (supportFragmentManager.isStateSaved.not()) {
            supportFragmentManager.popBackStack(popupTo.javaClass.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun getVisibleFragment(): Fragment? {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            for (fragment in supportFragmentManager.fragments) {
                if (fragment != null && fragment.isVisible) return fragment
            }
        }
        return null
    }
}