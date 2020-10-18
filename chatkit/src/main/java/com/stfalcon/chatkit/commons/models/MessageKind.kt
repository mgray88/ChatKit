package com.stfalcon.chatkit.commons.models

import android.text.Spanned

sealed class MessageKind {

    class Text(val text: String): MessageKind()

    class SpannedText(val spanned: Spanned): MessageKind()

    class Photo(val item: MediaItem): MessageKind()

    class emoji(val emoji: String): MessageKind()
}
