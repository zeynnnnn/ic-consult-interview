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

import javax.validation.constraints.Pattern;

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

@RestController
@RequestMapping(value={"/api/v1/customer/{uuid}"})
@Validated
public class CustomerEndpoint {

    Logger logger = LoggerFactory.getLogger(CustomerEndpoint.class);

    @Autowired
    private CustomerManagementService customerService;

    @Operation(
            summary = "Get a customer based on their cross system unique identifier",
            description = "Returns the customer object containing all the customer's data.",
            security = @SecurityRequirement(name = "swagger_oauth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer record found successfully.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponse.class))}),
            @ApiResponse(responseCode = "403", description = "The currently authenticated principal is not permitted to access this customer's data",
                    content = @Content)})
    @GetMapping()
    public CustomerResponse getCustomer(@AuthenticationPrincipal Jwt jwt, @Parameter(description = "Cross system customer id (uuid)") @Pattern(regexp = "^[a-z0-9-]*$") @PathVariable("uuid") String userId) {
        return customerService.getCustomer(userId);
    }

    @Operation(
            summary = "Update an already existing customer",
            description = "Updates an already existing customer by overwriting all values with the values provided in the request. Cannot be used to create new customer accounts. ",
            security = @SecurityRequirement(name = "swagger_oauth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer record updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponse.class))}),
            @ApiResponse(responseCode = "403", description = "The currently authenticated principal is not permitted to update this customer's data",
                    content = @Content)})
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse putCustomer (@AuthenticationPrincipal Jwt jwt, @Parameter(description = "Cross system customer id (uuid)")  @Pattern(regexp = "^[a-z0-9-]*$") @PathVariable("uuid") String userId, @RequestBody final CustomerRequest customerRequestValue) {
        return customerService.updateCustomer(userId, extractUserId(jwt), customerRequestValue);
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

