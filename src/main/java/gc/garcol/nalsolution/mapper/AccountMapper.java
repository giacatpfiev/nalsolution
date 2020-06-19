package gc.garcol.nalsolution.mapper;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.payload.req.RequestAccount;
import gc.garcol.nalsolution.payload.res.ResponseAccount;
import org.mapstruct.Mapper;

/**
 * @author thai-van
 **/
@Mapper
public interface AccountMapper {

    Account map(RequestAccount requestAccount);

    ResponseAccount map(Account account);

}
