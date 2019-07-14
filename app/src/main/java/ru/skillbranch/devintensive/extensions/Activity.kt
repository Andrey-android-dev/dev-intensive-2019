package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Type description here....
 *
 * Created by Andrey on 14.07.2019
 */

fun Activity.hideKeyboard(){
    val view = currentFocus;
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}