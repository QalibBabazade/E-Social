package az.babazade.esocial.services;

import az.babazade.esocial.dto.requests.CommentRequest;
import az.babazade.esocial.dto.responses.CommentResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Response<List<CommentResponse>> getAllComments(Optional<Long> userId, Optional<Long> postId);

    Response<CommentResponse> getCommentById(Long commentId);

    StatusAllResponse createComment(CommentRequest commentRequest);

    StatusAllResponse updateComment(CommentRequest commentRequest);

    StatusAllResponse deleteComment(Long commentId);
}
