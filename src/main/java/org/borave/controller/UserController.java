package org.borave.controller;

import org.borave.exception.UserException;
import org.borave.model.ApiResponse;
import org.borave.model.AuthenticationRequest;
import org.borave.model.User;
import org.borave.model.UserResponseDTO;
import org.borave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    private final AuthController authController;
    @Autowired
    public UserController(UserService userService, AuthController authController) {
        this.userService = userService;
        this.authController = authController;
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            String pass = user.getPassword();
            User createdUser = userService.addUser(user);
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(user.getUsername(), pass);
            ResponseEntity<?> authenticationResponse = authController.authenticateUser(authenticationRequest);
            if (authenticationResponse.getStatusCode().is2xxSuccessful()) {
                // Usuário criado e autenticado com sucesso
                ApiResponse response = new ApiResponse<>(true, "User successfully created and logged in", authenticationResponse.getBody());
                return ResponseEntity.ok(response);
            } else {
                // Falha no processo de autenticação
                ApiResponse response = new ApiResponse<>(false, "Failed to log in after user creation", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
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
