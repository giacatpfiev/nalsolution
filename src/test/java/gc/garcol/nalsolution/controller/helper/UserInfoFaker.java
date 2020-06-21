package gc.garcol.nalsolution.controller.helper;

import com.google.firebase.auth.UserInfo;
import lombok.*;

/**
 * @author thai-van
 **/
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoFaker implements UserInfo {

    private String uid;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String photoUrl;
    private String providerId;

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    public static UserInfoFaker buildFaker(String basicName) {
        return UserInfoFaker.builder()
                .displayName(basicName)
                .email(basicName)
                .photoUrl(basicName)
                .phoneNumber(basicName)
                .providerId(basicName)
                .uid(basicName)
                .build();
    }
}
