/*******************************************************************************
 * Copyright 2016 stfalcon.com
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stfalcon.chatkit.messages

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.interfaces.MessageCellDelegate
import com.stfalcon.chatkit.interfaces.MessageDataSourceDelegate
import com.stfalcon.chatkit.interfaces.MessageDisplayDelegate
import com.stfalcon.chatkit.interfaces.MessageLayoutDelegate
import com.stfalcon.chatkit.views.MessageContentCellViewHolder
import com.stfalcon.chatkit.views.TextMessageCellViewHolder
import com.stfalcon.chatkit.views.layoutInflater

/**
 * Adapter for [MessagesList].
 */
open class MessagesListAdapter<Message : MessageType> @JvmOverloads constructor(
    internal val senderId: String,
    protected val imageLoader: ImageLoader? = null,
    diffCallback: DiffUtil.ItemCallback<Message>
) : ListAdapter<Message, MessageContentCellViewHolder>(diffCallback), RecyclerScrollMoreListener.OnLoadMoreListener {

    open var messageCellDelegate = MessageCellDelegate()
    open var messageDisplayDelegate = MessageDisplayDelegate()
    open var messageLayoutDelegate = MessageLayoutDelegate()
    open var messageDataSourceDelegate = MessageDataSourceDelegate()
    open var layoutManager: RecyclerView.LayoutManager? = null
    open var messagesListStyle: MessagesListStyle? = null

    private var loadMoreListener: OnLoadMoreListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageContentCellViewHolder {
        return when (viewType) {
            1, -1 -> {
                TextMessageCellViewHolder.inflate(parent.layoutInflater, parent).also { vh ->
                    messagesListStyle?.let {
                        vh.configure(it, this, viewType < 0)
                    }
                }
            }
            else -> {
                TODO()
            }
        }
    }

    override fun onBindViewHolder(holder: MessageContentCellViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message, position, this)
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.sender.id == senderId) {
            message.kind.viewType * -1
        } else {
            message.kind.viewType
        }
    }

    override fun onLoadMore(page: Int, total: Int) {
        if (loadMoreListener != null) {
            loadMoreListener!!.onLoadMore(page, total)
        }
    }

    override fun getMessagesCount(): Int {
        return itemCount
    }

    public override fun getItem(position: Int): Message {
        return super.getItem(position)
    }
    
    /*
     * PUBLIC METHODS
     * */
    /**
     * Adds message to bottom of list and scroll if needed.
     *
     * @param message message to add.
     * @param scroll  `true` if need to scroll list to bottom when message added.
     */
//    fun addToStart(message: Message, scroll: Boolean) {
//        messages.add(0, message)
//        notifyItemRangeInserted(0, 1)
//        if (layoutManager != null && scroll) {
//            layoutManager!!.scrollToPosition(0)
//        }
//    }

    /**
     * Updates message by its id.
     *
     * @param message updated message object.
     */
//    fun update(message: Message): Boolean {
//        return update(message.messageId, message)
//    }

    /**
     * Updates message by old identifier (use this method if id has changed). Otherwise use [.update]
     *
     * @param oldId      an identifier of message to update.
     * @param newMessage new message object.
     */
//    fun update(oldId: String, newMessage: Message): Boolean {
//        val position = getMessagePositionById(oldId)
//        return if (position >= 0) {
//            messages[position] = newMessage
//            notifyItemChanged(position)
//            true
//        } else {
//            false
//        }
//    }

    /**
     * Moves the elements position from current to start
     *
     * @param newMessage new message object.
     */
//    fun updateAndMoveToStart(newMessage: Message) {
//        val position = getMessagePositionById(newMessage.messageId)
//        if (position >= 0) {
//            messages.removeAt(position)
//            messages.add(0, newMessage)
//            notifyItemMoved(position, 0)
//            notifyItemChanged(0)
//        }
//    }

    /**
     * Updates message by its id if it exists, add to start if not
     *
     * @param message message object to insert or update.
     */
//    fun upsert(message: Message) {
//        if (!update(message)) {
//            addToStart(message, false)
//        }
//    }

    /**
     * Updates and moves to start if message by its id exists and if specified move to start, if not
     * specified the item stays at current position and updated
     *
     * @param message message object to insert or update.
     */
//    fun upsert(message: Message, moveToStartIfUpdate: Boolean) {
//        if (moveToStartIfUpdate) {
//            if (getMessagePositionById(message.messageId) > 0) {
//                updateAndMoveToStart(message)
//            } else {
//                upsert(message)
//            }
//        } else {
//            upsert(message)
//        }
//    }

    /**
     * Returns `true` if, and only if, messages count in adapter is non-zero.
     *
     * @return `true` if size is 0, otherwise `false`
     */
//    val isEmpty: Boolean
//        get() = messages.isEmpty()

    /**
     * Set callback to be invoked when list scrolled to top.
     *
     * @param loadMoreListener listener.
     */
    fun setLoadMoreListener(loadMoreListener: OnLoadMoreListener?) {
        this.loadMoreListener = loadMoreListener
    }

    /*
     * PRIVATE METHODS
     * */

//    internal fun getMessagePositionById(id: String): Int {
//        return messages.indexOfFirst { it.messageId == id }
//    }

    /*
     * LISTENERS
     * */
    /**
     * Interface definition for a callback to be invoked when next part of messages need to be loaded.
     */
    fun interface OnLoadMoreListener {
        /**
         * Fires when user scrolled to the end of list.
         *
         * @param page            next page to download.
         * @param totalItemsCount current items count.
         */
        fun onLoadMore(page: Int, totalItemsCount: Int)
    }

    companion object {
        @JvmField
        var isSelectionModeEnabled = false
    }
}
