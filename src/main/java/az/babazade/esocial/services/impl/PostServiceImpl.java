package az.babazade.esocial.services.impl;

import az.babazade.esocial.dto.requests.PostRequest;
import az.babazade.esocial.dto.responses.PostResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;
import az.babazade.esocial.dto.responses.StatusResponse;
import az.babazade.esocial.entities.Post;
import az.babazade.esocial.entities.User;
import az.babazade.esocial.enums.EnumAvailableStatus;
import az.babazade.esocial.exception.ExceptionConstant;
import az.babazade.esocial.exception.MyException;
import az.babazade.esocial.repositories.PostRepository;
import az.babazade.esocial.repositories.UserRepository;
import az.babazade.esocial.services.PostService;
import az.babazade.esocial.util.Utility;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private UserRepository userRepository;

    private Utility utility;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, Utility utility) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.utility = utility;
    }

    @Override
    public Response<List<PostResponse>> getAllPosts(Optional<Long> userId) {
        Response<List<PostResponse>> response = new Response<>();

        try {
            List<Post> postList;
            if (userId.isPresent()) {
                postList = postRepository.findAllByUserIdAndActive(userId.get(), EnumAvailableStatus.ACTIVE.getValue());
            } else {
                postList = postRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            }
            if (postList == null) {
                throw new MyException(ExceptionConstant.POST_NOT_FOUND, "Post not found!");
            }
            List<PostResponse> postResponseList = postList.stream().map(PostResponse::new).collect(Collectors.toList());

            response.setT(postResponseList);
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
    public Response<PostResponse> getPostById(Long postId) {
        Response<PostResponse> response = new Response<>();
        try {
            utility.checkId(postId);
            Optional<Post> postOptional = postRepository.findPostByIdAndActive(postId, EnumAvailableStatus.ACTIVE.getValue());
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                PostResponse postResponse = new PostResponse(post);
                response.setT(postResponse);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
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
    public StatusAllResponse createPost(PostRequest postRequest) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            Long userId = postRequest.getUserId();
            String title = postRequest.getTitle();
            String text = postRequest.getText();
            utility.checkPost(userId, title, text);
            Optional<User> user = userRepository.findUserByIdAndActive(userId, EnumAvailableStatus.ACTIVE.getValue());
            if (user.isPresent()) {
                Post post = new Post();
                post.setUser(user.get());
                post.setTitle(title);
                post.setText(text);
                postRepository.save(post);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
            } else {
                throw new MyException(ExceptionConstant.USER_NOT_FOUND, "User not found!");
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
    public StatusAllResponse updatePost(PostRequest postRequest) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            Long postId = postRequest.getPostId();
            String title = postRequest.getTitle();
            String text = postRequest.getText();
            utility.checkPost(postId, title, text);
            Optional<Post> postOptional = postRepository.findPostByIdAndActive(postId, EnumAvailableStatus.ACTIVE.getValue());
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                post.setTitle(title);
                post.setText(text);
                postRepository.save(post);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
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
    public StatusAllResponse deletePost(Long postId) {
        StatusAllResponse response = new StatusAllResponse();
        try {
            Optional<Post> postOptional = postRepository.findPostByIdAndActive(postId, EnumAvailableStatus.ACTIVE.getValue());
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                post.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                postRepository.save(post);
                response.setStatusResponse(StatusResponse.getSuccessMessage());
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
}
