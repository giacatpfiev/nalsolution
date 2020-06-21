package gc.garcol.nalsolution.service.core;

import gc.garcol.nalsolution.entity.Account;

/**
 * @author thai-van
 **/
public interface AccountService {

    /**
     *
     * @param account
     * @return Account with id
     */
    Account create(Account account);

    int update(Account account);

    Account findById(Long id);

    Account findByEmail(String email);

    Account getByEmail(String email);

    boolean existedAccount(Long id);

    boolean existedAccount(String email);

    void deleteAccount(Long id);

}
