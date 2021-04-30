package uz.sukhrob.lesson7task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.sukhrob.lesson7task1.entity.Comment;
import uz.sukhrob.lesson7task1.entity.Post;
import uz.sukhrob.lesson7task1.entity.User;
import uz.sukhrob.lesson7task1.payload.ApiResponse;
import uz.sukhrob.lesson7task1.payload.CommentDto;
import uz.sukhrob.lesson7task1.payload.EditCommentDto;
import uz.sukhrob.lesson7task1.repository.CommentRepository;
import uz.sukhrob.lesson7task1.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    public ApiResponse addComment(CommentDto commentDto) {
        Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
        if (!optionalPost.isPresent()) return new ApiResponse("Post not found", false);

        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setPost(optionalPost.get());
        commentRepository.save(comment);
        return new ApiResponse("Successfully commented", true);


    }

    public ApiResponse deleteComment(Long id) {
        try {
            commentRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }

    public ApiResponse deleteMyComment(Long id) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Comment> byCreatedById = commentRepository.findByCreatedById(user.getId());
            for (Comment comment : byCreatedById) {
                if (comment.getId() == id) {
                    commentRepository.deleteById(id);
                    return new ApiResponse("Successfully deleted", true);
                }
            }
            return new ApiResponse("Comment not found", false);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }

    public ApiResponse editComment(Long id, EditCommentDto commentDto) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()) return new ApiResponse("Comment not found", false);
        Comment comment = optionalComment.get();

        comment.setText(commentDto.getText());
        commentRepository.save(comment);
        return new ApiResponse("Successfully edited", true);
    }

    public List<Comment> getComments() {
        List<Comment> all = commentRepository.findAll();
        return all;
    }
}
