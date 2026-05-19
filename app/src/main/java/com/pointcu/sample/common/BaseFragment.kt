package com.pointcu.sample.common

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.adwon.pointcu.R

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    val TAG = "adwon.sample"

    protected var rootView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rootView = view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    fun <T : View> findViewById(id: Int): T? {
        return rootView?.findViewById<T>(id)
    }

    fun replaceFragment(@IdRes containerViewId: Int, fragment: Fragment, backStack: Boolean) {
        if (context is BaseActivity) {
            (context as BaseActivity).replaceFragment(containerViewId, fragment, backStack)

        } else {
            parentFragmentManager.commit {
                if (backStack) {
                    addToBackStack(fragment.javaClass.simpleName)
                }
                setReorderingAllowed(true)
                replace(containerViewId, fragment, fragment.javaClass.name)
            }
        }
    }

    fun finishFragment(popupTo: Fragment? = null) {
        if (context is BaseActivity) {
            (context as BaseActivity).finishFragment(this@BaseFragment, popupTo)

        } else {
            parentFragmentManager.beginTransaction().remove(this@BaseFragment).commit()
        }
    }
}