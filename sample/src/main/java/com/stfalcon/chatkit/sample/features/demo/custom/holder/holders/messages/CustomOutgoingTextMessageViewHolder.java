package com.stfalcon.chatkit.sample.features.demo.custom.holder.holders.messages;

import android.view.View;

import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.sample.common.data.model.Message;

public class CustomOutgoingTextMessageViewHolder
        extends MessageHolders.OutgoingTextMessageViewHolder<Message> {

    public CustomOutgoingTextMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);

        time.setText(message.getStatus() + " " + time.getText());
    }
}
