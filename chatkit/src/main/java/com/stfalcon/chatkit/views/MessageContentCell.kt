package com.stfalcon.chatkit.views

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import androidx.viewbinding.ViewBinding
import com.stfalcon.chatkit.commons.MessageViewHolder
import com.stfalcon.chatkit.commons.models.MessageStyle
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.databinding.MessageContentCellBinding
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.messages.MessagesListStyle

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

    open fun applyStyle(style: MessagesListStyle, adapter: MessagesListAdapter<out MessageType>) {
        styleAvatar(style)
        styleMessageContainer(style)

        binding.root.setOnClickListener {
            val message = this.message ?: return@setOnClickListener
            if (adapter.isSelectionModeEnabled) {
                isSelected = !isSelected
                if (isSelected) adapter.incrementSelectedItemsCount() else adapter.decrementSelectedItemsCount()
                adapter.notifyItemChanged(adapter.getMessagePositionById(message.messageId))
            } else {
                adapter.messageCellDelegate?.onClick(it)
            }
        }
        binding.root.setOnLongClickListener {
            val handled = adapter.messageCellDelegate
                ?.onLongClick(binding.messageContainer) ?: false
            if (!handled) {
                adapter.isSelectionModeEnabled = true
                it.performClick()
            }
            true
        }
        binding.messageContainer.setOnClickListener {
            adapter.messageCellDelegate?.onMessageClick(binding.messageContainer)
        }
        binding.messageContainer.setOnLongClickListener {
            adapter.messageCellDelegate?.onMessageLongClick(binding.messageContainer) ?: true
        }

        // binding.messageContainer.setOnCreateContextMenuListener()
    }

    open fun configure(message: MessageType, position: Int, adapter: MessagesListAdapter<out MessageType>) {
        val delegate = adapter.messageDisplayDelegate ?: return

        // messageContainer.style = delegate.messageStyleFor(message)
    }

    override fun onBind(message: MessageType) {
        this.message = message
        // TODO
    }

    open fun styleAvatar(style: MessagesListStyle) {
    }

    open fun styleMessageContainer(style: MessagesListStyle) {
        messageContainer.style = MessageStyle.Bubble // TODO
    }
}

val View.layoutInflater
    get() = context.getSystemService<LayoutInflater>()!!
