package az.babazade.esocial.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StatusAllResponse {

    @JsonProperty(value = "status")
    private StatusResponse statusResponse;
}
