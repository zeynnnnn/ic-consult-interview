/*
 * CustomerEndpoint.java
 *
 * (c) Copyright iC Consult GmbH, 2021
 * All Rights reserved.
 *
 * iC Consult GmbH
 * 45128 Essen
 * Germany
 *
 */

package com.icconsult.interview.usermanagement.api.endpoint;

import com.icconsult.interview.usermanagement.api.dto.CustomerRequest;
import com.icconsult.interview.usermanagement.api.dto.CustomerResponse;
import com.icconsult.interview.usermanagement.behavior.CustomerManagementService;
import com.icconsult.interview.usermanagement.exception.NotAuthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping(value={"/api/v1/customer/{new}"})
@Validated
public class CustomerEndpointAdditional {

    Logger logger = LoggerFactory.getLogger(CustomerEndpointAdditional.class);

    @Autowired
    private CustomerManagementService customerService;


    @Operation(
            summary = "Add a new customer",
            description = "Adds a new customer. Used to create new customer accounts. ",
            security = @SecurityRequirement(name = "swagger_oauth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer record added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponse.class))}),
            @ApiResponse(responseCode = "403", description = "The currently authenticated principal is not permitted to add this customer's data",
                    content = @Content)})
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse putCustomer (@AuthenticationPrincipal Jwt jwt, @Pattern(regexp = "^[a-z0-9-]*$") @RequestParam("name") String name, @Pattern(regexp = "^[a-z0-9-]*$") @RequestParam("lastname") String lastname, @Pattern(regexp = "^*@*$") @RequestParam("email") String email) {
        return customerService.addCustomer(email,name,lastname, extractUserId(jwt));
    }



    String extractUserId(Jwt jwt) {
        if (jwt != null && jwt.getSubject() != null) {
            logger.trace("Obtained token: " + jwt.getTokenValue());
            logger.info("User attempting write operation: [" + jwt.getSubject() + "].");
            return jwt.getSubject();
        } else {
            throw new NotAuthorizedException("No subject assigned.");
        }
    }
}

