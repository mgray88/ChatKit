package com.stfalcon.chatkit.interfaces

import com.stfalcon.chatkit.commons.models.MessageType

open class MessageLayoutDelegate {
    open var cellTopLabelHeight: (message: MessageType, position: Int) -> Int = { _, _ -> 0}
    open fun cellTopLabelHeight(cellTopLabelHeight: (message: MessageType, position: Int) -> Int) {
        this.cellTopLabelHeight = cellTopLabelHeight
    }

    open var cellBottomLabelHeight: (message: MessageType, position: Int) -> Int = { _, _ -> 0}
    open fun cellBottomLabelHeight(cellBottomLabelHeight: (message: MessageType, position: Int) -> Int) {
        this.cellBottomLabelHeight = cellBottomLabelHeight
    }

    open var messageTopLabelHeight: (message: MessageType, position: Int) -> Int = { _, _ -> 0 }
    open fun messageTopLabelHeight(messageTopLabelHeight: (message: MessageType, position: Int) -> Int) {
        this.messageTopLabelHeight = messageTopLabelHeight
    }

    open var messageBottomLabelHeight: (message: MessageType, position: Int) -> Int = { _, _ -> 0 }
    open fun messageBottomLabelHeight(messageBottomLabelHeight: (message: MessageType, position: Int) -> Int) {
        this.messageBottomLabelHeight = messageBottomLabelHeight
    }
}
