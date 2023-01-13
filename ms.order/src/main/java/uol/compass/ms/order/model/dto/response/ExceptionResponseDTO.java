package uol.compass.ms.order.model.dto.response;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import uol.compass.ms.order.model.constants.ErrorCode;

@Data
@AllArgsConstructor
public class ExceptionResponseDTO {

    private final String code;

    private final String message;

    private final List<String> details;

    public ExceptionResponseDTO(ErrorCode errorCode, Throwable ex) {
        this(errorCode, ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
    }

    public ExceptionResponseDTO(ErrorCode errorCode, String details) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.details = Collections.singletonList(details);
    }

    public ExceptionResponseDTO(ErrorCode errorCode, List<String> details) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.details = details;
    }
}
