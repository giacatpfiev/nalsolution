package gc.garcol.nalsolution.utils;

import gc.garcol.nalsolution.enums.page.OrderBy;
import gc.garcol.nalsolution.exception.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

/**
 * @author thai-van
 **/
public enum PanigationUtil {

    PAGINATION;

    /**
     *
     * @param pageIndex
     * @param pageSize
     * @param sortBy
     * @param orderBy
     * @return
     */
    public Pageable of(int pageIndex, int pageSize, String sortBy, OrderBy orderBy) {

        Assert.isTrue(pageIndex >= 0, "PanigationUtil -> of. Message: pageIndex: " + pageIndex);
        Assert.isTrue(pageSize > 0, "PanigationUtil -> of. Message: pageSize: " + pageSize);
        Assert.notNull(sortBy, "PanigationUtil -> of. Message: sortBy null");
        Assert.notNull(sortBy, "PanigationUtil -> of. Message: orderBy null");

        Sort sort = OrderBy.DES.equals(orderBy) ?  Sort.by(sortBy).descending() : Sort.by(sortBy);

        return PageRequest.of(pageIndex, pageSize, sort);

    }

    /**
     *
     * @param clazz
     * @param fieldName
     */
    public void ensureExistedField(Class<?> clazz, String fieldName) {
        try {
            clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            throw new BadRequestException(clazz.getName() + " does not have field " + fieldName);
        }
    }

}
