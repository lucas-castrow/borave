package org.borave.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String email;
    private String password;
    private String username;

    public User(String email, String password, String username){
        this.email = email;
        this.password = password;
        this.username = username;
    }
    @JsonCreator
    public static User create(@JsonProperty("email") String email,
                              @JsonProperty("password") String password,
                              @JsonProperty("username") String username
                              ) {
        return new User(email, password, username);
    }
    public String getId() {
        return id;
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
