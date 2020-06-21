package gc.garcol.nalsolution.endpoint;

import gc.garcol.nalsolution.payload.req.RequestSocialSignIn;
import gc.garcol.nalsolution.service.core.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author thai-van
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/social-sign-in")
    public ResponseEntity<String> signInWithSocialToken(@Valid @RequestBody RequestSocialSignIn req) {

        log.info("AuthRestController -> signInWithSocialToken. Message {}", req.getTokenId());

        String jwt = authService.signInWithSocialToken(req.getTokenId());

        return ResponseEntity.ok(jwt);

    }

}
