package com.example.vkandroidtest.utlis

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class AppUtils {
    companion object {
        fun hideKeyboard(activity: Activity, view: View) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }
}