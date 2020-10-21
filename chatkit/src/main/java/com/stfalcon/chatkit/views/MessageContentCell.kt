package com.stfalcon.chatkit.views

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.viewbinding.ViewBinding
import com.stfalcon.chatkit.R
import com.stfalcon.chatkit.commons.MessageViewHolder
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.databinding.MessageContentCellBinding
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.messages.MessagesListStyle
import com.stfalcon.chatkit.utils.dp

open class MessageContentCellViewHolder(
    private val binding: MessageContentCellBinding
) : MessageViewHolder<MessageType>(binding.root), ViewBinding {

    open var message: MessageType? = null

    override fun getRoot(): ConstraintLayout = binding.root

    open var avatar: AvatarView = binding.avatar

    open var cellTopLabel: TextView = binding.cellTopLabel

    open var messageTopLabel: TextView = binding.messageTopLabel

    open var messageContainer: MessageContainerView = binding.messageContainer

    open var messageBottomLabel: TextView = binding.messageBottomLabel

    open var cellBottomLabel: TextView = binding.cellBottomLabel

    open var isSelected = false

    open fun configure(style: MessagesListStyle, adapter: MessagesListAdapter<out MessageType>, currentSender: Boolean) {
        styleAvatar(style, currentSender)
        styleMessageContainer(style, currentSender)

        binding.root.setOnClickListener {
            adapter.messageCellDelegate.onCellClick(it)
        }
        binding.root.setOnLongClickListener {
            adapter.messageCellDelegate.onCellLongClick(binding.messageContainer)
        }
        binding.messageContainer.setOnClickListener {
            adapter.messageCellDelegate.onMessageClick(binding.messageContainer)
        }
        binding.messageContainer.setOnLongClickListener {
            adapter.messageCellDelegate.onMessageLongClick(binding.messageContainer)
        }

        // binding.messageContainer.setOnCreateContextMenuListener()
    }

    override fun bind(message: MessageType, position: Int, adapter: MessagesListAdapter<out MessageType>) {
        val delegate = adapter.messageDisplayDelegate
        val style = adapter.messagesListStyle
    }

    open fun styleAvatar(style: MessagesListStyle, currentSender: Boolean) {
    }

    open fun styleMessageContainer(style: MessagesListStyle, currentSender: Boolean) {
        val params = messageContainer.layoutParams as ConstraintLayout.LayoutParams
        if (currentSender) {
            params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
            params.leftToLeft = R.id.left_guideline

            messageContainer.background = style.getOutgoingBubbleDrawable()
            messageContainer.setPadding(
                style.outgoingDefaultBubblePaddingLeft,
                style.outgoingDefaultBubblePaddingTop,
                style.outgoingDefaultBubblePaddingRight,
                style.outgoingDefaultBubblePaddingBottom
            )
        } else {
            params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
            params.rightToRight = R.id.right_guideline

            messageContainer.background = style.getIncomingBubbleDrawable()
            messageContainer.setPadding(
                style.incomingDefaultBubblePaddingLeft,
                style.incomingDefaultBubblePaddingTop,
                style.incomingDefaultBubblePaddingRight,
                style.incomingDefaultBubblePaddingBottom
            )
        }
        messageContainer.layoutParams = params
    }
}

val View.layoutInflater
    get() = context.getSystemService<LayoutInflater>()!!
