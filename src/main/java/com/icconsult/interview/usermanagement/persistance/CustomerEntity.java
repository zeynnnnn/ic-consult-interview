/*
 * CustomerEntity.java
 *
 * (c) Copyright iC Consult GmbH, 2021
 * All Rights reserved.
 *
 * iC Consult GmbH
 * 45128 Essen
 * Germany
 *
 */

package com.icconsult.interview.usermanagement.persistance;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "customer_data")
public class CustomerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String givenName;

    @NotNull
    private String familyName;

    @NotNull
    private String email;

    public CustomerEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(givenName, that.givenName) && Objects.equals(familyName, that.familyName) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, givenName, familyName, email);
    }
}

