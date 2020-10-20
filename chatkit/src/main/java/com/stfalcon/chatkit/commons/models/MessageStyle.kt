package com.stfalcon.chatkit.commons.models

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.stfalcon.chatkit.R
import com.stfalcon.chatkit.views.MessageContainerView

sealed class MessageStyle {
    object None: MessageStyle()
    object Bubble: MessageStyle()
    class BubbleOutline(@ColorInt val color: Int): MessageStyle()
    class BubbleTail(val corner: TailCorner, val style: TailStyle): MessageStyle()
    class BubbleTailOutline(@ColorInt val color: Int, val corner: TailCorner, val style: TailStyle): MessageStyle()
    class Custom(val config: (MessageContainerView) -> Void): MessageStyle()

    sealed class TailCorner {
        object TopLeft: TailCorner()
        object BottomLeft: TailCorner()
        object TopRight: TailCorner()
        object BottomRight: TailCorner()
    }

    sealed class TailStyle {
        object Curved: TailStyle()
        object Pointed: TailStyle()
    }

    @DrawableRes
    public fun drawableRes(): Int? = when (this) {
        None -> null
        Bubble -> R.drawable.bubble // TODO
        is BubbleOutline -> TODO()
        is BubbleTail -> {
            when (style) {
                TailStyle.Curved -> TODO()
                TailStyle.Pointed -> R.drawable.bubble_tail_pointed
            }
        }
        is BubbleTailOutline -> TODO()
        is Custom -> null
    }
}
