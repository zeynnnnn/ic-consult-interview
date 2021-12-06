/*
 * CustomerManagementService.java
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

public interface CustomerManagementService {

    CustomerResponse getCustomer(String userId);

    CustomerResponse updateCustomer(String userId, String admin, CustomerRequest newCustomerEntry);

}
