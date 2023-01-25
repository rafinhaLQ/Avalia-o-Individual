package uol.compass.mshistory.framework.exceptions.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}
