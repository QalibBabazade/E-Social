package az.babazade.esocial.dto.requests;

import lombok.Data;

@Data
public class CommentRequest {

    private Long commentId;
    private Long userId;
    private Long postId;
    private String text;
}
