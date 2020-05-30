package com.javaee.projectFroum.projectForum.repositories;

import com.javaee.projectFroum.projectForum.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(long id);

}
