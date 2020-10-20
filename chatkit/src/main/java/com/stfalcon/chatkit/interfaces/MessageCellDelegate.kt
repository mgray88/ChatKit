package com.stfalcon.chatkit.interfaces

import android.view.View
import com.stfalcon.chatkit.views.MessageContainerView

abstract class MessageCellDelegate :
    View.OnClickListener,
    View.OnLongClickListener {
    override fun onClick(v: View?) {}

    override fun onLongClick(v: View?): Boolean = true

    open fun onMessageClick(messageContainer: MessageContainerView) {}

    open fun onMessageLongClick(messageContainer: MessageContainerView): Boolean = true
}
