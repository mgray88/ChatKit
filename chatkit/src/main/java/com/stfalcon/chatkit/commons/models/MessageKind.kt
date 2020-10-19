package com.stfalcon.chatkit.commons.models

import android.text.Spanned

sealed class MessageKind {

    class Text(val text: String): MessageKind()

    class SpannedText(val spanned: Spanned): MessageKind()

    class Photo(val item: MediaItem): MessageKind()

    class Emoji(val emoji: String): MessageKind()

    internal val viewType: Int
        get() = when (this) {
            is Text -> 0
            is SpannedText -> 1
            is Photo -> 2
            is Emoji -> 3
        }
}
