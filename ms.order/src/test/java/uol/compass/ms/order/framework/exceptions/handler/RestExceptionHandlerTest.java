package uol.compass.ms.order.framework.exceptions.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uol.compass.ms.order.framework.exceptions.InvalidCepException;
import uol.compass.ms.order.framework.exceptions.InvalidDateException;
import uol.compass.ms.order.framework.exceptions.ItemNotFoundException;
import uol.compass.ms.order.framework.exceptions.OrderNotFoundException;

@ExtendWith(MockitoExtension.class)
public class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler exceptionHandler;

    @Test
    void whenHandleAllExceptions() {
        ResponseEntity<Object> response = exceptionHandler.handleAllExceptions(new Exception());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void whenHandleInvalidCepException() {
        ResponseEntity<Object> response = exceptionHandler.handleInvalidCepException(new InvalidCepException());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void whenHandleInvalidDateException() {
        ResponseEntity<Object> response = exceptionHandler.handleInvalidDateException(new InvalidDateException());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void whenHandleOrderNotFoundException() {
        ResponseEntity<Object> response = exceptionHandler.handleOrderNotFoundException(new OrderNotFoundException());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void whenHandleItemNotFoundException() {
        ResponseEntity<Object> response = exceptionHandler.handleItemNotFoundException(new ItemNotFoundException());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }
}
