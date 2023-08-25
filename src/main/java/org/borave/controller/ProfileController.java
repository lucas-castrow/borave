package org.borave.controller;

import org.borave.config.JwtTokenProvider;
import org.borave.exception.ProfileException;
import org.borave.model.ApiResponse;
import org.borave.model.ProfileDTO;
import org.borave.service.ProfileService;
import org.borave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public ProfileController(ProfileService profileService, JwtTokenProvider jwtTokenProvider) {
        this.profileService = profileService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> profile(@PathVariable ("id") String id){
        ApiResponse response;
        try {
            ProfileDTO profile = profileService.getProfile(id);
            response = new ApiResponse<>(true, "Profile loaded", profile);
            return ResponseEntity.ok(response);
        }
        catch (ProfileException ex) {
            response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/addFriend/{id}/{friendUsername}")
    public ResponseEntity<ApiResponse> addFriend(@PathVariable("id") String id, @PathVariable("friendUsername") String friendUsername, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        ApiResponse response;
        try {
            if (!jwtTokenProvider.isRealUser(token, profileService.getUsernameByUserId(id))) {
                response = new ApiResponse<>(false, "Bearer token invalid for this user.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            ProfileDTO friend = profileService.addFriend(id, friendUsername);
            response = new ApiResponse<>(true, "Friendship request sent", friend.getName());
            return ResponseEntity.ok(response);
        }
        catch (ProfileException ex) {
            response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/acceptFriendship/{id}/{friendId}")
    public ResponseEntity<ApiResponse> acceptFriendship(@PathVariable("id") String id, @PathVariable("friendId") String friendId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        ApiResponse response;
        try {
            if (!jwtTokenProvider.isRealUser(token, profileService.getUsernameByUserId(id))) {
                response = new ApiResponse<>(false, "Bearer token invalid for this user.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            boolean success = profileService.acceptFriendship(id, friendId);
            if (success) {
                response = new ApiResponse<>(true, "User successfully added as friend", null);
                return ResponseEntity.ok(response);
            } else {
                response = new ApiResponse<>(false, "Problem to add as friend", null);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (ProfileException ex) {
            response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/declineFriendship/{id}/{friendId}")
    public ResponseEntity<ApiResponse> declineFriendship(@PathVariable("id") String id, @PathVariable("friendId") String friendId, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        ApiResponse response;
        try {
            if (!jwtTokenProvider.isRealUser(token, profileService.getUsernameByUserId(id))) {
                response = new ApiResponse<>(false, "Bearer token invalid for this user.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            boolean success = profileService.declineFriendship(id, friendId);
            if (success) {
                response = new ApiResponse<>(true, "Friendship request denied", null);
                return ResponseEntity.ok(response);
            } else {
                response = new ApiResponse<>(false, "Problem to denied friendship request", null);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (ProfileException ex) {
            response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/friendsRequests/{id}")
    public ResponseEntity<ApiResponse> getFriendsRequestsProfiles(@PathVariable("id") String id, @RequestHeader("Authorization") String authorizationHeader) {
        ApiResponse response;
        try {
            String token = authorizationHeader.substring(7);
            if (!jwtTokenProvider.isRealUser(token, profileService.getUsernameByUserId(id))) {
                response = new ApiResponse<>(false, "Bearer token invalid for this user.", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            List<ProfileDTO> profiles = profileService.getFriendsRequestsProfiles(id);
            if (profiles.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Friend request is empty", null));
            }
            response = new ApiResponse<>(true, "Friends requests", profiles);
            return ResponseEntity.ok(response);
        }  catch (ProfileException ex) {
            response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/friends/{id}")
    public ResponseEntity<ApiResponse> getFriends(@PathVariable("id") String id) {
        try {
            List<ProfileDTO> profiles = profileService.getFriends(id);
            if (profiles.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Friend requests is empty", null));
            }
            ApiResponse response = new ApiResponse<>(true, "Friends requests", profiles);
            return ResponseEntity.ok(response);
        }  catch (ProfileException ex) {
            ApiResponse response = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
