package uz.sukhrob.lesson7task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sukhrob.lesson7task1.payload.ApiResponse;
import uz.sukhrob.lesson7task1.payload.PostDto;
import uz.sukhrob.lesson7task1.service.PostService;

import javax.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;


    @PreAuthorize(value = "hasAuthority(ADD_POST)")
    @PostMapping
    public ResponseEntity<?> addPost(@Valid @RequestBody PostDto postDto) {
        ApiResponse apiResponse = postService.addPost(postDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }
    @PreAuthorize(value = "hasAuthority(DELETE_POST)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = postService.delete(id);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }
    @PreAuthorize(value = "hasAuthority(EDIT_POST)")
    @PatchMapping
    public ResponseEntity<?> edit(@RequestBody Long id, @Valid @RequestBody PostDto postDto) {
        ApiResponse apiResponse = postService.edit(id, postDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }
    @PreAuthorize(value = "hasAuthority(VIEW_POST)")
    @GetMapping
    public ResponseEntity<?> getPosts() {
        Page postServicePosts = postService.getPosts();
        if (postServicePosts.isEmpty()) return ResponseEntity.status(409).body(postServicePosts);
        return ResponseEntity.ok(postServicePosts);

    }
}
