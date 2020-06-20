package gc.garcol.nalsolution.endpoint;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.mapper.AccountMapper;
import gc.garcol.nalsolution.payload.res.ResponseAccount;
import gc.garcol.nalsolution.service.core.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{accountId}")
    public ResponseEntity<ResponseAccount> getAccountById(@PathVariable("accountId") Long accountId) {

        log.info("AccountRestController -> getAccountById. Message - id: {}", accountId);
        Account account = accountService.findById(accountId);
        ResponseAccount res  = mapper.map(account);
        return ResponseEntity.ok(res);

    }

}
