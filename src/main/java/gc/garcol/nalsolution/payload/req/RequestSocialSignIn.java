package gc.garcol.nalsolution.payload.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author thai-van
 **/
@Getter
@NoArgsConstructor
public class RequestSocialSignIn {

    @NotNull
    @NotBlank
    private String tokenId;

}