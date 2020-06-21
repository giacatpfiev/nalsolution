package gc.garcol.nalsolution.endpoint;

import gc.garcol.nalsolution.authentication.SimpleUserDetail;
import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.mapper.AccountMapper;
import gc.garcol.nalsolution.payload.res.ResponseAccount;
import gc.garcol.nalsolution.service.core.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thai-van
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountRestController {

    private final AccountService accountService;

    private final AccountMapper mapper = AccountMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<ResponseAccount> getAccountById(@AuthenticationPrincipal SimpleUserDetail currentUser) {

        log.info("AccountRestController -> getAccountById. Message - id: {}", currentUser.getId());
        Account account = accountService.findById(currentUser.getId());
        ResponseAccount res  = mapper.map(account);
        return ResponseEntity.ok(res);

    }

}
