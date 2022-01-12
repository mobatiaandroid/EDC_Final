package com.edc.ae.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.edc.ae.R

internal class CustomEditTextBlack : AppCompatEditText {
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
            Typeface.createFromAsset(context.assets, "font/Nunito-Regular.ttf")
        setTypeface(font, Typeface.NORMAL)
        setTextColor(ContextCompat.getColor(context, R.color.black))
    }
}
