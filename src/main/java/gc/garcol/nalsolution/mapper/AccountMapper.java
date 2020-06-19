package gc.garcol.nalsolution.mapper;

import gc.garcol.nalsolution.entity.Account;
import gc.garcol.nalsolution.payload.req.RequestAccount;
import gc.garcol.nalsolution.payload.res.ResponseAccount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author thai-van
 **/
@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account map(RequestAccount requestAccount);

    ResponseAccount map(Account account);

}
