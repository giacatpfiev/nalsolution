package gc.garcol.nalsolution.service.core.impl;

import com.google.firebase.auth.UserInfo;
import gc.garcol.nalsolution.authentication.JwtProvider;
import gc.garcol.nalsolution.authentication.SimpleUserDetail;
import gc.garcol.nalsolution.authentication.UserDetailServiceImpl;
import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.service.FirebaseService;
import gc.garcol.nalsolution.service.core.AccountService;
import gc.garcol.nalsolution.service.core.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author thai-van
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final FirebaseService firebaseService;

    private final AccountService accountService;

    private final UserDetailServiceImpl userDetailsService;

    private final JwtProvider jwtProvider;

    /**
     * If email is not existed then create new account
     * @param socialToken
     * @return
     */
    @Override
    public String signInWithSocialToken(String socialToken) {

        Assert.isTrue(StringUtils.hasText(socialToken), "null social token!!!");
        UserInfo userInfo = firebaseService.extractUserInfo(socialToken);

        boolean existedEmail = accountService.existedAccount(userInfo.getEmail());

        if (!existedEmail) signUpWithSocialToken(userInfo);

        return initJwt(userInfo.getEmail());

    }

    /**
     *
     * @param userInfo
     */
    private void signUpWithSocialToken(UserInfo userInfo) {

        Account account = Account.builder()
                .email(userInfo.getEmail())
                .displayedName(userInfo.getDisplayName())
                .avatarUrl(userInfo.getPhotoUrl())
                .build();

        accountService.create(account);

    }

    /**
     *
     * @param email
     * @return
     */
    private String initJwt(String email) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        SimpleUserDetail userDetail = (SimpleUserDetail) authentication.getPrincipal();

        return jwtProvider.generateJWT(userDetail);

    }

}
