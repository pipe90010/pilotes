package com.tui.proof.pilotes.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tui.proof.pilotes.dto.PilotesOrder;
import com.tui.proof.pilotes.entity.PilotesOrderEntity;
import com.tui.proof.pilotes.mapper.PilotesMapper;
import com.tui.proof.pilotes.repository.CustomerRepository;
import com.tui.proof.pilotes.repository.PilotesOrderRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PilotesOrderService {

    /*private final PilotesOrderRepository pilotesOrderRepository = null;

    private final CustomerRepository customerRepository = null;

    public Flux<PilotesOrder> getPilotesOrdersForCustomer(Long customerId) {
        List<PilotesOrderEntity> entities = pilotesOrderRepository.findAllByCustomerId(customerId);
        return Flux.fromIterable(entities).map(PilotesMapper.toDto);
    }

    public Mono<PilotesOrderEntity> processOrder(PilotesOrderEntity pilotesOrderEntity) {

        String customerTelephone = pilotesOrderEntity.getCustomerEntity().getTelephone();

        return Mono.just(customerRepository.findCustomerEntitiesByTelephone(customerTelephone))
            .flatMap(customerEntity -> {
                // Add the new order to the customer's orders
                customerEntity.getPilotesOrderEntities().add(pilotesOrderEntity);

                // Save the updated customer entity
                return customerRepository.save(customerEntity)
                    .then(pilotesOrderRepository.save(pilotesOrderEntity)); // Save the new PilotesOrderEntity
            });
    }*/
}