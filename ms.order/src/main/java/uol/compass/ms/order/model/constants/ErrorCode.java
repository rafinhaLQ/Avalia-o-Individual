package uol.compass.ms.order.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    BAD_REQUEST("Pedido inválido"),
    INVALID_PARAMETER("Paramêtro do pedido inválido"),
    INTERNAL_SERVER_ERROR("Erro interno do servidor"),

    INVALID_CEP("CEP inválido");

    private final String message;

}
