package gc.garcol.nalsolution.service;

import com.google.firebase.auth.*;
import gc.garcol.nalsolution.exception.FirebaseRuntimeException;
import org.springframework.stereotype.Service;

/**
 * @author thai-van
 **/
@Service
public class FirebaseService {

    public UserInfo decodeToken(String tokenID) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(tokenID);
            return FirebaseAuth.getInstance().getUser(decodedToken.getUid());
        } catch (FirebaseAuthException e) {
            throw new FirebaseRuntimeException(e.getMessage());
        }
    }

    public String extractEmail(String tokenID) {
        UserRecord userInfo = (UserRecord) decodeToken(tokenID);
        String email = userInfo.getProviderData()[0].getEmail();
        return email;
    }

    public UserInfo extractUserInfo(String tokenID) {
        UserRecord userInfo = (UserRecord) decodeToken(tokenID);
        return userInfo.getProviderData()[0];
    }
}
