package com.stfalcon.chatkit.views

import android.graphics.Color
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RelativeLayout
import android.widget.TextView
import com.stfalcon.chatkit.commons.models.MessageKind
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.databinding.MessageContentCellBinding
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.messages.MessagesListStyle

open class TextMessageCellViewHolder(
    private val binding: MessageContentCellBinding
) : MessageContentCellViewHolder(binding) {

    open var messageLabel = TextView(binding.root.context).also {
        it.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun configure(style: MessagesListStyle, adapter: MessagesListAdapter<out MessageType>) {
        super.configure(style, adapter)
        messageLabel.setTextColor(Color.WHITE)
    }

    override fun bind(message: MessageType, position: Int, adapter: MessagesListAdapter<out MessageType>) {
        super.bind(message, position, adapter)
        when (val kind = message.kind) {
            is MessageKind.Text -> {
                messageLabel.text = kind.text
                messageContainer.invalidate()
            }
        }
    }

    companion object {
        fun inflate(
            inflater: LayoutInflater,
            parent: ViewGroup? = null,
            attachToParent: Boolean = false
        ): TextMessageCellViewHolder {
            val view = MessageContentCellBinding.inflate(inflater, parent, attachToParent)
            val vh = TextMessageCellViewHolder(view)
            vh.messageContainer.addView(vh.messageLabel)

            return vh
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
