/*
 * GlobalExceptionHandler.java
 *
 * (c) Copyright iC Consult GmbH, 2021
 * All Rights reserved.
 *
 * iC Consult GmbH
 * 45128 Essen
 * Germany
 *
 */

package com.icconsult.interview.usermanagement.api;

import com.icconsult.interview.usermanagement.api.dto.ErrorResponse;
import com.icconsult.interview.usermanagement.exception.CustomerNotFoundException;
import com.icconsult.interview.usermanagement.exception.NotAuthorizedException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Not authorized.",
                    content = @Content),
    })
    public ResponseEntity<ErrorResponse> handleNotAuthorized(NotAuthorizedException e) {
        logger.info(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN.getReasonPhrase(), "Not Authorized."), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something went terribly, terribly wrong.",
                    content = @Content),
    })
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception e) {
        logger.info(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unknown error."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User ID is NOT found in the database.",
                    content = @Content),
    })
    public ResponseEntity<ErrorResponse> handleNotFoundError(CustomerNotFoundException e) {
        logger.info(e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), "Not Found error."), HttpStatus.NOT_FOUND);
    }

}
