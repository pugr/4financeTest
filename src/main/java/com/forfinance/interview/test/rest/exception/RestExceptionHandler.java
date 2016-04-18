package com.forfinance.interview.test.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.forfinance.interview.test.domain.exception.LoanRiskAnalysisException;

/**
 * Exception handler that translates selected exceptions to HTTP statuses.
 * 
 * @author Jan Koren
 *
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpStatusMessage> handleIllegalStateException(IllegalStateException e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpStatusMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoanRiskAnalysisException.class)
    public ResponseEntity<HttpStatusMessage> handleLoanRiskAnalysisException(LoanRiskAnalysisException e) {
        return handleException(e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<HttpStatusMessage> handleException(Exception e, HttpStatus httpStatus) {
        HttpStatusMessage httpStatusMessage = new HttpStatusMessage(e.getMessage());
        return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON_UTF8).body(httpStatusMessage);
    }
}
