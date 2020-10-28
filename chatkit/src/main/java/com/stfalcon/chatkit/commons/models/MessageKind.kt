package com.stfalcon.chatkit.commons.models

import android.text.Spanned

sealed class MessageKind {

    data class Text(val text: String): MessageKind()

    data class SpannedText(val spanned: Spanned): MessageKind()

    data class Photo(val item: MediaItem): MessageKind()

    data class Emoji(val emoji: String): MessageKind()

    internal val viewType: Int
        get() = when (this) {
            is Text -> 1
            is SpannedText -> 2
            is Photo -> 3
            is Emoji -> 4
        }
}
