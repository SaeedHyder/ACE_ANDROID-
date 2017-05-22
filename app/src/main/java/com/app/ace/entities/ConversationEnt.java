package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created on 5/20/2017.
 */

public class ConversationEnt {
    private Conversation Conversation;

    private ArrayList<MsgEnt>Messages ;
    public Conversation getConversation() {
        return Conversation;
    }

    public void setConversation(Conversation conversation) {
        this.Conversation = conversation;
    }

    public ArrayList<MsgEnt> getMessages() {
        return Messages;
    }

    public void setMessages(ArrayList<MsgEnt> messages) {
        Messages = messages;
    }


}
