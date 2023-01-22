package uol.compass.ms.order.framework.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import uol.compass.ms.order.domain.model.enums.ErrorCode;

@Getter
public class ItemNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String details;
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    public ItemNotFoundException() {
        super(ErrorCode.ITEM_NOT_FOUND.name());
        this.httpStatus = HttpStatus.NOT_FOUND;
        this.errorCode = ErrorCode.ITEM_NOT_FOUND;
        this.details = ErrorCode.ITEM_NOT_FOUND.getMessage();
    }
}
