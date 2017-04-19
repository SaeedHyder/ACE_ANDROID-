package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 4/18/2017.
 */

public class MsgEnt {

    int id;
    String conversation_id;
    int sender_id;
    int receiver_id;
    String message_text;
    String status;
    String created_at;
    String updated_at;

    public Sender sender;
    public Receiver receiver;



    ArrayList<MsgEnt> msgArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }



    public ArrayList<MsgEnt> getMsgArrayList() {
        return msgArrayList;
    }

    public void setMsgArrayList(ArrayList<MsgEnt> msgArrayList) {
        this.msgArrayList = msgArrayList;
    }


}
