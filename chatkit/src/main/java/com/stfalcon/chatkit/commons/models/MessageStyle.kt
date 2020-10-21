package com.stfalcon.chatkit.commons.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
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

    public fun drawable(context: Context): Drawable? = when (this) {
        None -> null
        Bubble -> ContextCompat.getDrawable(context, R.drawable.bubble)
        is BubbleOutline -> TODO()
        is BubbleTail -> {
            when (style) {
                TailStyle.Curved -> TODO()
                TailStyle.Pointed -> {
                    var xFlip = false
                    var yFlip = false
                    when (corner) {
                        TailCorner.TopLeft -> yFlip = true
                        TailCorner.BottomLeft -> {}
                        TailCorner.TopRight -> { xFlip = true; yFlip = true }
                        TailCorner.BottomRight -> xFlip = true
                    }
                    val bmp = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.bubble_tail_pointed
                    )
                    createFlippedBitmap(bmp, xFlip, yFlip)?.toDrawable(context.resources)
                    // ContextCompat.getDrawable(context, R.drawable.bubble_tail_pointed)
                }
            }
        }
        is BubbleTailOutline -> TODO()
        is Custom -> TODO()
    }
}

fun createFlippedBitmap(source: Bitmap, xFlip: Boolean, yFlip: Boolean): Bitmap? {
    val matrix = Matrix()
    matrix.postScale(
        if (xFlip) -1f else 1f,
        if (yFlip) -1f else 1f,
        source.width / 2f,
        source.height / 2f
    )
    return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
}
