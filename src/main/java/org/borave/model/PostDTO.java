package org.borave.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDTO {

    private String id;
    private String postedBy;

    private String postedUsername;
    private List<String> content;
    private LocalDateTime sendAt;

    private String senderPhoto;

    private Integer friendLevelStories;

    public PostDTO(String id, String postedBy, String postedUsername, String content, LocalDateTime sendAt) {
        this.id = id;
        this.postedBy = postedBy;
        this.postedUsername = postedUsername;
        this.content = new ArrayList<>();
        this.content.add(content);
        this.sendAt = sendAt;
    }

    public String getId() {
        return id;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public List<String> getContent() {
        return content;
    }

    public void addContent(String content){
        this.content.add(content);
    }
    public String getPostedUsername() {
        return postedUsername;
    }

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public Integer getFriendLevelStories() {
        return friendLevelStories;
    }

    public void setFriendLevelStories(Integer friendLevelStories) {
        this.friendLevelStories = friendLevelStories;
    }
}
