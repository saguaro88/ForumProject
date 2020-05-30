package com.javaee.projectFroum.projectForum.services.interfaces;

import com.javaee.projectFroum.projectForum.models.Post;

import java.util.List;

public interface PostService {
    Post getPostById(long id);
    List<Post> getAllPosts();
    Post addPost(Post post);
    Boolean deletePostById(long id);

}
