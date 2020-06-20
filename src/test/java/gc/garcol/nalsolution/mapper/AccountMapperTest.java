package gc.garcol.nalsolution.mapper;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.payload.req.RequestAccount;
import gc.garcol.nalsolution.payload.res.ResponseAccount;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author thai-van
 **/
@Slf4j
public class AccountMapperTest {

    @Test
    public void mapFromReq() {
        String name = "mapFromReq";
        RequestAccount requestAccount = new RequestAccount();
        requestAccount.setId(1L);
        requestAccount.setAvatarUrl(name);
        requestAccount.setDisplayedName(name);
        requestAccount.setEmail(name);

        Account account = AccountMapper.INSTANCE.map(requestAccount);
        log.info("AccountMapperTest -> mapFromReq. {}", account);
    }

    @Test
    public void mapToRes() {
        String name = "mapToRes";
        Account account = Account.builder()
                .id(1L)
                .avatarUrl(name)
                .displayedName(name)
                .email(name)
                .build();
        ResponseAccount responseAccount = AccountMapper.INSTANCE.map(account);
        log.info("AccountMapperTest -> mapToRes. {}", responseAccount);
    }

}
