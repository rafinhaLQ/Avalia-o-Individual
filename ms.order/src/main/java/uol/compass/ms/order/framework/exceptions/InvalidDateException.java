package uol.compass.ms.order.framework.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import uol.compass.ms.order.domain.model.enums.ErrorCode;

@Getter
public class InvalidDateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String details;
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    public InvalidDateException() {
        super(ErrorCode.INVALID_DATE.name());
        this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        this.errorCode = ErrorCode.INVALID_DATE;
        this.details = ErrorCode.INVALID_DATE.getMessage();
    }
}
