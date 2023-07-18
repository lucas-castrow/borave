package org.borave.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

    private String id;
    private String postedBy;
    private String content;
    private LocalDateTime sendAt;

    private String senderPhoto;

    private Integer friendLevelStories;

    public PostDTO(String id, String postedBy, String content, LocalDateTime sendAt) {
        this.id = id;
        this.postedBy = postedBy;
        this.content = content;
        this.sendAt = sendAt;
    }

    public String getId() {
        return id;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public String getContent() {
        return content;
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
