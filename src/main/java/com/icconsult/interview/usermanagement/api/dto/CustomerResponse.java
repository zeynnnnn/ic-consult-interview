/*
 * CustomerResponse.java
 *
 * (c) Copyright iC Consult GmbH, 2021
 * All Rights reserved.
 *
 * iC Consult GmbH
 * 45128 Essen
 * Germany
 *
 */

package com.icconsult.interview.usermanagement.api.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

public class CustomerResponse extends CustomerRequest {

    @NotBlank
    private final String userId;

    public CustomerResponse(String userId, String givenName, String familyName, String email) {
        super(givenName, familyName, email);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerResponse that = (CustomerResponse) o;
        return Objects.equals(userId, that.userId) && Objects.equals(getGivenName(), that.getGivenName()) && Objects.equals(getFamilyName(), that.getFamilyName()) && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, super.hashCode());
    }

}
