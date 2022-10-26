package az.babazade.esocial.dto.requests;

import lombok.Data;

@Data
public class LikeRequest {

    private Long userId;
    private Long postId;
}
