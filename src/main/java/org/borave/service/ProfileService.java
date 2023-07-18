package org.borave.service;


import org.borave.exception.ProfileException;
import org.borave.model.Profile;
import org.borave.model.ProfileDTO;
import org.borave.model.User;
import org.borave.repository.ProfileRepository;
import org.borave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public ProfileDTO getProfile(String id){
        Profile userProfile = profileRepository.findById(id);
        if (userProfile == null){
            throw new ProfileException.ProfileNotFoundException("User profile not found");
        }
        User user = userRepository.findById(userProfile.getUserId());
        return new ProfileDTO(userProfile.getId(), user.getId(), userProfile.getName(), user.getUsername(), userProfile.getPhoto(), userProfile.getFriends());
    }

    public Profile getMyProfile(String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new ProfileException.ProfileNotFoundException("User not found");
        }
        Profile myProfile = profileRepository.findByUserId(user.getId());
        if (myProfile == null){
            throw new ProfileException.ProfileNotFoundException("User profile not found");
        }
        return myProfile;
    }
    public ProfileDTO addFriend(String id, String friendUsername) {
        Profile userProfile = profileRepository.findById(id);
        User friend = userRepository.findByUsername(friendUsername);
        if(friend == null) {
            throw new ProfileException.ProfileNotFoundException("Username not found");
        }
        Profile friendProfile = profileRepository.findByUserId(friend.getId());

        if (userProfile == null || friendProfile == null){
            throw new ProfileException.ProfileNotFoundException("User profile not found");
        }else if(friendProfile.getId().equals(id)){
            throw new ProfileException.SameProfileException("Problem! That's your username");
        }else if(friendProfile.getFriendRequests().contains(id)){
            throw new ProfileException.FriendRequestAlreadySentException("Friend request already sent");
        }

        friendProfile.getFriendRequests().add(userProfile.getId());
        profileRepository.save(friendProfile);
        return new ProfileDTO(friendProfile.getId(), friend.getId(), friendProfile.getName(), friend.getUsername(), friendProfile.getPhoto(), friendProfile.getFriends());
    }

    public boolean acceptFriendship(String id, String friendId){
        Profile userProfile = profileRepository.findById(id);
        Profile friendProfile = profileRepository.findById(friendId);
        if (userProfile == null || friendProfile == null){
            throw new ProfileException.ProfileNotFoundException("User profile not found");
        }
        if((friendProfile != null && userProfile != null) && !friendProfile.getFriends().contains(userProfile.getId())){
            friendProfile.getFriends().add(userProfile.getId());
            userProfile.getFriends().add(friendProfile.getId());
            userProfile.getFriendRequests().remove(friendProfile.getId());
            profileRepository.save(friendProfile);
            profileRepository.save(userProfile);
            return true;
        }
        return false;
    }
    public boolean declineFriendship(String id, String friendId){
        Profile userProfile = profileRepository.findById(id);
        Profile friendProfile = profileRepository.findById(friendId);
        if (userProfile == null || friendProfile == null){
            throw new ProfileException.ProfileNotFoundException("User profile not found");
        }
        if((friendProfile != null && userProfile != null) && !friendProfile.getFriends().contains(userProfile.getId())){
            userProfile.getFriendRequests().remove(friendProfile.getId());
            profileRepository.save(userProfile);
            return true;
        }
        return false;
    }

    public List<ProfileDTO> getFriends(String id){
        Profile user = profileRepository.findById(id);
        if (user == null){
            throw new ProfileException.ProfileNotFoundException("User profile not found");
        }
        List<ProfileDTO> profiles = new ArrayList<ProfileDTO>();

        for(String friendRequest : user.getFriends()){
            Profile friendProfile = profileRepository.findById(friendRequest);
            User friend = userRepository.findById(friendProfile.getUserId());

            if(friendProfile != null) {
                ProfileDTO profileDTO = new ProfileDTO(friendProfile.getId(), friend.getId(), friendProfile.getName(), friend.getUsername(), friendProfile.getPhoto(), friendProfile.getFriends());
                profileDTO.setFriendLevelStories(user.getFriendLevelStories(profileDTO.getId()));
                profiles.add(profileDTO);
            }
        }

        return profiles;
    }


    public List<ProfileDTO> getFriendsRequestsProfiles(String id){
        Profile user = profileRepository.findById(id);
        if (user == null){
            throw new ProfileException.ProfileNotFoundException("User not found");
        }
        List<ProfileDTO> profiles = new ArrayList<ProfileDTO>();

        for(String friendRequest : user.getFriendRequests()){
            Profile friendProfile = profileRepository.findById(friendRequest);
            User friend = userRepository.findById(friendProfile.getUserId());
            if(friendProfile != null) {
                profiles.add(new ProfileDTO(friendProfile.getId(), friend.getId(), friendProfile.getName(), friend.getUsername(), friendProfile.getPhoto(), friendProfile.getFriends()));
            }
        }

        return profiles;
    }

    public String getUsernameByUserId(String id){
        Profile profile = profileRepository.findById(id);
        if (profile == null){
            throw new ProfileException.ProfileNotFoundException("User not found");
        }
        return userRepository.findById(profile.getUserId()).getUsername();
    }
}
