package org.borave.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String password;
    private String username;
    private List<String> friends;

    private List<String> friendRequests;

    private Map<String, Integer> friendLevelStories;

    public User(String name, String email, String password, String username){
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.friendLevelStories = new HashMap<>();
    }
    @JsonCreator
    public static User create(@JsonProperty("name") String name,
                              @JsonProperty("email") String email,
                              @JsonProperty("password") String password,
                              @JsonProperty("username") String username
                              ) {
        return new User(name, email, password, username);
    }
    public String getId() {
        return id.toString();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addFriendLevelStories(String friendId) {
        friendLevelStories.put(friendId, friendLevelStories.getOrDefault(friendId, 0) + 1);
    }

    public int getFriendLevelStories(ObjectId friendId) {
        return friendLevelStories.getOrDefault(friendId, 0);
    }
}
