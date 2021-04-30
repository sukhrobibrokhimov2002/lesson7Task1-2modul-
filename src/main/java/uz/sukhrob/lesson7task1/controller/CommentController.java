package uz.sukhrob.lesson7task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sukhrob.lesson7task1.entity.Comment;
import uz.sukhrob.lesson7task1.payload.ApiResponse;
import uz.sukhrob.lesson7task1.payload.CommentDto;
import uz.sukhrob.lesson7task1.payload.EditCommentDto;
import uz.sukhrob.lesson7task1.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PreAuthorize(value = "hasAuthority(ADD_COMMENT)")
    @PostMapping
    public ResponseEntity<?> addComment(@Valid @RequestBody CommentDto commentDto) {
        ApiResponse apiResponse = commentService.addComment(commentDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority(DELETE_COMMENT)")
    @DeleteMapping("/deleteUserComment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable long id) {
        ApiResponse apiResponse = commentService.deleteComment(id);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority(DELETE_MY_COMMENT)")
    @DeleteMapping("/deleteMyComment/{id}")
    public ResponseEntity<?> deleteMyComment(@PathVariable long id) {
        ApiResponse apiResponse = commentService.deleteMyComment(id);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority(EDIT_COMMENT)")
    @PatchMapping("/{id}")
    public ResponseEntity<?> editComment(@RequestBody EditCommentDto editCommentDto, @PathVariable long id) {
        ApiResponse apiResponse = commentService.editComment(id, editCommentDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }


    @GetMapping("/getAllComments")
    public ResponseEntity<?> getAll() {
        List<Comment> comments = commentService.getComments();
        if (comments.isEmpty()) return ResponseEntity.status(409).body(comments);
        return ResponseEntity.ok(comments);
    }
}
