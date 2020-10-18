/*******************************************************************************
 * Copyright 2016 stfalcon.com
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stfalcon.chatkit.messages

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import com.stfalcon.chatkit.R
import com.stfalcon.chatkit.commons.Style

/**
 * Style for MessagesListStyle customization by xml attributes
 */
internal class MessagesListStyle private constructor(
    context: Context,
    attrs: AttributeSet
) : Style(context, attrs) {
    var textAutoLinkMask = 0
        private set
    var incomingTextLinkColor = 0
        private set
    var outgoingTextLinkColor = 0
        private set
    var incomingAvatarWidth = 0
        private set
    var incomingAvatarHeight = 0
        private set
    private var incomingBubbleDrawable = 0
    private var incomingDefaultBubbleColor = 0
    private var incomingDefaultBubblePressedColor = 0
    private var incomingDefaultBubbleSelectedColor = 0
    private var incomingImageOverlayDrawable = 0
    private var incomingDefaultImageOverlayPressedColor = 0
    private var incomingDefaultImageOverlaySelectedColor = 0
    var incomingDefaultBubblePaddingLeft = 0
        private set
    var incomingDefaultBubblePaddingRight = 0
        private set
    var incomingDefaultBubblePaddingTop = 0
        private set
    var incomingDefaultBubblePaddingBottom = 0
        private set
    var incomingTextColor = 0
        private set
    var incomingTextSize = 0
        private set
    var incomingTextStyle = 0
        private set
    var incomingTimeTextColor = 0
        private set
    var incomingTimeTextSize = 0
        private set
    var incomingTimeTextStyle = 0
        private set
    var incomingImageTimeTextColor = 0
        private set
    var incomingImageTimeTextSize = 0
        private set
    var incomingImageTimeTextStyle = 0
        private set
    private var outgoingBubbleDrawable = 0
    private var outgoingDefaultBubbleColor = 0
    private var outgoingDefaultBubblePressedColor = 0
    private var outgoingDefaultBubbleSelectedColor = 0
    private var outgoingImageOverlayDrawable = 0
    private var outgoingDefaultImageOverlayPressedColor = 0
    private var outgoingDefaultImageOverlaySelectedColor = 0
    var outgoingDefaultBubblePaddingLeft = 0
        private set
    var outgoingDefaultBubblePaddingRight = 0
        private set
    var outgoingDefaultBubblePaddingTop = 0
        private set
    var outgoingDefaultBubblePaddingBottom = 0
        private set
    var outgoingTextColor = 0
        private set
    var outgoingTextSize = 0
        private set
    var outgoingTextStyle = 0
        private set
    var outgoingTimeTextColor = 0
        private set
    var outgoingTimeTextSize = 0
        private set
    var outgoingTimeTextStyle = 0
        private set
    var outgoingImageTimeTextColor = 0
        private set
    var outgoingImageTimeTextSize = 0
        private set
    var outgoingImageTimeTextStyle = 0
        private set
    var dateHeaderPadding = 0
        private set
    var dateHeaderFormat: String? = null
        private set
    var dateHeaderTextColor = 0
        private set
    var dateHeaderTextSize = 0
        private set
    var dateHeaderTextStyle = 0
        private set

    private fun getMessageSelector(
        @ColorInt normalColor: Int, @ColorInt selectedColor: Int,
        @ColorInt pressedColor: Int, @DrawableRes shape: Int
    ): Drawable {
        val drawable = DrawableCompat.wrap(getVectorDrawable(shape)!!).mutate()
        DrawableCompat.setTintList(
            drawable,
            ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_selected),
                    intArrayOf(android.R.attr.state_pressed),
                    intArrayOf(-android.R.attr.state_pressed, -android.R.attr.state_selected)
                ), intArrayOf(selectedColor, pressedColor, normalColor)
            )
        )
        return drawable
    }

    fun getOutgoingBubbleDrawable(): Drawable? {
        return if (outgoingBubbleDrawable == -1) {
            getMessageSelector(
                outgoingDefaultBubbleColor, outgoingDefaultBubbleSelectedColor,
                outgoingDefaultBubblePressedColor, R.drawable.shape_outgoing_message
            )
        } else {
            getDrawable(outgoingBubbleDrawable)
        }
    }

    fun getOutgoingImageOverlayDrawable(): Drawable? {
        return if (outgoingImageOverlayDrawable == -1) {
            getMessageSelector(
                Color.TRANSPARENT, outgoingDefaultImageOverlaySelectedColor,
                outgoingDefaultImageOverlayPressedColor, R.drawable.shape_outgoing_message
            )
        } else {
            getDrawable(outgoingImageOverlayDrawable)
        }
    }

    fun getIncomingBubbleDrawable(): Drawable? {
        return if (incomingBubbleDrawable == -1) {
            getMessageSelector(
                incomingDefaultBubbleColor, incomingDefaultBubbleSelectedColor,
                incomingDefaultBubblePressedColor, R.drawable.shape_incoming_message
            )
        } else {
            getDrawable(incomingBubbleDrawable)
        }
    }

    fun getIncomingImageOverlayDrawable(): Drawable? {
        return if (incomingImageOverlayDrawable == -1) {
            getMessageSelector(
                Color.TRANSPARENT, incomingDefaultImageOverlaySelectedColor,
                incomingDefaultImageOverlayPressedColor, R.drawable.shape_incoming_message
            )
        } else {
            getDrawable(incomingImageOverlayDrawable)
        }
    }

    companion object {
        @JvmStatic
        fun parse(context: Context, attrs: AttributeSet): MessagesListStyle {
            val style = MessagesListStyle(context, attrs)
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessagesList)
            style.textAutoLinkMask = typedArray.getInt(R.styleable.MessagesList_textAutoLink, 0)
            style.incomingTextLinkColor = typedArray.getColor(
                R.styleable.MessagesList_incomingTextLinkColor,
                style.systemAccentColor
            )
            style.outgoingTextLinkColor = typedArray.getColor(
                R.styleable.MessagesList_outgoingTextLinkColor,
                style.systemAccentColor
            )
            style.incomingAvatarWidth = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_incomingAvatarWidth,
                style.getDimension(R.dimen.message_avatar_width)
            )
            style.incomingAvatarHeight = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_incomingAvatarHeight,
                style.getDimension(R.dimen.message_avatar_height)
            )
            style.incomingBubbleDrawable =
                typedArray.getResourceId(R.styleable.MessagesList_incomingBubbleDrawable, -1)
            style.incomingDefaultBubbleColor = typedArray.getColor(
                R.styleable.MessagesList_incomingDefaultBubbleColor,
                style.getColor(R.color.white_two)
            )
            style.incomingDefaultBubblePressedColor = typedArray.getColor(
                R.styleable.MessagesList_incomingDefaultBubblePressedColor,
                style.getColor(R.color.white_two)
            )
            style.incomingDefaultBubbleSelectedColor = typedArray.getColor(
                R.styleable.MessagesList_incomingDefaultBubbleSelectedColor,
                style.getColor(R.color.cornflower_blue_two_24)
            )
            style.incomingImageOverlayDrawable =
                typedArray.getResourceId(R.styleable.MessagesList_incomingImageOverlayDrawable, -1)
            style.incomingDefaultImageOverlayPressedColor = typedArray.getColor(
                R.styleable.MessagesList_incomingDefaultImageOverlayPressedColor,
                style.getColor(R.color.transparent)
            )
            style.incomingDefaultImageOverlaySelectedColor = typedArray.getColor(
                R.styleable.MessagesList_incomingDefaultImageOverlaySelectedColor,
                style.getColor(R.color.cornflower_blue_light_40)
            )
            style.incomingDefaultBubblePaddingLeft = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_incomingBubblePaddingLeft,
                style.getDimension(R.dimen.message_padding_left)
            )
            style.incomingDefaultBubblePaddingRight = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_incomingBubblePaddingRight,
                style.getDimension(R.dimen.message_padding_right)
            )
            style.incomingDefaultBubblePaddingTop = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_incomingBubblePaddingTop,
                style.getDimension(R.dimen.message_padding_top)
            )
            style.incomingDefaultBubblePaddingBottom = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_incomingBubblePaddingBottom,
                style.getDimension(R.dimen.message_padding_bottom)
            )
            style.incomingTextColor = typedArray.getColor(
                R.styleable.MessagesList_incomingTextColor,
                style.getColor(R.color.dark_grey_two)
            )
            style.incomingTextSize = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_incomingTextSize,
                style.getDimension(R.dimen.message_text_size)
            )
            style.incomingTextStyle =
                typedArray.getInt(R.styleable.MessagesList_incomingTextStyle, Typeface.NORMAL)
            style.incomingTimeTextColor = typedArray.getColor(
                R.styleable.MessagesList_incomingTimeTextColor,
                style.getColor(R.color.warm_grey_four)
            )
            style.incomingTimeTextSize = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_incomingTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size)
            )
            style.incomingTimeTextStyle =
                typedArray.getInt(R.styleable.MessagesList_incomingTimeTextStyle, Typeface.NORMAL)
            style.incomingImageTimeTextColor = typedArray.getColor(
                R.styleable.MessagesList_incomingImageTimeTextColor,
                style.getColor(R.color.warm_grey_four)
            )
            style.incomingImageTimeTextSize = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_incomingImageTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size)
            )
            style.incomingImageTimeTextStyle = typedArray.getInt(
                R.styleable.MessagesList_incomingImageTimeTextStyle,
                Typeface.NORMAL
            )
            style.outgoingBubbleDrawable =
                typedArray.getResourceId(R.styleable.MessagesList_outgoingBubbleDrawable, -1)
            style.outgoingDefaultBubbleColor = typedArray.getColor(
                R.styleable.MessagesList_outgoingDefaultBubbleColor,
                style.getColor(R.color.cornflower_blue_two)
            )
            style.outgoingDefaultBubblePressedColor = typedArray.getColor(
                R.styleable.MessagesList_outgoingDefaultBubblePressedColor,
                style.getColor(R.color.cornflower_blue_two)
            )
            style.outgoingDefaultBubbleSelectedColor = typedArray.getColor(
                R.styleable.MessagesList_outgoingDefaultBubbleSelectedColor,
                style.getColor(R.color.cornflower_blue_two_24)
            )
            style.outgoingImageOverlayDrawable =
                typedArray.getResourceId(R.styleable.MessagesList_outgoingImageOverlayDrawable, -1)
            style.outgoingDefaultImageOverlayPressedColor = typedArray.getColor(
                R.styleable.MessagesList_outgoingDefaultImageOverlayPressedColor,
                style.getColor(R.color.transparent)
            )
            style.outgoingDefaultImageOverlaySelectedColor = typedArray.getColor(
                R.styleable.MessagesList_outgoingDefaultImageOverlaySelectedColor,
                style.getColor(R.color.cornflower_blue_light_40)
            )
            style.outgoingDefaultBubblePaddingLeft = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_outgoingBubblePaddingLeft,
                style.getDimension(R.dimen.message_padding_left)
            )
            style.outgoingDefaultBubblePaddingRight = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_outgoingBubblePaddingRight,
                style.getDimension(R.dimen.message_padding_right)
            )
            style.outgoingDefaultBubblePaddingTop = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_outgoingBubblePaddingTop,
                style.getDimension(R.dimen.message_padding_top)
            )
            style.outgoingDefaultBubblePaddingBottom = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_outgoingBubblePaddingBottom,
                style.getDimension(R.dimen.message_padding_bottom)
            )
            style.outgoingTextColor = typedArray.getColor(
                R.styleable.MessagesList_outgoingTextColor,
                style.getColor(R.color.white)
            )
            style.outgoingTextSize = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_outgoingTextSize,
                style.getDimension(R.dimen.message_text_size)
            )
            style.outgoingTextStyle =
                typedArray.getInt(R.styleable.MessagesList_outgoingTextStyle, Typeface.NORMAL)
            style.outgoingTimeTextColor = typedArray.getColor(
                R.styleable.MessagesList_outgoingTimeTextColor,
                style.getColor(R.color.white60)
            )
            style.outgoingTimeTextSize = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_outgoingTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size)
            )
            style.outgoingTimeTextStyle =
                typedArray.getInt(R.styleable.MessagesList_outgoingTimeTextStyle, Typeface.NORMAL)
            style.outgoingImageTimeTextColor = typedArray.getColor(
                R.styleable.MessagesList_outgoingImageTimeTextColor,
                style.getColor(R.color.warm_grey_four)
            )
            style.outgoingImageTimeTextSize = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_outgoingImageTimeTextSize,
                style.getDimension(R.dimen.message_time_text_size)
            )
            style.outgoingImageTimeTextStyle = typedArray.getInt(
                R.styleable.MessagesList_outgoingImageTimeTextStyle,
                Typeface.NORMAL
            )
            style.dateHeaderPadding = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_dateHeaderPadding,
                style.getDimension(R.dimen.message_date_header_padding)
            )
            style.dateHeaderFormat = typedArray.getString(R.styleable.MessagesList_dateHeaderFormat)
            style.dateHeaderTextColor = typedArray.getColor(
                R.styleable.MessagesList_dateHeaderTextColor,
                style.getColor(R.color.warm_grey_two)
            )
            style.dateHeaderTextSize = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_dateHeaderTextSize,
                style.getDimension(R.dimen.message_date_header_text_size)
            )
            style.dateHeaderTextStyle =
                typedArray.getInt(R.styleable.MessagesList_dateHeaderTextStyle, Typeface.NORMAL)
            typedArray.recycle()
            return style
        }
    }
}
