package az.babazade.esocial.dto.responses;

import az.babazade.esocial.entities.Post;
import lombok.Data;

@Data
public class PostResponse {

    private Long postId;
    private String title;
    private String text;

    public PostResponse(Post entity){
        this.postId = entity.getId();
        this.title = entity.getTitle();
        this.text = entity.getText();

    }
}
