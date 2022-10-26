package az.babazade.esocial.services;

import az.babazade.esocial.dto.requests.LikeRequest;
import az.babazade.esocial.dto.responses.LikeResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;

import java.util.List;
import java.util.Optional;

public interface LikeService {

    Response<List<LikeResponse>> getAllLikes(Optional<Long> userId, Optional<Long> postId);

    Response<LikeResponse> getLikeById(Long likeId);

    StatusAllResponse createLike(LikeRequest likeRequest);

    //StatusAllResponse updateLike(LikeRequest likeRequest);

    StatusAllResponse deleteLike(Long likeId);
}
