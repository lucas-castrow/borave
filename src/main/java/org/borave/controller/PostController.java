package org.borave.controller;

import org.borave.exception.UserException;
import org.borave.model.ApiResponse;
import org.borave.model.Post;
import org.borave.model.User;
import org.borave.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/createPost")
    public ResponseEntity<ApiResponse> createPost(@RequestBody Map<String, Object> request) {
        try {
            String postedBy = (String) request.get("postedBy");
            String content = (String) request.get("content");
            List<String> authorizedUsernames = (List<String>) request.get("authorizedUsernames");

            Post createdPost = postService.createPost(postedBy, content, authorizedUsernames);
            ApiResponse response = new ApiResponse(true, "Post created successfully", createdPost);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ApiResponse response = new ApiResponse(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
