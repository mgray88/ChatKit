package com.stfalcon.chatkit.sample.common.data.model;

import com.stfalcon.chatkit.commons.models.MessageKind;
import com.stfalcon.chatkit.commons.models.MessageType;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/*
 * Created by troy379 on 04.04.17.
 */
public class Message implements MessageType,
        MessageContentType.Image, /*this is for default image messages implementation*/
        MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {

    private String id;
    private String text;
    private Date createdAt;
    private User user;
    private Image image;
    private Voice voice;

    public Message(String id, User user, String text) {
        this(id, user, text, new Date());
    }

    public Message(String id, User user, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    @Override
    public String getMessageId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getSentDate() {
        return createdAt;
    }

    @Override
    public User getSender() {
        return this.user;
    }

    @Override
    public String getImageUrl() {
        return image == null ? null : image.url;
    }

    public Voice getVoice() {
        return voice;
    }

    public String getStatus() {
        return "Sent";
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    @NotNull
    @Override
    public MessageKind getKind() {
        return new MessageKind.Text(text);
    }

    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }

    public static class Voice {

        private String url;
        private int duration;

        public Voice(String url, int duration) {
            this.url = url;
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public int getDuration() {
            return duration;
        }
    }
}
