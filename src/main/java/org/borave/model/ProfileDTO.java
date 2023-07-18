package org.borave.model;

import java.util.List;
import java.util.Map;

public class ProfileDTO {

    private String id;

    private String userId;

    private String name;

    private String username;

    private String photo;

    private List<String> friends;

    private Integer friendLevelStories;

    public ProfileDTO(String id, String userId, String name, String username, String photo, List<String> friends) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.photo = photo;
        this.friends = friends;
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

    public List<String> getFriends() {
        return friends;
    }
}
