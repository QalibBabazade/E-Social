package az.babazade.esocial.services.impl;

import az.babazade.esocial.dto.requests.CommentRequest;
import az.babazade.esocial.dto.responses.CommentResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;
import az.babazade.esocial.dto.responses.StatusResponse;
import az.babazade.esocial.entities.Comment;
import az.babazade.esocial.entities.Post;
import az.babazade.esocial.entities.User;
import az.babazade.esocial.enums.EnumAvailableStatus;
import az.babazade.esocial.exception.ExceptionConstant;
import az.babazade.esocial.exception.MyException;
import az.babazade.esocial.repositories.CommentRepository;
import az.babazade.esocial.repositories.PostRepository;
import az.babazade.esocial.repositories.UserRepository;
import az.babazade.esocial.services.CommentService;
import az.babazade.esocial.util.Utility;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private UserRepository userRepository;

    private PostRepository postRepository;

    private Utility utility;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
                              PostRepository postRepository, Utility utility) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.utility = utility;
    }

    @Override
    public Response<List<CommentResponse>> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        Response<List<CommentResponse>> response = new Response<>();
        try {
            List<Comment> commentList;
            if (userId.isPresent() && postId.isPresent()) {

                commentList = commentRepository.findAllByUserIdAndPostIdAndActive(userId.get(), postId.get(), EnumAvailableStatus.ACTIVE.getValue());

            } else if (userId.isPresent()) {

                commentList = commentRepository.findAllByUserIdAndActive(userId.get(), EnumAvailableStatus.ACTIVE.getValue());

            } else if (postId.isPresent()) {

                commentList = commentRepository.findAllByPostIdAndActive(postId.get(), EnumAvailableStatus.ACTIVE.getValue());

            } else {

                commentList = commentRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());

            }
            if (commentList == null) {
                throw new MyException(ExceptionConstant.COMMENT_NOT_FOUND, "Comment not found!");
            }
            List<CommentResponse> commentResponseList = commentList.stream().map(CommentResponse::new).collect(Collectors.toList());

            response.setT(commentResponseList);
            response.setStatusResponse(StatusResponse.getSuccessMessage());

        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }
        return response;
    }

    @Override
    public Response<CommentResponse> getCommentById(Long commentId) {
        Response<CommentResponse> response = new Response<>();
        try {
            utility.checkId(commentId);
            Optional<Comment> commentOptional = commentRepository.findCommentByIdAndActive(commentId, EnumAvailableStatus.ACTIVE.getValue());
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                CommentResponse commentResponse = new CommentResponse(comment);
                response.setT(commentResponse);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            } else {
                throw new MyException(ExceptionConstant.COMMENT_NOT_FOUND, "Comment not found!");
            }

        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }
        return response;
    }

    @Override
    public StatusAllResponse createComment(CommentRequest commentRequest) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            Long userId = commentRequest.getUserId();
            Long postId = commentRequest.getPostId();
            String text = commentRequest.getText();
            utility.checkComment(userId,postId,text);
            Optional<User> user = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.getValue());
            Optional<Post> post = postRepository.findPostByIdAndActive(postId, EnumAvailableStatus.ACTIVE.getValue());
            if (user.isPresent() && post.isPresent()) {
                Comment comment = new Comment();
                comment.setUser(user.get());
                comment.setPost(post.get());
                comment.setText(text);
                commentRepository.save(comment);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            }else if(!user.isPresent()){
                throw new MyException(ExceptionConstant.USER_NOT_FOUND,"User not found!");
            }else{
                throw new MyException(ExceptionConstant.POST_NOT_FOUND,"Post not found!");
            }

        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }
        return response;
    }

    @Override
    public StatusAllResponse updateComment(CommentRequest commentRequest) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            Long commentId = commentRequest.getCommentId();
            String text = commentRequest.getText();
            utility.checkUpdate(commentId,text);
            Optional<Comment> commentOptional = commentRepository.findCommentByIdAndActive(commentId, EnumAvailableStatus.ACTIVE.getValue());
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                comment.setText(text);
                commentRepository.save(comment);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            }else{
                throw new MyException(ExceptionConstant.COMMENT_NOT_FOUND,"Comment not found!");
            }
        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }
        return response;
    }

    @Override
    public StatusAllResponse deleteComment(Long commentId) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            utility.checkId(commentId);
            Optional<Comment> commentOptional = commentRepository.findCommentByIdAndActive(commentId, EnumAvailableStatus.ACTIVE.getValue());
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                comment.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                commentRepository.save(comment);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            }else{
                throw new MyException(ExceptionConstant.COMMENT_NOT_FOUND,"Comment not found!");
            }

        } catch (MyException exception) {
            response.setStatusResponse(new StatusResponse(exception.getCode(), exception.getMessage()));
            exception.printStackTrace();
        } catch (Exception exception) {
            response.setStatusResponse(new StatusResponse(ExceptionConstant.INTERNAL_EXCEPTION, "Internal Exception!"));
            exception.printStackTrace();
        }
        return response;
    }
}
