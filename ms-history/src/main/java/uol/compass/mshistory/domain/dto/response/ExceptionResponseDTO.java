package uol.compass.mshistory.domain.dto.response;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import uol.compass.mshistory.domain.model.ErrorCode;

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
