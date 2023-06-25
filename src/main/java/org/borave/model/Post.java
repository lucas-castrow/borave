package org.borave.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//        id: Identificador único da história.
//        userId: ID do usuário que criou a história.
//        content: Conteúdo da história (imagem, vídeo ou texto).
//        timestamp: Data e hora de criação da história.
//        viewers: Lista de IDs de usuários que visualizaram a história.
//        likes: Lista de IDs de usuários que curtiram a história.
//        comments: Lista de comentários na história.
public class Post {

    @Id
    private ObjectId id;

    private String postedBy;
    private String content;
    private LocalDateTime timestamp;
    private List<String> viewers;
    private List<String> likes;
    private List<Comment> comments;
    private List<String> authorizedUsers;

    public Post(String postedBy, String content, List<String> authorizedUsers){
        this.postedBy = postedBy;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.viewers = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.authorizedUsers = authorizedUsers;
    }

    public String getId() {
        return id.toString();
    }


    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getViewers() {
        return viewers;
    }

    public void setViewers(List<String> viewers) {
        this.viewers = viewers;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getAuthorizedUsers() {
        return authorizedUsers;
    }

    public void setAuthorizedUsers(List<String> authorizedUsers) {
        this.authorizedUsers = authorizedUsers;
    }
}
