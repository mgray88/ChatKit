package com.stfalcon.chatkit.views

import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import com.stfalcon.chatkit.R
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.databinding.MessageContentCellBinding
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.messages.MessagesListStyle

open class TextMessageCellViewHolder(
    private val binding: MessageContentCellBinding
) : MessageContentCellViewHolder(binding) {

    override fun applyStyle(style: MessagesListStyle, adapter: MessagesListAdapter<out MessageType>) {
        super.applyStyle(style, adapter)
    }

    override fun configure(message: MessageType, position: Int, adapter: MessagesListAdapter<out MessageType>) {
        super.configure(message, position, adapter)
    }

    companion object {
        fun inflate(
            inflater: LayoutInflater,
            parent: ViewGroup? = null,
            attachToParent: Boolean = false
        ): TextMessageCellViewHolder {
            val view = MessageContentCellBinding.inflate(inflater, parent, attachToParent)
            return TextMessageCellViewHolder(view)
        }
    }

    open fun configureLinksBehavior(textView: TextView) {
        textView.linksClickable = true
        textView.movementMethod = object : LinkMovementMethod() {
            override fun onTouchEvent(
                widget: TextView?,
                buffer: Spannable?,
                event: MotionEvent?
            ): Boolean {
                var result = false
                if (!MessagesListAdapter.isSelectionModeEnabled) {
                    result = super.onTouchEvent(widget, buffer, event)
                }
                itemView.onTouchEvent(event)
                return result
            }
        }
    }
}
