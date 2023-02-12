/*
 * AccessProtectedCustomerManagementService.java
 *
 * (c) Copyright iC Consult GmbH, 2021
 * All Rights reserved.
 *
 * iC Consult GmbH
 * 45128 Essen
 * Germany
 *
 */

package com.icconsult.interview.usermanagement.behavior;

import com.icconsult.interview.usermanagement.api.dto.CustomerRequest;
import com.icconsult.interview.usermanagement.api.dto.CustomerResponse;
import com.icconsult.interview.usermanagement.exception.CustomerNotFoundException;
import com.icconsult.interview.usermanagement.persistance.CustomerEntity;
import com.icconsult.interview.usermanagement.persistance.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCustomerManagementService implements CustomerManagementService {

    Logger logger = LoggerFactory.getLogger(DefaultCustomerManagementService.class);

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerResponse getCustomer(String userId) {
        CustomerEntity customerEntity = customerRepository.findByUserId(userId);
        if (customerEntity == null) {
            logger.warn("Tried to get customer entry which doesn't exist in DB. Nonexistent customer uuid: [" + userId + "].");
            throw new CustomerNotFoundException("Cannot find customer to retrieve: [" + userId + "].");
        } else {
            logger.info("Successfully retrieved customer from: [givenName=" + anonymizeString(customerEntity.getGivenName()) + ", familyName=" + anonymizeString(customerEntity.getFamilyName()) + "email=" + anonymizeString(customerEntity.getEmail()) + "].");
        }
        return toCustomerResponse(customerEntity);
    }

    @Override
    public CustomerResponse updateCustomer(String userId, String admin, CustomerRequest newCustomerEntry) {
        CustomerEntity customerEntity = customerRepository.findByUserId(userId);
        if (customerEntity == null) {
            logger.warn("Tried to update customer entry which doesn't exist in DB. Nonexistent customer uuid: [" + userId + "].");
            throw new CustomerNotFoundException("Cannot find customer for update: [" + userId + "].");
        } else {

            customerEntity.setFamilyName(newCustomerEntry.getFamilyName());
            customerEntity.setGivenName(newCustomerEntry.getGivenName());
            customerEntity.setEmail(newCustomerEntry.getEmail());
        }

        try {
            customerRepository.save(customerEntity);
            logger.info("Customer update successful, new values: [givenName=" + anonymizeString(customerEntity.getGivenName()) + ", familyName=" + anonymizeString(customerEntity.getFamilyName()) + "email=" + anonymizeString(customerEntity.getEmail()) + "].");
            return toCustomerResponse(customerEntity);
        } catch (Exception e) {
            logger.error("Error while trying to update customer [{}]", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public CustomerResponse addCustomer(String email,String name, String lastName, String admin) {
        System.out.println("Email: "+ email);
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(email);
        customerEntity.setGivenName(name);
        customerEntity.setFamilyName(lastName);
        customerRepository.save(customerEntity);
        logger.info("Successfully added customer from: [givenName=" + anonymizeString(customerEntity.getGivenName()) + ", familyName=" + anonymizeString(customerEntity.getFamilyName()) + "email=" + anonymizeString(customerEntity.getEmail()) + "].");
        return toCustomerResponse(customerEntity);
    }

    private CustomerResponse toCustomerResponse(CustomerEntity customerEntity) {
        CustomerResponse response = new CustomerResponse(customerEntity.getUserId(), customerEntity.getGivenName(), customerEntity.getFamilyName(), customerEntity.getEmail());
        return response;
    }

    private String anonymizeString(String plaintext) {
        if (plaintext == null || plaintext.isBlank() || plaintext.length() <= 2) {
            return plaintext;
        } else {
            StringBuffer output = new StringBuffer();
            output.append(plaintext.charAt(0));
            for (int i = 0; i < plaintext.length() - 2; ++i) {
                output.append('*');
            }
            output.append(plaintext.charAt(plaintext.length()-1)); //Wrong length fix. Error out of bound
            return output.toString();
        }
    }
}
