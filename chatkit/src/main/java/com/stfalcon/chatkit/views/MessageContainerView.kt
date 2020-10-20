package com.stfalcon.chatkit.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.stfalcon.chatkit.commons.models.MessageStyle

open class MessageContainerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultRes: Int = 0
) : FrameLayout(context, attrs, defaultRes) {

    open var style: MessageStyle = MessageStyle.None
        set(value) {
            field = value
            applyMessageStyle()
        }

    internal fun applyMessageStyle() = style.let { style ->
        when (style) {
            MessageStyle.None -> {
                background = null
            }
            MessageStyle.Bubble -> style.drawableRes()?.let { res ->
                setBackgroundResource(res)
            }
            is MessageStyle.BubbleOutline -> TODO()
            is MessageStyle.BubbleTail -> style.drawableRes()?.let { res ->
                setBackgroundResource(res)
            }
            is MessageStyle.BubbleTailOutline -> TODO()
            is MessageStyle.Custom -> {
                style.config.invoke(this)
            }
        }
    }
}
