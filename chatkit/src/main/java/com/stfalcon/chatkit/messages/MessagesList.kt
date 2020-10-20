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

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.stfalcon.chatkit.commons.models.MessageType
import com.stfalcon.chatkit.messages.MessagesListStyle.Companion.parse

/**
 * Component for displaying list of messages
 */
class MessagesList : RecyclerView {
    private var messagesListStyle: MessagesListStyle? = null

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        parseStyle(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        parseStyle(context, attrs)
    }

    /**
     * Don't use this method for setting your adapter, otherwise exception will by thrown.
     * Call [.setAdapter] instead.
     */
    override fun setAdapter(adapter: Adapter<*>?) {
        throw IllegalArgumentException("You can't set adapter to MessagesList. Use #setAdapter(MessagesListAdapter) instead.")
    }

    /**
     * Sets adapter for MessagesList
     *
     * @param adapter   Adapter. Must extend MessagesListAdapter
     * @param <Message> Message model class
     */
    fun <Message : MessageType> setAdapter(adapter: MessagesListAdapter<Message>) {
        setAdapter(adapter, true)
    }

    /**
     * Sets adapter for MessagesList
     *
     * @param adapter       Adapter. Must extend MessagesListAdapter
     * @param reverseLayout whether to use reverse layout for layout manager.
     * @param <Message>     Message model class
     */
    fun <Message : MessageType> setAdapter(
        adapter: MessagesListAdapter<Message>,
        reverseLayout: Boolean
    ) {
        val itemAnimator: SimpleItemAnimator = DefaultItemAnimator()
        itemAnimator.supportsChangeAnimations = false
        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, reverseLayout
        )
        setItemAnimator(itemAnimator)
        setLayoutManager(layoutManager)
        adapter.layoutManager = layoutManager
        adapter.messagesListStyle = messagesListStyle
        addOnScrollListener(RecyclerScrollMoreListener(layoutManager, adapter))
        super.setAdapter(adapter)
    }

    private fun parseStyle(context: Context, attrs: AttributeSet?) {
        messagesListStyle = parse(context, attrs!!)
    }
}
