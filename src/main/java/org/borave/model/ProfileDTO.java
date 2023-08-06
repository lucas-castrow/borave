package org.borave.model;

import java.util.List;

public class ProfileDTO {

    private String id;

    private String userId;

    private String name;

    private String username;

    private String photo;

    private List<String> commonFriends;

    private Integer friendLevelStories;

    public ProfileDTO(String id, String userId, String name, String username, String photo, List<String> commonFriends) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.photo = photo;
        this.commonFriends = commonFriends;
    }

    public Integer getFriendLevelStories() {
        return friendLevelStories;
    }

    public void setFriendLevelStories(Integer friendLevelStories) {
        this.friendLevelStories = friendLevelStories;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoto() {
        return photo;
    }

    public List<String> getCommonFriends() {
        return commonFriends;
    }
}
