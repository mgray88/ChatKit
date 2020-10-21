package com.stfalcon.chatkit.views

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.viewbinding.ViewBinding
import com.stfalcon.chatkit.R
import com.stfalcon.chatkit.commons.MessageViewHolder
import com.stfalcon.chatkit.commons.models.AvatarPosition
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

    open var avatar: TextView = binding.avatar

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
        val layoutDelegate = adapter.messageLayoutDelegate

        delegate.configureAvatarView(avatar, message, position)

        run {
            val cellTopLabelHeight = layoutDelegate.cellTopLabelHeight(message, position)
            val params = cellTopLabel.layoutParams
            params.height = cellTopLabelHeight
            cellTopLabel.layoutParams = params
        }
        run {
            val cellBottomLabelHeight = layoutDelegate.cellBottomLabelHeight(message, position)
            val params = cellBottomLabel.layoutParams
            params.height = cellBottomLabelHeight
            cellBottomLabel.layoutParams = params
        }
        run {

        }
    }

    open fun styleAvatar(style: MessagesListStyle, currentSender: Boolean) {
        if (currentSender) {
        } else {
            val params = avatar.layoutParams as ConstraintLayout.LayoutParams
            val width = style.incomingAvatarWidth
            val height = style.incomingAvatarHeight
            if (width > 0 && height > 0) {
                params.width = width
                params.height = height

                when (style.incomingAvatarPosition.vertical) {
                    AvatarPosition.Vertical.CellTop -> {
                        params.topToTop = R.id.cell_top_label
                    }
                    AvatarPosition.Vertical.MessageLabelTop -> {
                        params.topToTop = R.id.message_top_label
                    }
                    AvatarPosition.Vertical.MessageTop -> {
                        params.topToTop = R.id.message_container
                    }
                    AvatarPosition.Vertical.MessageCenter -> {
                        params.topToTop = R.id.message_container
                        params.bottomToBottom = R.id.message_container
                    }
                    AvatarPosition.Vertical.MessageBottom -> {
                        params.bottomToBottom = R.id.message_container
                    }
                    AvatarPosition.Vertical.MessageLabelBottom -> {
                        params.bottomToBottom = R.id.message_bottom_label
                    }
                    AvatarPosition.Vertical.CellBottom -> {
                        params.bottomToBottom = R.id.cell_bottom_label
                    }
                }

                avatar.layoutParams = params
            }
        }
    }

    open fun styleMessageContainer(style: MessagesListStyle, currentSender: Boolean) {
        val params = messageContainer.layoutParams as ConstraintLayout.LayoutParams
        if (currentSender) {
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            params.rightToLeft = ConstraintLayout.LayoutParams.UNSET
            params.horizontalBias = 1f
            params.startToEnd = R.id.left_guideline

            messageContainer.background = style.getOutgoingBubbleDrawable()
            messageContainer.setPadding(
                style.outgoingDefaultBubblePaddingLeft,
                style.outgoingDefaultBubblePaddingTop,
                style.outgoingDefaultBubblePaddingRight,
                style.outgoingDefaultBubblePaddingBottom
            )

        } else {
            params.startToEnd = R.id.avatar
            params.leftToRight = ConstraintLayout.LayoutParams.UNSET
            params.horizontalBias = 0f
            params.endToStart = R.id.right_guideline

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
