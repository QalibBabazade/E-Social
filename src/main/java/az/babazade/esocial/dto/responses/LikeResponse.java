package az.babazade.esocial.dto.responses;

import az.babazade.esocial.entities.Like;
import lombok.Data;

@Data
public class LikeResponse {

    private Long id;
    private UserResponse userResponse;
    private PostResponse postResponse;

    public LikeResponse(Like entity){

        this.id = entity.getId();
        this.userResponse = new UserResponse(entity.getUser());
        this.postResponse = new PostResponse(entity.getPost());
    }
}
