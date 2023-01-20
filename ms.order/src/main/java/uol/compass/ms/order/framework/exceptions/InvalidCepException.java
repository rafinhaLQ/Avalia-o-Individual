package uol.compass.ms.order.framework.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import uol.compass.ms.order.domain.model.enums.ErrorCode;

@Getter
public class InvalidCepException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String details;
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    public InvalidCepException() {
        super(ErrorCode.INVALID_CEP.name());
        this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        this.errorCode = ErrorCode.INVALID_CEP;
        this.details = ErrorCode.INVALID_CEP.getMessage();
    }
}
