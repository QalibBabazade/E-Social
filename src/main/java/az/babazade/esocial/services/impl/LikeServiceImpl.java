package az.babazade.esocial.services.impl;

import az.babazade.esocial.dto.requests.LikeRequest;
import az.babazade.esocial.dto.responses.LikeResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;
import az.babazade.esocial.dto.responses.StatusResponse;
import az.babazade.esocial.entities.Like;
import az.babazade.esocial.entities.Post;
import az.babazade.esocial.entities.User;
import az.babazade.esocial.enums.EnumAvailableStatus;
import az.babazade.esocial.exception.ExceptionConstant;
import az.babazade.esocial.exception.MyException;
import az.babazade.esocial.repositories.LikeRepository;
import az.babazade.esocial.repositories.PostRepository;
import az.babazade.esocial.repositories.UserRepository;
import az.babazade.esocial.services.LikeService;
import az.babazade.esocial.util.Utility;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    private LikeRepository likeRepository;

    private UserRepository userRepository;

    private PostRepository postRepository;

    private Utility utility;

    public LikeServiceImpl(LikeRepository likeRepository, UserRepository userRepository,
                           PostRepository postRepository, Utility utility) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.utility = utility;
    }

    @Override
    public Response<List<LikeResponse>> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        Response<List<LikeResponse>> response = new Response<>();
        try {
            List<Like> likeList;
            if (userId.isPresent() && postId.isPresent()) {

                likeList = likeRepository.findAllByUserIdAndPostIdAndActive(userId.get(), postId.get(), EnumAvailableStatus.ACTIVE.getValue());

            } else if (userId.isPresent()) {

                likeList = likeRepository.findAllByUserIdAndActive(userId.get(), EnumAvailableStatus.ACTIVE.getValue());

            } else if (postId.isPresent()) {

                likeList = likeRepository.findAllByPostIdAndActive(postId.get(), EnumAvailableStatus.ACTIVE.getValue());

            } else {

                likeList = likeRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());

            }

            if (likeList == null) {
                throw new MyException(ExceptionConstant.LIKE_NOT_FOUND, "Like not found!");
            }
                List<LikeResponse> likeResponseList = likeList.stream().map(LikeResponse::new).collect(Collectors.toList());

            response.setT(likeResponseList);
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
    public Response<LikeResponse> getLikeById(Long likeId) {
        Response<LikeResponse> response = new Response<>();
        try {
            utility.checkId(likeId);
            Optional<Like> likeOptional = likeRepository.findLikeByIdAndActive(likeId, EnumAvailableStatus.ACTIVE.getValue());
            if (likeOptional.isPresent()) {
                Like like = likeOptional.get();
                LikeResponse likeResponse = new LikeResponse(like);
                response.setT(likeResponse);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            } else {

                throw new MyException(ExceptionConstant.LIKE_NOT_FOUND, "Like not found!");
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
    public StatusAllResponse createLike(LikeRequest likeRequest) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            Long userId = likeRequest.getUserId();
            Long postId = likeRequest.getPostId();
            if (userId == null || postId == null) {
                throw new MyException(ExceptionConstant.INVALID_REQUEST_DATA, "Invalid request data!");
            }
            Optional<User> userOptional = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.getValue());
            Optional<Post> postOptional = postRepository.findPostByIdAndActive(postId, EnumAvailableStatus.ACTIVE.getValue());
            if (userOptional.isPresent() && postOptional.isPresent()) {
                User user = userOptional.get();
                Post post = postOptional.get();
                Like like = new Like();
                like.setUser(user);
                like.setPost(post);
                likeRepository.save(like);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            } else if (!userOptional.isPresent()) {
                throw new MyException(ExceptionConstant.USER_NOT_FOUND, "User not found!");
            } else {
                throw new MyException(ExceptionConstant.POST_NOT_FOUND, "Post not found!");
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
    public StatusAllResponse deleteLike(Long likeId) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            utility.checkId(likeId);
            Optional<Like> likeOptional = likeRepository.findLikeByIdAndActive(likeId, EnumAvailableStatus.ACTIVE.getValue());
            if (likeOptional.isPresent()) {
                Like like = likeOptional.get();
                like.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                likeRepository.save(like);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            }else{
                throw new MyException(ExceptionConstant.LIKE_NOT_FOUND, "Like not found!");
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
