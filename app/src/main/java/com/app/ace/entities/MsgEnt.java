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

    //Add Fields for Inbox layout
    String message_id;
    String deleted_at;
    String sender_flag;
    String receiver_flag;
    public Message message;

    int sender_block;
    int receiver_block;
    int sender_mute;
    int receiver_mute;
    int is_following;



    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getSender_flag() {
        return sender_flag;
    }

    public void setSender_flag(String sender_flag) {
        this.sender_flag = sender_flag;
    }

    public String getReceiver_flag() {
        return receiver_flag;
    }

    public void setReceiver_flag(String receiver_flag) {
        this.receiver_flag = receiver_flag;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }







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


    public int getSender_block() {
        return sender_block;
    }

    public void setSender_block(int sender_block) {
        this.sender_block = sender_block;
    }

    public int getReceiver_block() {
        return receiver_block;
    }

    public void setReceiver_block(int receiver_block) {
        this.receiver_block = receiver_block;
    }

    public int getSender_mute() {
        return sender_mute;
    }

    public void setSender_mute(int sender_mute) {
        this.sender_mute = sender_mute;
    }

    public int getReceiver_mute() {
        return receiver_mute;
    }

    public void setReceiver_mute(int receiver_mute) {
        this.receiver_mute = receiver_mute;
    }

    public int getIs_following() {
        return is_following;
    }

    public void setIs_following(int is_following) {
        this.is_following = is_following;
    }
}
