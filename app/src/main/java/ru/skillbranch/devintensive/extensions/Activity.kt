package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

/**
 * Type description here....
 *
 * Created by Andrey on 14.07.2019
 */

fun Activity.hideKeyboard() {
    val view = currentFocus;
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}


fun Activity.isKeyboardOpen(): Boolean {
    val v = findViewById<ViewGroup>(android.R.id.content).getChildAt(0);
    val r = Rect()
    v.getWindowVisibleDisplayFrame(r)
    if (v.height > r.bottom) {
        return true
    } else {
        return false
    }
}

fun Activity.isKeyboardClosed(): Boolean {
    val v = findViewById<ViewGroup>(android.R.id.content).getChildAt(0);
    val r = Rect()
    v.getWindowVisibleDisplayFrame(r)
    if (v.height < r.bottom) {
        return true
    } else {
        return false
    }
}