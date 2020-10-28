package com.stfalcon.chatkit.interfaces

import android.view.View
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.views.MessageContainerView

open class MessageCellDelegate {
    open var onCellClick: (v: View) -> Unit = { _ -> }
    open fun onCellClick(onCellClick: (v: View) -> Unit) {
        this.onCellClick = onCellClick
    }

    open var onCellLongClick: (v: View) -> Boolean = { _ -> true }
    open fun onCellLongClick(onCellLongClick: (v: View) -> Boolean) {
        this.onCellLongClick = onCellLongClick
    }

    open var onMessageClick: (message: MessageType) -> Unit = { _ -> }
    open fun onMessageClick(onMessageClick: (message: MessageType) -> Unit) {
        this.onMessageClick = onMessageClick
    }

    open var onMessageLongClick: (messageContainer: MessageContainerView) -> Boolean = { _ -> true }
    open fun onMessageLongClick(onMessageLongClick: (messageContainer: MessageContainerView) -> Boolean) {
        this.onMessageLongClick = onMessageLongClick
    }
}
