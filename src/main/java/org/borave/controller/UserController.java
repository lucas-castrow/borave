package org.borave.controller;

import org.borave.exception.ProfileException;
import org.borave.model.ApiResponse;
import org.borave.model.AuthenticationRequest;
import org.borave.model.User;
import org.borave.model.UserResponseDTO;
import org.borave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> userParams) {
        try {
            String name = userParams.get("name");
            User user = new User(userParams.get("email"), userParams.get("password"), userParams.get("username"));
            System.err.println(name);
            String pass = user.getPassword();
            userService.addUser(user, name);
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
        }catch (ProfileException ex) {
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
        }catch(ProfileException ex){
            ApiResponse response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
