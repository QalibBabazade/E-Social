package az.babazade.esocial.dto.responses;

import az.babazade.esocial.entities.Comment;
import lombok.Data;

@Data
public class CommentResponse {
    private Long commentId;
    private String text;

    public CommentResponse(Comment entity){
        this.commentId = entity.getId();
        this.text = entity.getText();
    }
}
