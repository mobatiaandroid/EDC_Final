package com.edc.ae.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.edc.ae.R

class CustomTextViewExtraBoldWhite : AppCompatTextView {
    constructor(context: Context?) : super(context!!) {
        setFont()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        setFont()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context!!, attrs, defStyle) {
        setFont()
    }

    private fun setFont() {
        val font =
            Typeface.createFromAsset(context.assets, "font/Nunito-ExtraBold.ttf")
        setTypeface(font, Typeface.BOLD);
        setTextColor(ContextCompat.getColor(context, R.color.white))
    }
}