package com.stfalcon.chatkit.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.core.widget.TextViewCompat
import androidx.viewbinding.ViewBinding
import com.stfalcon.chatkit.R
import com.stfalcon.chatkit.commons.MessageViewHolder
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.messages.MessagesListStyle

open class MessageContentCell(
    context: Context,
    attrs: AttributeSet? = null,
    defaultStyle: Int = 0
) : ConstraintLayout(context, attrs, defaultStyle), ViewBinding {

    override fun getRoot(): MessageContentCell = this

    open var avatar: AvatarView = findViewById(R.id.avatar)

    open var cellTopLabel: TextView = findViewById(R.id.cell_top_label)

    open var messageTopLabel: TextView = findViewById(R.id.message_top_label)

    open var messageContainer: MessageContainerView = findViewById(R.id.message_container)

    open var messageBottomLabel: TextView = findViewById(R.id.message_bottom_label)

    open var cellBottomLabel: TextView = findViewById(R.id.cell_bottom_label)

    open fun applyStyle(style: MessagesListStyle) {

    }

    open fun configure(message: MessageType, adapter: MessagesListAdapter<MessageType>) {

    }

    companion object {
        fun inflate(inflater: LayoutInflater): MessageContentCell {
            TODO()
        }
    }
}

open class MessageContentCellViewHolder(
    override val binding: MessageContentCell
) : MessageViewHolder<MessageType, MessageContentCell>(binding) {
    override fun onBind(message: MessageType) {
        TODO("not implemented")
    }
}

val View.layoutInflater
    get() = context.getSystemService<LayoutInflater>()!!
