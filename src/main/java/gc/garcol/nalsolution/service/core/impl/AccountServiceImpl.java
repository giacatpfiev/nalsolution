package gc.garcol.nalsolution.service.core.impl;

import gc.garcol.nalsolution.cache.impl.AccountLRURepository;
import gc.garcol.nalsolution.configuration.cache.CacheName;
import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.exception.NotFoundException;
import gc.garcol.nalsolution.manager.IDGeneratorManager;
import gc.garcol.nalsolution.repository.AccountRepository;
import gc.garcol.nalsolution.service.CommonRepositoryService;
import gc.garcol.nalsolution.service.core.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static gc.garcol.nalsolution.utils.Assert.ASSERT;

/**
 * @author thai-van
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    static final String CACHE_ID = CacheName.ACCOUNT_ID;
    static final String CACHE_EMAIL = CacheName.ACCOUNT_EMAIL;

    private final IDGeneratorManager idGeneratorManager;

    private final AccountRepository accountRepository;

    private final CommonRepositoryService commonRepositoryService;

    private final AccountLRURepository accountLRURepository;

    @Override
    @Caching(put = {
        @CachePut(cacheNames = CACHE_ID, key = "#account.id"),
        @CachePut(cacheNames = CACHE_EMAIL, key = "#account.email")
    })
    public Account create(Account account) {

        Long accountId = idGeneratorManager.increaseAndGet(Account.class);
        account.setId(accountId);

        commonRepositoryService.runInSession(session -> session.persist(account));
        log.info("AccountServiceImpl -> create. Message - new account: {}", account);

        return account;

    }

    @Override
    @CachePut(cacheNames = CACHE_ID, key = "#account.id")
    public int update(Account account) {

        int changed = accountRepository.update(account);
        log.info("AccountServiceImpl -> update. Message - account: {}, changed: {}", account, changed);

        return changed;

    }

    @Override
    @Cacheable(cacheNames = CACHE_ID, unless = "#result == null", key = "#id")
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
    @Cacheable(cacheNames = CACHE_EMAIL, unless = "#result == null", key = "#email")
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
    public Account getByEmail(String email) {

        Account account = accountLRURepository.findById(email);

        if (ASSERT.cacheHit(account)) return account;

        return accountLRURepository.callInLock(email, () -> {
            Account rereadAccount = accountLRURepository.findById(email);

            if (ASSERT.cacheHit(rereadAccount)) return account;

            Optional<Account> accountDBOpt = accountRepository.findAccountByEmail(email);

            if (ASSERT.databaseMiss(accountDBOpt)) {
                throw new NotFoundException("AccountServiceImpl -> findByEmail. Message - email: " + email);
            }

            Account result = accountDBOpt.get();
            accountLRURepository.save(email, result);

            return result;
        });

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
    @Caching(evict = {
        @CacheEvict(cacheNames = CACHE_ID, key = "#id")
    })
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

}
