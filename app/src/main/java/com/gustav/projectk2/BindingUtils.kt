package com.gustav.projectk2

import android.util.Log
import android.webkit.WebView
import androidx.databinding.BindingAdapter

@BindingAdapter("webViewUrl")
fun WebView.updateUrl(url: String?) {
    url?.let {
        loadData(url, "text/html; charset=utf-8", "UTF-8")
    }
}