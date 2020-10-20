package com.stfalcon.chatkit.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.FontRes

open class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultStyle: Int = 0
) : RoundedImageView(context, attrs, defaultStyle) {

    open var initials: String? = null
        set(value) {
            field = value
            setImageFrom(initials)
        }

    @FontRes open var placeholderFont: Int? = null
        set(value) {
            field = value
            setImageFrom(initials)
        }

    @ColorRes open var placeholderTextColor: Int? = null
        set(value) {
            field = value
            setImageFrom(initials)
        }

    private fun setImageFrom(initials: String?) {
        initials?.let {
            setImageDrawable(getDrawableFrom(initials))
        }
    }

    private fun getDrawableFrom(initials: String): Drawable {
        TODO()
    }
}
