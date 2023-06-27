package org.borave.model;

import org.bson.types.ObjectId;

public class UserResponseDTO {
    private String name;
    private String email;
    private String username;
    private Integer friendLevelStory;

    public UserResponseDTO( String name, String email, String username) {
        this.name = name;
        this.email = email;
        this.username = username;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFriendLevelStory() {
        return friendLevelStory;
    }

    public void setFriendLevelStory(Integer friendLevelStory) {
        this.friendLevelStory = friendLevelStory;
    }
}
