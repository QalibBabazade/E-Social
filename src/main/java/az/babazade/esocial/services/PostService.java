package az.babazade.esocial.services;

import az.babazade.esocial.dto.requests.PostRequest;
import az.babazade.esocial.dto.responses.PostResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Response<List<PostResponse>> getAllPosts(Optional<Long> userId);

    Response<PostResponse> getPostById(Long postId);

    StatusAllResponse createPost(PostRequest postRequest);

    StatusAllResponse updatePost(PostRequest postRequest);

    StatusAllResponse deletePost(Long postId);
}
