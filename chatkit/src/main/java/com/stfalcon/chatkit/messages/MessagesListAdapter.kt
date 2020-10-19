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

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.utils.DateFormatter
import com.stfalcon.chatkit.views.MessageContentCell
import com.stfalcon.chatkit.views.MessageContentCellViewHolder
import com.stfalcon.chatkit.views.TextMessageCell
import com.stfalcon.chatkit.views.TextMessageCellViewHolder
import com.stfalcon.chatkit.views.layoutInflater
import java.util.ArrayList
import java.util.Date

/**
 * Adapter for [MessagesList].
 */
open class MessagesListAdapter<Message : MessageType> @JvmOverloads constructor(
    private val senderId: String,
    private val imageLoader: ImageLoader? = null,
    private val holders: MessageHolders = MessageHolders(),
) : RecyclerView.Adapter<MessageContentCellViewHolder>(), RecyclerScrollMoreListener.OnLoadMoreListener {
    protected val messages = mutableListOf<Message>()

    protected var items = mutableListOf<Wrapper<*>>()
    private var selectedItemsCount = 0
    private var selectionListener: SelectionListener? = null
    private var loadMoreListener: OnLoadMoreListener? = null
    private var onMessageClickListener: OnMessageClickListener<Message>? = null
    private var onMessageViewClickListener: OnMessageViewClickListener<Message>? = null
    private var onMessageLongClickListener: OnMessageLongClickListener<Message>? = null
    private var onMessageViewLongClickListener: OnMessageViewLongClickListener<Message>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var messagesListStyle: MessagesListStyle? = null
    private var dateHeadersFormatter: DateFormatter.Formatter? = null
    private val viewClickListenersArray = SparseArray<OnMessageViewClickListener<Message>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageContentCellViewHolder {
        return when (viewType) {
            0 -> {
                val textMessageCell = TextMessageCell.inflate(parent.layoutInflater)
                messagesListStyle?.let { textMessageCell.applyStyle(it) }
                TextMessageCellViewHolder(textMessageCell)
            }
            else -> {
                MessageContentCellViewHolder(MessageContentCell.inflate(parent.layoutInflater))
            }
        }
        // return holders.getHolder(parent, viewType, messagesListStyle)
    }

    override fun onBindViewHolder(holder: MessageContentCellViewHolder, position: Int) {
        val message = messages[position]
        holder.onBind(message)
        // val wrapper: Wrapper<MESSAGE> = items[position] as Wrapper<MESSAGE>
        // holders.bind(
        //     holder, wrapper.item, wrapper.isSelected, imageLoader,
        //     getMessageClickListener(wrapper),
        //     getMessageLongClickListener(wrapper),
        //     dateHeadersFormatter,
        //     viewClickListenersArray as SparseArray<OnMessageViewClickListener<MessageType>>
        // )
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return messages[position].kind.viewType
        // return holders.getViewType(items[position].item, senderId)
    }

    override fun onLoadMore(page: Int, total: Int) {
        if (loadMoreListener != null) {
            loadMoreListener!!.onLoadMore(page, total)
        }
    }

    override fun getMessagesCount(): Int {
        var count = 0
        for (item in items) {
            if (item.item is MessageType) {
                count++
            }
        }
        return count
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
    fun addToStart(message: Message, scroll: Boolean) {
        val isNewMessageToday = !isPreviousSameDate(0, message.sentDate)
        if (isNewMessageToday) {
            items.add(
                0, Wrapper<Date>(
                    message.sentDate
                )
            )
        }
        val element: Wrapper<Message> = Wrapper(message)
        items.add(0, element)
        notifyItemRangeInserted(0, if (isNewMessageToday) 2 else 1)
        if (layoutManager != null && scroll) {
            layoutManager!!.scrollToPosition(0)
        }
    }

    /**
     * Adds messages list in chronological order. Use this method to add history.
     *
     * @param messages messages from history.
     * @param reverse  `true` if need to reverse messages before adding.
     */
    fun addToEnd(messages: List<Message>, reverse: Boolean) {
        if (messages.isEmpty()) return
        val messages = if (reverse) messages.reversed() else messages
        if (items.isNotEmpty()) {
            val lastItemPosition = items.size - 1
            val lastItem = items[lastItemPosition].item as Date
            if (DateFormatter.isSameDay(messages[0].sentDate, lastItem)) {
                items.removeAt(lastItemPosition)
                notifyItemRemoved(lastItemPosition)
            }
        }
        val oldSize = items.size
        generateDateHeaders(messages)
        notifyItemRangeInserted(oldSize, items.size - oldSize)
    }

    /**
     * Updates message by its id.
     *
     * @param message updated message object.
     */
    fun update(message: Message): Boolean {
        return update(message.messageId, message)
    }

    /**
     * Updates message by old identifier (use this method if id has changed). Otherwise use [.update]
     *
     * @param oldId      an identifier of message to update.
     * @param newMessage new message object.
     */
    fun update(oldId: String, newMessage: Message): Boolean {
        val position = getMessagePositionById(oldId)
        return if (position >= 0) {
            val element: Wrapper<Message> =
                Wrapper(newMessage)
            items[position] = element
            notifyItemChanged(position)
            true
        } else {
            false
        }
    }

    /**
     * Moves the elements position from current to start
     *
     * @param newMessage new message object.
     */
    fun updateAndMoveToStart(newMessage: Message) {
        val position = getMessagePositionById(newMessage.messageId)
        if (position >= 0) {
            val element: Wrapper<Message> = Wrapper(newMessage)
            items.removeAt(position)
            items.add(0, element)
            notifyItemMoved(position, 0)
            notifyItemChanged(0)
        }
    }

    /**
     * Updates message by its id if it exists, add to start if not
     *
     * @param message message object to insert or update.
     */
    fun upsert(message: Message) {
        if (!update(message)) {
            addToStart(message, false)
        }
    }

    /**
     * Updates and moves to start if message by its id exists and if specified move to start, if not
     * specified the item stays at current position and updated
     *
     * @param message message object to insert or update.
     */
    fun upsert(message: Message, moveToStartIfUpdate: Boolean) {
        if (moveToStartIfUpdate) {
            if (getMessagePositionById(message.messageId) > 0) {
                updateAndMoveToStart(message)
            } else {
                upsert(message)
            }
        } else {
            upsert(message)
        }
    }

    /**
     * Deletes message.
     *
     * @param message message to delete.
     */
    fun delete(message: Message) {
        deleteById(message.messageId)
    }

    /**
     * Deletes messages list.
     *
     * @param messages messages list to delete.
     */
    fun delete(messages: List<Message>) {
        var result = false
        for (message in messages) {
            val index = getMessagePositionById(message.messageId)
            if (index >= 0) {
                items.removeAt(index)
                notifyItemRemoved(index)
                result = true
            }
        }
        if (result) {
            recountDateHeaders()
        }
    }

    /**
     * Deletes message by its identifier.
     *
     * @param id identifier of message to delete.
     */
    fun deleteById(id: String) {
        val index = getMessagePositionById(id)
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
            recountDateHeaders()
        }
    }

    /**
     * Deletes messages by its identifiers.
     *
     * @param ids array of identifiers of messages to delete.
     */
    fun deleteByIds(ids: Array<String>) {
        var result = false
        for (id in ids) {
            val index = getMessagePositionById(id)
            if (index >= 0) {
                items.removeAt(index)
                notifyItemRemoved(index)
                result = true
            }
        }
        if (result) {
            recountDateHeaders()
        }
    }

    /**
     * Returns `true` if, and only if, messages count in adapter is non-zero.
     *
     * @return `true` if size is 0, otherwise `false`
     */
    val isEmpty: Boolean
        get() = items.isEmpty()
    /**
     * Clears the messages list.
     */
    /**
     * Clears the messages list. With notifyDataSetChanged
     */
    @JvmOverloads
    fun clear(notifyDataSetChanged: Boolean = true) {
        items.clear()
        if (notifyDataSetChanged) {
            notifyDataSetChanged()
        }
    }

    /**
     * Enables selection mode.
     *
     * @param selectionListener listener for selected items count. To get selected messages use [.getSelectedMessages].
     */
    fun enableSelectionMode(selectionListener: SelectionListener?) {
        requireNotNull(selectionListener) { "SelectionListener must not be null. Use `disableSelectionMode()` if you want tp disable selection mode" }
        this.selectionListener = selectionListener
    }

    /**
     * Disables selection mode and removes [SelectionListener].
     */
    fun disableSelectionMode() {
        selectionListener = null
        unselectAllItems()
    }

    /**
     * Returns the list of selected messages.
     *
     * @return list of selected messages. Empty list if nothing was selected or selection mode is disabled.
     */
    val selectedMessages: ArrayList<Message>
        get() {
            val selectedMessages = ArrayList<Message>()
            for (wrapper in items) {
                if (wrapper.item is MessageType && wrapper.isSelected) {
                    selectedMessages.add(wrapper.item as Message)
                }
            }
            return selectedMessages
        }

    /**
     * Returns selected messages text and do [.unselectAllItems] for you.
     *
     * @param formatter The formatter that allows you to format your message model when copying.
     * @param reverse   Change ordering when copying messages.
     * @return formatted text by [Formatter]. If it's `null` - `MESSAGE#toString()` will be used.
     */
    fun getSelectedMessagesText(formatter: Formatter<Message>?, reverse: Boolean): String {
        val copiedText = getSelectedText(formatter, reverse)
        unselectAllItems()
        return copiedText
    }

    /**
     * Copies text to device clipboard and returns selected messages text. Also it does [.unselectAllItems] for you.
     *
     * @param context   The context.
     * @param formatter The formatter that allows you to format your message model when copying.
     * @param reverse   Change ordering when copying messages.
     * @return formatted text by [Formatter]. If it's `null` - `MESSAGE#toString()` will be used.
     */
    fun copySelectedMessagesText(
        context: Context,
        formatter: Formatter<Message>?,
        reverse: Boolean
    ): String {
        val copiedText = getSelectedText(formatter, reverse)
        copyToClipboard(context, copiedText)
        unselectAllItems()
        return copiedText
    }

    /**
     * Unselect all of the selected messages. Notifies [SelectionListener] with zero count.
     */
    fun unselectAllItems() {
        for (i in items.indices) {
            val wrapper: Wrapper<*> = items[i]
            if (wrapper.isSelected) {
                wrapper.isSelected = false
                notifyItemChanged(i)
            }
        }
        isSelectionModeEnabled = false
        selectedItemsCount = 0
        notifySelectionChanged()
    }

    /**
     * Deletes all of the selected messages and disables selection mode.
     * Call [.getSelectedMessages] before calling this method to delete messages from your data source.
     */
    fun deleteSelectedMessages() {
        val selectedMessages: List<Message> = selectedMessages
        delete(selectedMessages)
        unselectAllItems()
    }

    /**
     * Sets click listener for item. Fires ONLY if list is not in selection mode.
     *
     * @param onMessageClickListener click listener.
     */
    fun setOnMessageClickListener(onMessageClickListener: OnMessageClickListener<Message>?) {
        this.onMessageClickListener = onMessageClickListener
    }

    /**
     * Sets click listener for message view. Fires ONLY if list is not in selection mode.
     *
     * @param onMessageViewClickListener click listener.
     */
    fun setOnMessageViewClickListener(onMessageViewClickListener: OnMessageViewClickListener<Message>?) {
        this.onMessageViewClickListener = onMessageViewClickListener
    }

    /**
     * Registers click listener for view by id
     *
     * @param viewId                     view
     * @param onMessageViewClickListener click listener.
     */
    fun registerViewClickListener(
        viewId: Int,
        onMessageViewClickListener: OnMessageViewClickListener<Message>
    ) {
        viewClickListenersArray.append(viewId, onMessageViewClickListener)
    }

    /**
     * Sets long click listener for item. Fires only if selection mode is disabled.
     *
     * @param onMessageLongClickListener long click listener.
     */
    fun setOnMessageLongClickListener(onMessageLongClickListener: OnMessageLongClickListener<Message>?) {
        this.onMessageLongClickListener = onMessageLongClickListener
    }

    /**
     * Sets long click listener for message view. Fires ONLY if selection mode is disabled.
     *
     * @param onMessageViewLongClickListener long click listener.
     */
    fun setOnMessageViewLongClickListener(onMessageViewLongClickListener: OnMessageViewLongClickListener<Message>?) {
        this.onMessageViewLongClickListener = onMessageViewLongClickListener
    }

    /**
     * Set callback to be invoked when list scrolled to top.
     *
     * @param loadMoreListener listener.
     */
    fun setLoadMoreListener(loadMoreListener: OnLoadMoreListener?) {
        this.loadMoreListener = loadMoreListener
    }

    /**
     * Sets custom [DateFormatter.Formatter] for text representation of date headers.
     */
    fun setDateHeadersFormatter(dateHeadersFormatter: DateFormatter.Formatter?) {
        this.dateHeadersFormatter = dateHeadersFormatter
    }

    /*
     * PRIVATE METHODS
     * */
    private fun recountDateHeaders() {
        val indicesToDelete: MutableList<Int> = ArrayList()
        for (i in items.indices) {
            val wrapper: Wrapper<*> = items[i]
            if (wrapper.item is Date) {
                if (i == 0) {
                    indicesToDelete.add(i)
                } else {
                    if (items[i - 1].item is Date) {
                        indicesToDelete.add(i)
                    }
                }
            }
        }
        indicesToDelete.reverse()
        for (i in indicesToDelete) {
            items.removeAt(i)
            notifyItemRemoved(i)
        }
    }

    protected fun generateDateHeaders(messages: List<Message>) {
        for (i in messages.indices) {
            val message = messages[i]
            items.add(Wrapper(message))
            if (messages.size > i + 1) {
                val nextMessage = messages[i + 1]
                if (!DateFormatter.isSameDay(message.sentDate, nextMessage.sentDate)) {
                    items.add(
                        Wrapper(
                            message.sentDate
                        )
                    )
                }
            } else {
                items.add(
                    Wrapper(
                        message.sentDate
                    )
                )
            }
        }
    }

    private fun getMessagePositionById(id: String): Int {
        for (i in items.indices) {
            val wrapper: Wrapper<*> = items[i]
            if (wrapper.item is MessageType) {
                val message = wrapper.item as Message
                if (message.messageId.contentEquals(id)) {
                    return i
                }
            }
        }
        return -1
    }

    private fun isPreviousSameDate(position: Int, dateToCompare: Date): Boolean {
        if (items.size <= position) return false
        return if (items[position].item is MessageType) {
            val previousPositionDate = (items[position].item as Message).sentDate
            DateFormatter.isSameDay(dateToCompare, previousPositionDate)
        } else false
    }

    private fun isPreviousSameAuthor(id: String, position: Int): Boolean {
        val prevPosition = position + 1
        return if (items.size <= prevPosition) false else items[prevPosition].item is MessageType
            && (items[prevPosition].item as Message).sender.id.contentEquals(id)
    }

    private fun incrementSelectedItemsCount() {
        selectedItemsCount++
        notifySelectionChanged()
    }

    private fun decrementSelectedItemsCount() {
        selectedItemsCount--
        isSelectionModeEnabled = selectedItemsCount > 0
        notifySelectionChanged()
    }

    private fun notifySelectionChanged() {
        if (selectionListener != null) {
            selectionListener!!.onSelectionChanged(selectedItemsCount)
        }
    }

    private fun notifyMessageClicked(message: Message) {
        if (onMessageClickListener != null) {
            onMessageClickListener?.onMessageClick(message)
        }
    }

    private fun notifyMessageViewClicked(view: View, message: Message) {
        if (onMessageViewClickListener != null) {
            onMessageViewClickListener?.onMessageViewClick(view, message)
        }
    }

    private fun notifyMessageLongClicked(message: Message) {
        if (onMessageLongClickListener != null) {
            onMessageLongClickListener?.onMessageLongClick(message)
        }
    }

    private fun notifyMessageViewLongClicked(view: View, message: Message) {
        if (onMessageViewLongClickListener != null) {
            onMessageViewLongClickListener?.onMessageViewLongClick(view, message)
        }
    }

    private fun getMessageClickListener(wrapper: Wrapper<Message>): View.OnClickListener {
        return View.OnClickListener { view ->
            if (selectionListener != null && isSelectionModeEnabled) {
                wrapper.isSelected = !wrapper.isSelected
                if (wrapper.isSelected) incrementSelectedItemsCount() else decrementSelectedItemsCount()
                val message: Message = wrapper.item
                notifyItemChanged(getMessagePositionById(message.messageId))
            } else {
                notifyMessageClicked(wrapper.item)
                notifyMessageViewClicked(view, wrapper.item)
            }
        }
    }

    private fun getMessageLongClickListener(wrapper: Wrapper<Message>): OnLongClickListener {
        return OnLongClickListener { view ->
            if (selectionListener == null) {
                notifyMessageLongClicked(wrapper.item)
                notifyMessageViewLongClicked(view, wrapper.item)
                true
            } else {
                isSelectionModeEnabled = true
                view.performClick()
                true
            }
        }
    }

    private fun getSelectedText(formatter: Formatter<Message>?, reverse: Boolean): String {
        val builder = StringBuilder()
        val selectedMessages = selectedMessages
        if (reverse) selectedMessages.reverse()
        for (message in selectedMessages) {
            builder.append(if (formatter == null) message.toString() else formatter.format(message))
            builder.append("\n\n")
        }
        builder.replace(builder.length - 2, builder.length, "")
        return builder.toString()
    }

    private fun copyToClipboard(context: Context, copiedText: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(copiedText, copiedText)
        clipboard.primaryClip = clip
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
        this.layoutManager = layoutManager
    }

    fun setStyle(style: MessagesListStyle?) {
        messagesListStyle = style
    }

    /*
     * WRAPPER
     * */
    inner class Wrapper<DATA> internal constructor(var item: DATA) {
        var isSelected = false
    }
    /*
     * LISTENERS
     * */
    /**
     * Interface definition for a callback to be invoked when next part of messages need to be loaded.
     */
    interface OnLoadMoreListener {
        /**
         * Fires when user scrolled to the end of list.
         *
         * @param page            next page to download.
         * @param totalItemsCount current items count.
         */
        fun onLoadMore(page: Int, totalItemsCount: Int)
    }

    /**
     * Interface definition for a callback to be invoked when selected messages count is changed.
     */
    interface SelectionListener {
        /**
         * Fires when selected items count is changed.
         *
         * @param count count of selected items.
         */
        fun onSelectionChanged(count: Int)
    }

    /**
     * Interface definition for a callback to be invoked when message item is clicked.
     */
    interface OnMessageClickListener<MESSAGE : MessageType> {
        /**
         * Fires when message is clicked.
         *
         * @param message clicked message.
         */
        fun onMessageClick(message: MESSAGE)
    }

    /**
     * Interface definition for a callback to be invoked when message view is clicked.
     */
    interface OnMessageViewClickListener<MESSAGE : MessageType> {
        /**
         * Fires when message view is clicked.
         *
         * @param message clicked message.
         */
        fun onMessageViewClick(view: View, message: MESSAGE)
    }

    /**
     * Interface definition for a callback to be invoked when message item is long clicked.
     */
    interface OnMessageLongClickListener<MESSAGE : MessageType> {
        /**
         * Fires when message is long clicked.
         *
         * @param message clicked message.
         */
        fun onMessageLongClick(message: MESSAGE)
    }

    /**
     * Interface definition for a callback to be invoked when message view is long clicked.
     */
    interface OnMessageViewLongClickListener<MESSAGE : MessageType> {
        /**
         * Fires when message view is long clicked.
         *
         * @param message clicked message.
         */
        fun onMessageViewLongClick(view: View, message: MESSAGE)
    }

    /**
     * Interface used to format your message model when copying.
     */
    interface Formatter<MESSAGE> {
        /**
         * Formats an string representation of the message object.
         *
         * @param message The object that should be formatted.
         * @return Formatted text.
         */
        fun format(message: MESSAGE): String?
    }

    companion object {
        @JvmField
        var isSelectionModeEnabled = false
    }
}
