package org.borave.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

//        id: Identificador único do comentário.
//        storyId: ID da história em que o comentário foi feito.
//        userId: ID do usuário que fez o comentário.
//        content: Conteúdo do comentário.
//        timestamp: Data e hora do comentário.
public class Comment {

    @Id
    private ObjectId id;
    private ObjectId postId;
    private String commentedBy;
    private String content;
    private LocalDateTime timestamp;

    public Comment(ObjectId postId, String commentedBy, String content){
        this.postId = postId;
        this.commentedBy = commentedBy;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getPostId() {
        return postId;
    }

    public void setPostId(ObjectId postId) {
        this.postId = postId;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
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
}
