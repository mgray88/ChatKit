package com.stfalcon.chatkit.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.stfalcon.chatkit.commons.MessageViewHolder
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.databinding.MessageContentCellBinding
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.messages.MessagesListStyle

open class TextMessageCell(
    context: Context,
    attrs: AttributeSet? = null,
    defaultStyle: Int = 0
) : MessageContentCell(context, attrs, defaultStyle) {

    override fun getRoot(): TextMessageCell = this

    override fun applyStyle(style: MessagesListStyle) {
        super.applyStyle(style)
    }

    override fun configure(message: MessageType, adapter: MessagesListAdapter<MessageType>) {
        super.configure(message, adapter)
    }

    companion object {
        fun inflate(inflater: LayoutInflater): TextMessageCell {
            TODO()
        }
    }
}

open class TextMessageCellViewHolder(
    override val binding: TextMessageCell
) : MessageContentCellViewHolder(binding) {
    override fun onBind(message: MessageType) {
        TODO("not implemented")
    }
}
