package gc.garcol.nalsolution.service.core;

/**
 * @author thai-van
 **/
public interface AuthService {

    /**
     *
     * @param socialToken
     * @return JWT
     */
    String signInWithSocialToken(String socialToken);

}
