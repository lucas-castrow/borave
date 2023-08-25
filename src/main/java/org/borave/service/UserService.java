package org.borave.service;

import org.borave.model.Profile;
import org.borave.model.UserResponseDTO;
import org.borave.exception.ProfileException;
import org.borave.model.User;
import org.borave.nats.NatsService;
import org.borave.nats.NatsSubjects;
import org.borave.repository.ProfileRepository;
import org.borave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder, NatsService natsService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User addUser(User user, String name) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new ProfileException.UsernameAlreadyExistsException("Username already exists");
        }else if(userRepository.existsByEmail(user.getEmail())){
            throw new ProfileException.EmailAlreadyExistsException("Email already exists");
        }
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        User newUser = userRepository.save(user);
        Profile profile = new Profile(newUser.getId(), name);
        profileRepository.save(profile);
       return newUser;
    }

    public UserResponseDTO getUserById(String id){
        User user = userRepository.findById(id);
        if (user != null){
            UserResponseDTO userResponse = new UserResponseDTO(user.getEmail(), user.getUsername());
            return userResponse;
        }else {
            throw new ProfileException.ProfileNotFoundException("User not found");
        }
    }

    public String getUsernameById(String id){
        User user = userRepository.findById(id);
        if (user != null){
            return user.getUsername();
        }else {
            throw new ProfileException.ProfileNotFoundException("User not found");
        }
    }


    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null){
            UserResponseDTO userResponse = new UserResponseDTO(user.getEmail(), user.getUsername());
            return userResponse;
        }else {
            throw new ProfileException.ProfileNotFoundException("User not found");
        }
    }
}
