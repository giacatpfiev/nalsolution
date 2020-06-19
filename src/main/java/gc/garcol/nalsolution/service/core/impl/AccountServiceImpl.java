package gc.garcol.nalsolution.service.core.impl;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.exception.NotFoundException;
import gc.garcol.nalsolution.manager.IDGeneratorManager;
import gc.garcol.nalsolution.repository.AccountRepository;
import gc.garcol.nalsolution.service.CommonRepositoryService;
import gc.garcol.nalsolution.service.core.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author thai-van
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final IDGeneratorManager idGeneratorManager;

    private final AccountRepository accountRepository;

    private final CommonRepositoryService commonRepositoryService;

    @Override
    public Account create(Account account) {

        Long accountId = idGeneratorManager.increaseAndGet(Account.class);
        account.setId(accountId);

        commonRepositoryService.runInSession(session -> session.persist(account));
        log.info("AccountServiceImpl -> create. Message - new account: {}", account);

        return account;

    }

    @Override
    public int update(Account account) {

        int changed = accountRepository.update(account);
        log.info("AccountServiceImpl -> update. Message - account: {}, changed: {}", account, changed);

        return changed;

    }

    @Override
    public Account findById(Long id) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        return accountOpt
                .orElseThrow(
                        () -> new NotFoundException(
                                "AccountServiceImpl -> findById. Message - id: " + id
                        )
                );
    }

    @Override
    public Account findByEmail(String email) {
        Optional<Account> accountOpt = accountRepository.findAccountByEmail(email);
        return accountOpt
                .orElseThrow(
                        () -> new NotFoundException(
                                "AccountServiceImpl -> findByEmail. Message - email: " + email
                        )
                );
    }

    @Override
    public boolean existedAccount(Long id) {
        return accountRepository.existsAccountById(id);
    }

    @Override
    public boolean existedAccount(String email) {
        return accountRepository.existsAccountByEmail(email);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

}
