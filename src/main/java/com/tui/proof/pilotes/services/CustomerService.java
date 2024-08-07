package com.tui.proof.pilotes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tui.proof.pilotes.entity.CustomerEntity;
import com.tui.proof.pilotes.entity.PilotesOrderEntity;
import com.tui.proof.pilotes.repository.CustomerRepository;
import com.tui.proof.pilotes.repository.PilotesOrderRepository;

import reactor.core.publisher.Mono;

@Service
public class CustomerService  {

    @Autowired
    private  CustomerRepository customerRepository;


    /*public Mono<Void> processOrder(String customerTelephone, PilotesOrderEntity pilotesOrderEntity) {
         CustomerEntity customerEntity = customerRepository.findCustomerEntitiesByTelephone(customerTelephone);
            
         .flatMap(customerEntity -> {
                customerEntity.getPilotesOrderEntities().add(pilotesOrderEntity);

                return customerRepository.save(customerEntity) // Save updated customer entity
                    .then(pilotesOrderRepository.save(pilotesOrderEntity)); // Save the new order
            })
            .then(); // Complete the Mono<Void> to signal the end of processing
    
    return null;
    }*/
}