package br.com.fiap.coleta_plus.exception;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String message,
        Map<String, String> fieldErrors,
        List<String> details
) {
    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(Instant.now(), status, message, null, null);
    }

    public static ErrorResponse of(int status, String message, Map<String, String> fieldErrors) {
        return new ErrorResponse(Instant.now(), status, message, fieldErrors, null);
    }
}
