package az.babazade.esocial.controllers;

import az.babazade.esocial.dto.requests.CommentRequest;
import az.babazade.esocial.dto.responses.CommentResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;
import az.babazade.esocial.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public Response<List<CommentResponse>> getAllComments(@RequestParam Optional<Long> userId,
                                                          @RequestParam Optional<Long> postId) {
        return commentService.getAllComments(userId, postId);
    }

    @GetMapping(value = "/{commentId}")
    public Response<CommentResponse> getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @PostMapping
    public StatusAllResponse createComment(@RequestBody CommentRequest commentRequest) {
        return commentService.createComment(commentRequest);
    }

    @PutMapping
    public StatusAllResponse updateComment(@RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(commentRequest);
    }

    @DeleteMapping(value = "/{commentId}")
    public StatusAllResponse deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}
