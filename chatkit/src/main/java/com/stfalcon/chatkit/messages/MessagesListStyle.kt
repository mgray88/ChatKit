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
import com.stfalcon.chatkit.commons.models.AvatarPosition
import com.stfalcon.chatkit.commons.models.MessageStyle

/**
 * Style for MessagesListStyle customization by xml attributes
 */
class MessagesListStyle private constructor(
    context: Context,
    attrs: AttributeSet
) : Style(context, attrs) {
    var textAutoLinkMask = 0
    var defaultMessageSpacing = 0

    var incomingTextLinkColor = 0
    var outgoingTextLinkColor = 0
    var incomingAvatarWidth = 0
    var incomingAvatarHeight = 0
    var incomingAvatarPosition = AvatarPosition(AvatarPosition.Vertical.MessageCenter)
    var incomingDefaultBubblePaddingLeft = 0
    var incomingDefaultBubblePaddingRight = 0
    var incomingDefaultBubblePaddingTop = 0
    var incomingDefaultBubblePaddingBottom = 0
    var incomingTextColor = 0
    var incomingTextSize = 0
    var incomingTextStyle = 0
    var incomingTimeTextColor = 0
    var incomingTimeTextSize = 0
    var incomingTimeTextStyle = 0
    var incomingImageTimeTextColor = 0
    var incomingImageTimeTextSize = 0
    var incomingImageTimeTextStyle = 0

    private var incomingBubbleStyleRaw = 0
    private var incomingBubbleStyleTailCorner = 0
    private var incomingBubbleStyleTailStyle = 0
    private var incomingBubbleDrawable = 0
    private var incomingDefaultBubbleColor = 0
    private var incomingDefaultBubblePressedColor = 0
    private var incomingDefaultBubbleSelectedColor = 0
    private var incomingImageOverlayDrawable = 0
    private var incomingDefaultImageOverlayPressedColor = 0
    private var incomingDefaultImageOverlaySelectedColor = 0

    val incomingBubbleStyle: MessageStyle
        get() = when (incomingBubbleStyleRaw) {
            1 -> MessageStyle.Bubble
            2 -> MessageStyle.BubbleOutline(incomingDefaultBubbleColor)
            3 -> {
                MessageStyle.BubbleTail(
                    bubbleStyleTailCorner(incomingBubbleStyleTailCorner),
                    bubbleStyleTailStyle(incomingBubbleStyleTailStyle)
                )
            }
            4 -> {
                MessageStyle.BubbleTailOutline(
                    incomingDefaultBubbleColor,
                    bubbleStyleTailCorner(incomingBubbleStyleTailCorner),
                    bubbleStyleTailStyle(incomingBubbleStyleTailStyle)
                )
            }
            else -> MessageStyle.None
        }

    var outgoingDefaultBubblePaddingLeft = 0
    var outgoingDefaultBubblePaddingRight = 0
    var outgoingDefaultBubblePaddingTop = 0
    var outgoingDefaultBubblePaddingBottom = 0
    var outgoingTextColor = 0
    var outgoingTextSize = 0
    var outgoingTextStyle = 0
    var outgoingTimeTextColor = 0
    var outgoingTimeTextSize = 0
    var outgoingTimeTextStyle = 0
    var outgoingImageTimeTextColor = 0
    var outgoingImageTimeTextSize = 0
    var outgoingImageTimeTextStyle = 0

    private var outgoingBubbleStyleRaw = 0
    private var outgoingBubbleStyleTailCorner = 0
    private var outgoingBubbleStyleTailStyle = 0
    private var outgoingBubbleDrawable = 0
    private var outgoingDefaultBubbleColor = 0
    private var outgoingDefaultBubblePressedColor = 0
    private var outgoingDefaultBubbleSelectedColor = 0
    private var outgoingImageOverlayDrawable = 0
    private var outgoingDefaultImageOverlayPressedColor = 0
    private var outgoingDefaultImageOverlaySelectedColor = 0

    val outgoingBubbleStyle: MessageStyle
        get() = when (outgoingBubbleStyleRaw) {
            1 -> MessageStyle.Bubble
            2 -> MessageStyle.BubbleOutline(outgoingDefaultBubbleColor)
            3 -> {
                MessageStyle.BubbleTail(
                    bubbleStyleTailCorner(outgoingBubbleStyleTailCorner),
                    bubbleStyleTailStyle(outgoingBubbleStyleTailStyle)
                )
            }
            4 -> {
                MessageStyle.BubbleTailOutline(
                    outgoingDefaultBubbleColor,
                    bubbleStyleTailCorner(outgoingBubbleStyleTailCorner),
                    bubbleStyleTailStyle(outgoingBubbleStyleTailStyle)
                )
            }
            else -> MessageStyle.None
        }

    var dateHeaderPadding = 0
    var dateHeaderFormat: String? = null
    var dateHeaderTextColor = 0
    var dateHeaderTextSize = 0
    var dateHeaderTextStyle = 0

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

    private fun bubbleStyleTailCorner(raw: Int) = when (raw) {
        0 -> MessageStyle.TailCorner.TopLeft
        1 -> MessageStyle.TailCorner.BottomLeft
        2 -> MessageStyle.TailCorner.TopRight
        4 -> MessageStyle.TailCorner.BottomRight
        else -> throw IllegalArgumentException("")
    }

    private fun bubbleStyleTailStyle(raw: Int) = when (raw) {
        0 -> MessageStyle.TailStyle.Pointed
        1 -> MessageStyle.TailStyle.Curved
        else -> throw IllegalArgumentException("")
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
            style.defaultMessageSpacing = typedArray.getDimensionPixelSize(
                R.styleable.MessagesList_messageItemSpacing,
                style.getDimension(R.dimen.message_item_spacing)
            )
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
            style.incomingAvatarPosition = when (typedArray.getInt(
                R.styleable.MessagesList_incomingAvatarPosition, 3
            )) {
                0 -> AvatarPosition(AvatarPosition.Vertical.CellTop)
                1 -> AvatarPosition(AvatarPosition.Vertical.MessageLabelTop)
                2 -> AvatarPosition(AvatarPosition.Vertical.MessageTop)
                3 -> AvatarPosition(AvatarPosition.Vertical.MessageCenter)
                4 -> AvatarPosition(AvatarPosition.Vertical.MessageBottom)
                5 -> AvatarPosition(AvatarPosition.Vertical.MessageLabelBottom)
                6 -> AvatarPosition(AvatarPosition.Vertical.CellBottom)
                else -> AvatarPosition(AvatarPosition.Vertical.MessageCenter)
            }

            style.incomingBubbleStyleRaw =
                typedArray.getInt(R.styleable.MessagesList_incomingBubbleStyle, 0)
            style.incomingBubbleStyleTailCorner =
                typedArray.getInt(R.styleable.MessagesList_incomingBubbleStyleTailCorner, 0)
            style.incomingBubbleStyleTailStyle =
                typedArray.getInt(R.styleable.MessagesList_incomingBubbleStyleTailStyle, 0)
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
            style.outgoingBubbleStyleRaw =
                typedArray.getInt(R.styleable.MessagesList_outgoingBubbleStyle, 0)
            style.outgoingBubbleStyleTailCorner =
                typedArray.getInt(R.styleable.MessagesList_outgoingBubbleStyleTailCorner, 0)
            style.outgoingBubbleStyleTailStyle =
                typedArray.getInt(R.styleable.MessagesList_outgoingBubbleStyleTailStyle, 0)
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
