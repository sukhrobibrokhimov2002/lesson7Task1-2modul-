package uz.sukhrob.lesson7task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.sukhrob.lesson7task1.entity.Post;
import uz.sukhrob.lesson7task1.payload.ApiResponse;
import uz.sukhrob.lesson7task1.payload.PostDto;
import uz.sukhrob.lesson7task1.repository.PostRepository;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public ApiResponse addPost(PostDto postDto) {
        Post post = new Post();
        post.setText(postDto.getText());
        post.setTitle(postDto.getTitle());
        post.setUrl(postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Post successfully added", true);
    }

    public ApiResponse delete(Long id) {
        try {
            Optional<Post> byId = postRepository.findById(id);
            if (!byId.isPresent()) return new ApiResponse("Post not found", false);
            postRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }

    public ApiResponse edit(Long id, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) return new ApiResponse("Post not found", false);
        Post post = optionalPost.get();
        post.setText(postDto.getText());
        post.setTitle(postDto.getTitle());
        post.setUrl(postDto.getUrl());
        postRepository.save(post);
        return new ApiResponse("Successfully edited", true);
    }

    public Page getPosts() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Post> all = postRepository.findAll(pageable);
        return all;
    }
}
