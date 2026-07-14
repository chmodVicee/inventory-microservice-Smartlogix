package com.SmartLogix.Handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleGeneralException() {
        // Arrange
        String errorMessage = "Database connection failed";
        Exception ex = new Exception(errorMessage);

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleGeneralException(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ocurrió un error interno", response.getBody().get("error"));
        assertEquals(errorMessage, response.getBody().get("detalle"));
    }

    @Test
    void testHandleIllegalArgumentException() {
        // Arrange
        String errorMessage = "El stock no puede ser negativo";
        IllegalArgumentException ex = new IllegalArgumentException(errorMessage);

        // Act
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleIllegalArgument(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Peticion invalida", response.getBody().get("error"));
        assertEquals(errorMessage, response.getBody().get("mensaje"));
    }
}
