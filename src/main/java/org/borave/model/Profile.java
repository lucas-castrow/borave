package org.borave.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile {

    @Id
    private String id;

    private String userId;

    private String name;

    private String photo;
    private List<String> friends;

    private List<String> friendRequests;

    private Map<String, Integer> friendLevelStories;


    public Profile(String userId, String name){
        this.userId = userId;
        this.name = name;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.friendLevelStories = new HashMap<>();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setFriendLevelStories(Map<String, Integer> friendLevelStories) {
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

    public void setName(String name) {
        this.name = name;
    }


    public Map<String, Integer> getFriendLevelStories() {
        return friendLevelStories;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }


    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<String> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public void addFriendLevelStories(String friendId) {
        friendLevelStories.put(friendId, friendLevelStories.getOrDefault(friendId, 0) + 1);
    }

    public int getFriendLevelStories(String friendId) {
        return friendLevelStories.getOrDefault(friendId, 0);
    }


}
