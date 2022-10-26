package az.babazade.esocial.controllers;

import az.babazade.esocial.dto.requests.LikeRequest;
import az.babazade.esocial.dto.responses.LikeResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;
import az.babazade.esocial.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/likes")
public class LikeController {

    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public Response<List<LikeResponse>> getAllLikes(@RequestParam Optional<Long> userId,
                                                    @RequestParam Optional<Long> postId) {
        return likeService.getAllLikes(userId, postId);
    }

    @GetMapping(value = "/{likeId}")
    public Response<LikeResponse> getLikeById(@PathVariable Long likeId){
        return likeService.getLikeById(likeId);
    }

    @PostMapping
    public StatusAllResponse createLike(@RequestBody LikeRequest likeRequest){
        return likeService.createLike(likeRequest);
    }

  /*  @PutMapping
    public StatusAllResponse updateLike(@RequestBody LikeRequest likeRequest){
        return likeService.updateLike(likeRequest);
    }*/

    @DeleteMapping(value = "/{likeId}")
    public StatusAllResponse deleteLike(@PathVariable Long likeId){
        return likeService.deleteLike(likeId);
    }
}
