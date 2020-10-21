package com.stfalcon.chatkit.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.appcompat.widget.AppCompatImageView
import com.stfalcon.chatkit.utils.TextDrawable
import com.stfalcon.chatkit.utils.sp

open class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultStyle: Int = 0
) : AppCompatImageView(context, attrs, defaultStyle) {

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        setImageDrawable(TextDrawable.builder()
            .beginConfig()
            .width(width)
            .height(height)
            .fontSize(20.sp)
            .textColor(Color.WHITE)
            .endConfig()
            .round()
            .build("?", Color.GRAY)
        )
    }

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
            val drawable = this.drawable as TextDrawable
            drawable.text = it
            setImageDrawable(drawable)
        }
    }

    // private fun getDrawableFrom(initials: String): Drawable {
    //     return TextDrawable.builder()
    //         .beginConfig()
    //         .width(width)
    //         .height(height)
    //         .fontSize(20.sp)
    //         .textColor(Color.WHITE)
    //         .endConfig()
    //         .buildRound(initials, Color.GRAY)
    // }
}
