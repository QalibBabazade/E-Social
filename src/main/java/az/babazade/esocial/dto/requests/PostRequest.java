package az.babazade.esocial.dto.requests;

import lombok.Data;

@Data
public class PostRequest {

    private Long postId;
    private Long userId;
    private String title;
    private String text;
}
