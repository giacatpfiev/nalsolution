package gc.garcol.nalsolution.authentication;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.service.core.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author thai-van
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("UserDetailServiceImpl -> loadUserByUsername. Email: |{}|", email);
        Account account = accountService.findByEmail(email);
        return SimpleUserDetail.of(account);
    }
}
