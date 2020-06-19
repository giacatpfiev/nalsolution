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

    Account save(Account account);

    Account findById(Long id);

    Account findByEmail(String email);

    boolean existedAccount(Long id);

    boolean existedAccount(String email);

    boolean deleteAccount(Long id);

}
