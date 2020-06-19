package gc.garcol.nalsolution.utils;

import gc.garcol.nalsolution.enums.page.OrderBy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

/**
 * @author thai-van
 **/
public enum PanigationUtil {

    PAGINATION;

    public Pageable of(int pageIndex, int pageSize, String sortBy, OrderBy orderBy) {

        Assert.isTrue(pageIndex >= 0, "PanigationUtil -> of. Message: pageIndex: " + pageIndex);
        Assert.isTrue(pageSize > 0, "PanigationUtil -> of. Message: pageSize: " + pageSize);
        Assert.notNull(sortBy, "PanigationUtil -> of. Message: sortBy null");
        Assert.notNull(sortBy, "PanigationUtil -> of. Message: orderBy null");

        Sort sort = OrderBy.DES.equals(orderBy) ?  Sort.by(sortBy).descending() : Sort.by(sortBy);

        return PageRequest.of(pageIndex, pageSize, sort);

    }

}
