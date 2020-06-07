package com.example.inhuis.helpers

import android.content.Context
import android.graphics.Typeface

object FontManager {
    const val ROOT = "/"
    const val FONTAWESOME = ROOT + "fontawesome-webfont.ttf"
    fun getTypeface(context: Context, font: String?): Typeface {
        return Typeface.createFromAsset(context.assets, font)
    }
}