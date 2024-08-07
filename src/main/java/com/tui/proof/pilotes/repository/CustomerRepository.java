package com.tui.proof.pilotes.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.tui.proof.pilotes.entity.CustomerEntity;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

    CustomerEntity findCustomerEntitiesByTelephone(String telephone);
    List<CustomerEntity> findCustomerEntitiesByNameContains(String customerPartialName);
  
  }