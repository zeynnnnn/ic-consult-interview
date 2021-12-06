/*
 * CustomerRepository.java
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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    public CustomerEntity findByUserId(String userId);

    public <S extends CustomerEntity> S save(S customer);

}
