package gc.garcol.nalsolution.repository;

import gc.garcol.nalsolution._configuration.NalsolutionApplicationTest;
import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.manager.IDGeneratorManager;
import gc.garcol.nalsolution.service.CommonRepositoryService;
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
public class AccountRespositoryTest {

    @Autowired private AccountRepository accountRepository;

    @Autowired private CommonRepositoryService commonRepositoryService;

    @Autowired private IDGeneratorManager idGeneratorManager;

    @Test
    public void updateAccount() {
        log.info("AccountRespositoryTest -> updateAccount. ============ INIT DATA ============");
        Long accountId = idGeneratorManager.increaseAndGet(Account.class);
        String email = "updateAccount@gmail.com";

        Account initAccount = Account.builder()
                .id(accountId)
                .avatarUrl("updateAccount avatarURL")
                .displayedName("updateAccount displayedName")
                .email(email)
                .password("updateAccount password")
                .build();

//        commonRepositoryService.runInTransaction(() -> {
//            accountRepository.save(initAccount);
//        });

        commonRepositoryService.runInSession(session -> {
            session.persist(initAccount);
        });

        log.info("AccountRespositoryTest -> updateAccount. ============ TEST ============");

        String avatarUrl = "new avatarURL";
        String displayedName = "new displayed name";
        String password = "new password";

        Account account = Account.builder()
                .id(accountId)
                .avatarUrl(avatarUrl)
                .password(password)
                .displayedName(displayedName)
                .build();

        commonRepositoryService.runInTransaction(() -> {
            accountRepository.update(account);
        });

        Account persistedAccount = accountRepository.findById(accountId).get();

        Assert.assertEquals(email, persistedAccount.getEmail());
        Assert.assertEquals(avatarUrl, persistedAccount.getAvatarUrl());
        Assert.assertEquals(displayedName, persistedAccount.getDisplayedName());
        Assert.assertEquals(password, persistedAccount.getPassword());
    }

}
