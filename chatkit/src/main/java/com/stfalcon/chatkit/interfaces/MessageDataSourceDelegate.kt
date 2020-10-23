package com.stfalcon.chatkit.interfaces

import android.widget.TextView
import com.stfalcon.chatkit.commons.models.MessageType

open class MessageDataSourceDelegate {
//    open var configureDateLabel: (dateView: TextView, message: MessageType, position: Int) -> Unit = { _, _, _ ->}
//    open fun configureDateLabel(configureDateLabel: (dateView: TextView, message: MessageType, position: Int) -> Unit) {
//        this.configureDateLabel = configureDateLabel
//    }

    open var bindCellTopLabel: (message: MessageType, position: Int, textView: TextView) -> Unit = { _, _, _ -> }
    open fun bindCellTopLabel(bindCellTopLabel: (message: MessageType, position: Int, textView: TextView) -> Unit) {
        this.bindCellTopLabel = bindCellTopLabel
    }
    
    open var cellTopLabelText: (message: MessageType, position: Int) -> String? = { _, _ -> null }
    open fun cellTopLabelText(cellTopLabelText: (message: MessageType, position: Int) -> String?) {
        this.cellTopLabelText = cellTopLabelText
    }

    open var bindMessageTopLabel: (message: MessageType, position: Int, textView: TextView) -> Unit = { _, _, _ -> }
    open fun bindMessageTopLabel(bindMessageTopLabel: (message: MessageType, position: Int, textView: TextView) -> Unit) {
        this.bindMessageTopLabel = bindMessageTopLabel
    }

    open var bindMessageBottomLabel: (message: MessageType, position: Int, textView: TextView) -> Unit = { _, _, _ -> }
    open fun bindMessageBottomLabel(bindMessageBottomLabel: (message: MessageType, position: Int, textView: TextView) -> Unit) {
        this.bindMessageBottomLabel = bindMessageBottomLabel
    }
}