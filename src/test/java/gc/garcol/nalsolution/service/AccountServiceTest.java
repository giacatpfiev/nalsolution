package gc.garcol.nalsolution.service;

import gc.garcol.nalsolution._configuration.NalsolutionApplicationTest;
import gc.garcol.nalsolution.authentication.UserDetailServiceImpl;
import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.exception.NotFoundException;
import gc.garcol.nalsolution.repository.AccountRepository;
import gc.garcol.nalsolution.service.core.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author thai-van
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NalsolutionApplicationTest.class)
public class AccountServiceTest {

    @Autowired private CommonRepositoryService commonRepositoryService;

    @Autowired private AccountService accountService;

    @Autowired private AccountRepository accountRepository;

    @Autowired private UserDetailServiceImpl userDetailService;

    @Test
    public void findByIdWithExistedAccount() {
        log.info("AccountServiceTest -> findByIdWithExistedAccount. ======= INIT DATA ========");
        Account account = Account.builder()
                .displayedName("test findByIdWithExistedAccount displayedName")
                .email("test findByIdWithExistedAccount email")
                .build();

        accountService.create(account);

        log.info("AccountServiceTest -> findByIdWithExistedAccount. ======= TEST ========");
        Long id = account.getId();
        Account persistedAccount = accountService.findById(id);
        Account persistedAccount1 = accountService.findById(id);
        Account persistedAccount2 = accountService.findByEmail("test findByIdWithExistedAccount email");
        Account persistedAccount3 = accountService.findByEmail("test findByIdWithExistedAccount email");
        userDetailService.loadUserByUsername("test findByIdWithExistedAccount email");
        userDetailService.loadUserByUsername("test findByIdWithExistedAccount email");
        userDetailService.loadUserByUsername("test findByIdWithExistedAccount email");
        Assert.assertNotNull(persistedAccount);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdWithNotExistedAccount() {
        Long id = -1L;
        Account persistedAccount = accountService.findById(id);
    }

    @Test
    public void findByEmailWithExistedAccount() {
        log.info("AccountServiceTest -> findByEmailWithExistedAccount. ======= INIT DATA ========");
        Account account = Account.builder()
                .displayedName("test findByEmailWithExistedAccount displayedName")
                .email("test findByEmailWithExistedAccount email")
                .build();

        accountService.create(account);

        log.info("AccountServiceTest -> findByEmailWithExistedAccount. ======= TEST ========");
        String email = account.getEmail();
        Account persistedAccount = accountService.findByEmail(email);
        Assert.assertNotNull(persistedAccount);
    }

    @Test(expected = NotFoundException.class)
    public void findByEmailWithNotExistedAccount() {
        String email = "findByEmailWithNotExistedAccount";
        Account persistedAccount = accountService.findByEmail(email);
    }

    @Test
    public void updateExistedAccount() {
        log.info("AccountServiceTest -> updateExistedAccount. ======= INIT DATA ========");
        Account initAccount = Account.builder()
                .displayedName("test updateExistedAccount displayedName")
                .email("test updateExistedAccount email")
                .build();

        accountService.create(initAccount);

        Long id = initAccount.getId();
        String email = initAccount.getEmail();
        log.info("AccountServiceTest -> updateExistedAccount. ======= TEST ========");

        String displayedName = "updateExistedAccount";
        String password = "updateExistedAccount";
        String avatarUrl = "updateExistedAccount";

        Account account = Account.builder()
                .email("this email will not be changed!!!!")
                .displayedName(displayedName)
                .avatarUrl(avatarUrl)
                .id(id)
                .build();

        int numChanged = accountService.update(account);

        Account updatedAccount = accountService.findById(id);

        Assert.assertEquals(1, numChanged);
        Assert.assertEquals(email, updatedAccount.getEmail());
        Assert.assertEquals(avatarUrl, updatedAccount.getAvatarUrl());
        Assert.assertEquals(displayedName, updatedAccount.getDisplayedName());
    }

    @Test
    public void testUpdateNotExistedAccount() {
        Account account = Account.builder()
                .id(-1L)
                .build();

        int numChanged = accountService.update(account);
        Assert.assertEquals(0, numChanged);
    }

}
