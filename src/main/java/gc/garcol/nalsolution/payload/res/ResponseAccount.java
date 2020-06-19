package gc.garcol.nalsolution.payload.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thai-van
 **/
@Getter
@Setter
@NoArgsConstructor
public class ResponseAccount {

    private Long id;

    private String email;

    private String password;

    private String displayedName;

    private String avatarUrl;

    @Override
    public String toString() {
        return "ResponseAccount{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", displayedName='" + displayedName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
    
}
