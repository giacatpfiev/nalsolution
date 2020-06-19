package gc.garcol.nalsolution.service.core.impl;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.manager.IDGeneratorManager;
import gc.garcol.nalsolution.repository.AccountRepository;
import gc.garcol.nalsolution.service.core.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Account create(Account account) {
        Long accountId = idGeneratorManager.getAndIncrease(Account.class);
        account.setId(accountId);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public Account findById(Long id) {
        return null;
    }

    @Override
    public Account findByEmail(String email) {
        return null;
    }

    @Override
    public boolean existedAccount(Long id) {
        return false;
    }

    @Override
    public boolean existedAccount(String email) {
        return false;
    }

    @Override
    public boolean deleteAccount(Long id) {
        return false;
    }
}
