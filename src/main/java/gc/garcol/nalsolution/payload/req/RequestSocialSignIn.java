package gc.garcol.nalsolution.payload.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * equals & hashCode using for unit test.
 * @author thai-van
 **/
@Getter
@Setter
@NoArgsConstructor
public class RequestSocialSignIn {

    @NotNull
    @NotBlank
    private String tokenId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestSocialSignIn)) return false;
        RequestSocialSignIn that = (RequestSocialSignIn) o;
        return Objects.equals(tokenId, that.tokenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId);
    }
}