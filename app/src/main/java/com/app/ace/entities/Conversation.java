package com.app.ace.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 5/20/2017.
 */

public class Conversation {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sender_id")
    @Expose
    private Integer senderId;
    @SerializedName("receiver_id")
    @Expose
    private Integer receiverId;
    @SerializedName("message_id")
    @Expose
    private Integer messageId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("sender_flag")
    @Expose
    private Integer senderFlag;
    @SerializedName("receiver_flag")
    @Expose
    private Integer receiverFlag;
    @SerializedName("sender_block")
    @Expose
    private Integer senderBlock;
    @SerializedName("receiver_block")
    @Expose
    private Integer receiverBlock;
    @SerializedName("sender_mute")
    @Expose
    private Integer senderMute;
    @SerializedName("receiver_mute")
    @Expose
    private Integer receiverMute;
    @SerializedName("is_following")
    @Expose
    private Integer isFollowing;
    public Integer getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(Integer isFollowing) {
        this.isFollowing = isFollowing;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getSenderFlag() {
        return senderFlag;
    }

    public void setSenderFlag(Integer senderFlag) {
        this.senderFlag = senderFlag;
    }

    public Integer getReceiverFlag() {
        return receiverFlag;
    }

    public void setReceiverFlag(Integer receiverFlag) {
        this.receiverFlag = receiverFlag;
    }

    public Integer getSenderBlock() {
        return senderBlock;
    }

    public void setSenderBlock(Integer senderBlock) {
        this.senderBlock = senderBlock;
    }

    public Integer getReceiverBlock() {
        return receiverBlock;
    }

    public void setReceiverBlock(Integer receiverBlock) {
        this.receiverBlock = receiverBlock;
    }

    public Integer getSenderMute() {
        return senderMute;
    }

    public void setSenderMute(Integer senderMute) {
        this.senderMute = senderMute;
    }

    public Integer getReceiverMute() {
        return receiverMute;
    }

    public void setReceiverMute(Integer receiverMute) {
        this.receiverMute = receiverMute;
    }
}
