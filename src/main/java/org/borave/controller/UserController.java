package org.borave.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.borave.exception.UserException;
import org.borave.model.ApiResponse;
import org.borave.model.User;
import org.borave.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse> addUser(@RequestBody User user) {
        try {
            User createdUser = userService.addUser(user);
            ApiResponse response = new ApiResponse<>(true, "User successfully created", createdUser);
            return ResponseEntity.ok(response);
        }catch (UserException ex) {
            ApiResponse response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
        try {
            UserResponseDTO userResponse = userService.getUserByUsername(username);
            ApiResponse response = new ApiResponse<>(true, "User founded", userResponse);
            return ResponseEntity.ok(response);
        }catch(UserException ex){
            ApiResponse response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/addFriend/{username}/{friendUsername}")
    public ResponseEntity<ApiResponse> addFriend(@PathVariable("username") String username, @PathVariable("friendUsername") String friendUsername) {

        try {
            User friend = userService.addFriend(username, friendUsername);
            ApiResponse response = new ApiResponse<>(true, "User successfully added as friend", friend.getName());
            return ResponseEntity.ok(response);
        }
        catch (UserException ex) {
            ApiResponse response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/acceptFriend/{username}/{friendUsername}")
    public ResponseEntity<String> acceptFriend(@PathVariable("username") String username, @PathVariable("friendUsername") String friendUsername) {

        boolean success = userService.acceptFriend(username, friendUsername);
        if (success) {
            return ResponseEntity.ok("Usuário adicionado à lista de amigos.");
        } else {
            return ResponseEntity.badRequest().body("Falha ao adicionar amigo. Verifique os IDs do usuário e do amigo.");
        }
    }
}
