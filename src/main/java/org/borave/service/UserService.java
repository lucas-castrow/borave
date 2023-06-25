package org.borave.service;

import org.borave.controller.UserResponseDTO;
import org.borave.exception.UserException;
import org.borave.model.User;
import org.borave.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User addUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UserException.UsernameAlreadyExistsException("Username already exists");
        }else if(userRepository.existsByEmail(user.getEmail())){
            throw new UserException.EmailAlreadyExistsException("Email already exists");
        }
       return userRepository.save(user);
    }
    public User addFriend(String username, String friendUsername) {
        User user = userRepository.findByUsername(username);
        User friend = userRepository.findByUsername(friendUsername);

        if (user == null || friend == null){
            throw new UserException.UserNotExists("User not found");
        }
        friend.getFriendRequests().add(user.getId());
        userRepository.save(friend);
        return friend;
    }

    public boolean acceptFriend(String username, String friendUsername){
        User user = userRepository.findByUsername(username);
        User friend = userRepository.findByUsername(friendUsername);
        if((friend != null && user != null) && !friend.getFriends().contains(user.getId())){
            friend.getFriends().add(user.getId());
            user.getFriends().add(friend.getId());
            user.getFriendRequests().remove(friend.getId());
            userRepository.save(friend);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null){
            UserResponseDTO userResponse = new UserResponseDTO(user.getName(), user.getEmail(), user.getUsername());
            return userResponse;
        }else {
            throw new UserException.UserNotExists("User not found");
        }
    }
}
