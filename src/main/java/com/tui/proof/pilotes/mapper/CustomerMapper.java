package com.tui.proof.pilotes.mapper;

import java.math.BigDecimal;
import java.util.*;
import java.time.*;
import java.util.stream.*;

import com.tui.proof.pilotes.dto.Customer;
import com.tui.proof.pilotes.dto.InsertCustomerRequest;
import com.tui.proof.pilotes.dto.PilotesOrder;
import com.tui.proof.pilotes.entity.CustomerEntity;
import com.tui.proof.pilotes.entity.PilotesOrderEntity;

import reactor.core.publisher.Mono;

public class CustomerMapper {


    public static CustomerEntity mapToEntity(InsertCustomerRequest dto) {
        return getCustomerEntity(dto);
    }

    protected static CustomerEntity getCustomerEntity(InsertCustomerRequest dto) {
        CustomerEntity customerEntity = null;
        if (dto != null) {
            customerEntity = new CustomerEntity();
            customerEntity.setName(dto.getName());
            customerEntity.setSurname(dto.getSurname());
            customerEntity.setTelephone(dto.getTelephoneNumber());
            CustomerEntity finalCustomerEntity = customerEntity;
            List<PilotesOrderEntity> pilotesOrderEntityList = dto.getPilotesOrders().stream()
                    .map(p -> {
                        PilotesOrderEntity pilotesOrderEntity = new PilotesOrderEntity();
                        pilotesOrderEntity.setCustomerEntity(finalCustomerEntity);
                        pilotesOrderEntity.setOrderNumber(p.getOrderNumber());
                        pilotesOrderEntity.setPilotesNumber(p.getPilotesNumber());
                        pilotesOrderEntity.setDeliveryAddress(p.getDeliveryAddress());
                        pilotesOrderEntity.setTotalOrderAmount(BigDecimal.valueOf(1.33 * p.getPilotesNumber()));
                        pilotesOrderEntity.setCreatedAt(LocalDateTime.now());
                        return pilotesOrderEntity;

                    })
                    .collect(Collectors.toList());
            customerEntity.setPilotesOrderEntities(pilotesOrderEntityList);
        }
        return customerEntity;
    }

    public static Customer mapToDto(CustomerEntity customerEntity) {
        Customer customerDTO = new Customer();
        if (customerEntity != null) {
            customerDTO.setName(customerEntity.getName());
            customerDTO.setSurname(customerEntity.getSurname());
            customerDTO.setTelephoneNumber(customerEntity.getTelephone());
            List<PilotesOrder> pilotesOrders = customerEntity.getPilotesOrderEntities()
                    .stream()
                    .map(o -> {
                        PilotesOrder pilotesOrder = new PilotesOrder();
                        pilotesOrder.setPilotesNumber(o.getPilotesNumber());
                        pilotesOrder.setOrderNumber(o.getOrderNumber());
                        pilotesOrder.setDeliveryAddress(o.getDeliveryAddress());
                        pilotesOrder.setTotalOrderAmount(o.getTotalOrderAmount());
                        pilotesOrder.setCreatedAt(o.getCreatedAt());
                        return pilotesOrder;
                    })
                    .collect(Collectors.toList());
            customerDTO.setPilotesOrders(pilotesOrders);
        }
        return customerDTO;
    }

    // Method to map CustomerEntity to Customer DTO reactively
    /*public static Mono<Customer> maptMonoCustomer (Mono<CustomerEntity> customerEntityMono) {
        return customerEntityMono.flatMap(customerEntity -> {
            // Create a new Customer DTO
            Customer customerDTO = new Customer();
            customerDTO.setName(customerEntity.getName());
            customerDTO.setSurname(customerEntity.getSurname());
            customerDTO.setTelephoneNumber(customerEntity.getTelephone());

            // Map the list of PilotesOrderEntities to a list of PilotesOrder DTOs
            List<PilotesOrder> pilotesOrders = customerEntity.getPilotesOrderEntities().stream()
                .map(PilotesMapper.toDto) // Assuming PilotesMapper::toDto maps PilotesOrderEntity to PilotesOrder
                .collect(Collectors.toList());

            customerDTO.setPilotesOrders(pilotesOrders); // Set the list of PilotesOrder DTOs to the Customer DTO

            return Mono.just(customerDTO); // Return Mono<Customer> with the populated DTO
        });
    }*/

}
