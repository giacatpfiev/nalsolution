package gc.garcol.nalsolution.payload.req;

import gc.garcol.nalsolution.enums.page.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author thai-van
 **/
@Getter
@NoArgsConstructor
public class RequestWorkPage {

    @NotNull
    @NotBlank
    private String sortBy;

    @NotNull
    private OrderBy orderBy;

    @PositiveOrZero
    private Integer pageIndex;

    @Positive
    private Integer pageSize;

    @Override
    public String toString() {
        return "RequestWorkPage{" +
                ", sortBy='" + sortBy + '\'' +
                ", orderBy=" + orderBy +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                '}';
    }
}
