package org.borave.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.borave.config.JwtTokenProvider;
import org.borave.config.JwtTokenStatus;
import org.borave.exception.ProfileException;
import org.borave.model.ApiResponse;
import org.borave.model.AuthenticationRequest;
import org.borave.model.AuthenticationResponse;
import org.borave.model.Profile;
import org.borave.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private final ProfileService profileService;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, ProfileService profileService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.profileService = profileService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
          Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
          try {
              SecurityContextHolder.getContext().setAuthentication(authentication);
              Profile profile = profileService.getMyProfile(authenticationRequest.getUsername());
              String jwt = jwtTokenProvider.createToken(authentication, profile.getUserId());
              Map<String, Object> data = new HashMap<>();
              data.put("profile", profile);
              data.put("token", jwt);
              ApiResponse response = new ApiResponse<>(true, "User founded", data);

              return ResponseEntity.ok(response);
          }
          catch(ProfileException ex){
              ApiResponse response = new ApiResponse<>(false, ex.getMessage(), null);
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
          }
    }

}