package com.stfalcon.chatkit.interfaces

import com.stfalcon.chatkit.commons.models.MessageStyle
import com.stfalcon.chatkit.commons.models.MessageType

abstract class MessageDisplayDelegate {
    open fun messageStyleFor(message: MessageType): MessageStyle? = null
}
