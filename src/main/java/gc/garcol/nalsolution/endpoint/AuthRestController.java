package gc.garcol.nalsolution.endpoint;

import gc.garcol.nalsolution.payload.req.RequestSocialSignIn;
import gc.garcol.nalsolution.service.core.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author thai-van
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/social-sign-in")
    public String signInWithSocialToken(@Valid @RequestBody RequestSocialSignIn req) {

        return authService.signInWithSocialToken(req.getTokenId());

    }

}
