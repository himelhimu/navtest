package test.android.sabbir.navdrawer.models;

import java.io.Serializable;

/**
 * Created by sabbir on 11/22/17.
 */

public class ChatMessage implements Serializable {

    private String message,messageUser;
    private long messageTime;
    private String messageFrom;

    public ChatMessage() {
    }

    public ChatMessage(String message, String messageUser, long messageTime) {
        this.message = message;
        this.messageUser = messageUser;
        this.messageTime = messageTime;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                ", messageUser='" + messageUser + '\'' +
                ", messageTime=" + messageTime +
                ", messageFrom='" + messageFrom + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }
}
