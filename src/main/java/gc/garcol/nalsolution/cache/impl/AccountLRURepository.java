package gc.garcol.nalsolution.cache.impl;

import gc.garcol.nalsolution.cache.repository.CacheSmartLock;
import gc.garcol.nalsolution.cache.repository.LRULockCacheRepository;
import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.manager.scheduler.SchedulerManager;
import org.springframework.stereotype.Repository;

/**
 * @author thai-van
 **/
@Repository
public class AccountLRURepository extends LRULockCacheRepository<String, Account> {

    public AccountLRURepository(SchedulerManager schedulerManager) {
        super(schedulerManager, CacheSmartLock.C_ACCOUNT_EMAIL_LOCK);
    }

}
