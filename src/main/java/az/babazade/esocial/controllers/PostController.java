package az.babazade.esocial.controllers;

import az.babazade.esocial.dto.requests.PostRequest;
import az.babazade.esocial.dto.responses.PostResponse;
import az.babazade.esocial.dto.responses.Response;
import az.babazade.esocial.dto.responses.StatusAllResponse;
import az.babazade.esocial.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Response<List<PostResponse>> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }

    @GetMapping(value = "/{postId}")
    public Response<PostResponse> getPostById(@PathVariable Long postId){
        return  postService.getPostById(postId);
    }

    @PostMapping
    public StatusAllResponse createPost(@RequestBody PostRequest postRequest){
        return postService.createPost(postRequest);
    }

    @PutMapping
    public StatusAllResponse updatePost(@RequestBody PostRequest postRequest){
        return postService.updatePost(postRequest);
    }

    @DeleteMapping(value = "/{postId}")
    public StatusAllResponse deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }
}
