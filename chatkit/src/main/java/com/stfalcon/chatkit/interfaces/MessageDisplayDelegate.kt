package com.stfalcon.chatkit.interfaces

import android.view.View
import com.stfalcon.chatkit.commons.models.MessageStyle
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.views.AvatarView

open class MessageDisplayDelegate {
    open fun messageStyleFor(message: MessageType): MessageStyle? = null

    open var configureAvatarView: (avatarView: AvatarView, message: MessageType, position: Int) -> Unit  = { _, _, _  -> }
    open fun configureAvatarView(configureAvatarView: (avatarView: AvatarView, message: MessageType, position: Int) -> Unit)  {
        this.configureAvatarView = configureAvatarView
    }
}
