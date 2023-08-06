package org.borave.repository;

import org.borave.model.Post;

import java.util.List;

public interface PostRepositoryCustom  {

    List<Post> findPostsByAuthorizedUsers(String profileId);

    public <S extends Post> S save(S post);

}
