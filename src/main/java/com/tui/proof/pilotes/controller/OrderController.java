package com.tui.proof.pilotes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.tui.proof.pilotes.repository.CustomerRepository;
import com.tui.proof.pilotes.repository.PilotesOrderRepository;
import com.tui.proof.pilotes.security.JwtUtils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import com.tui.proof.pilotes.entity.CustomerEntity;
import com.tui.proof.pilotes.entity.PilotesOrderEntity;
import com.tui.proof.pilotes.mapper.CustomerMapper;
import com.tui.proof.pilotes.mapper.PilotesMapper;
import com.tui.proof.pilotes.dto.Customer;
import com.tui.proof.pilotes.dto.InsertCustomerRequest;
import com.tui.proof.pilotes.dto.ModifyOrderRequest;
import com.tui.proof.pilotes.dto.PilotesOrder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/pilotes")
public class OrderController {

  @Getter
  @Setter
  public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String id;
    private String userName;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, String id, String userName, String email, List<String> roles) {

      this.token = accessToken;
      this.id = id;
      this.userName = userName;
      this.email = email;
      this.roles = roles;
    }
  }

  private static Logger logger = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  PilotesOrderRepository pilotesOrderRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  JwtUtils jwtUtils;

  @GetMapping("/orders")
  public ResponseEntity<List<PilotesOrder>> getAllPilotesOrders() {
    List<PilotesOrderEntity> result;
    try {
      result = StreamSupport.stream(pilotesOrderRepository.findAll().spliterator(), false)
          .collect(Collectors.toList());
    } catch (Exception e) {
      errorlogger("Error while retrieving all orders", e);
      return ResponseEntity.internalServerError().body(null);
    }
    return ResponseEntity.ok().body(PilotesMapper.mapOrdersToDto(result));
  }

  @GetMapping("/orders/{customerPartialName}")
  public ResponseEntity<List<PilotesOrder>> getAllPilotesOrdersByName(@PathVariable String customerPartialName) {
    List<CustomerEntity> customers;
    List<PilotesOrderEntity> pilotesOrderEntityList = new ArrayList<>();
    try {
      customers = customerRepository.findCustomerEntitiesByNameContains(customerPartialName);
      customers.forEach(cust -> {
        List<PilotesOrderEntity> customerPilotes = cust.getPilotesOrderEntities();
        pilotesOrderEntityList.addAll(customerPilotes);
      });
    } catch (Exception e) {
      logAppError("Error while retrieving all orders by partial customer name", e);
      return ResponseEntity.internalServerError().body(null);
    }
    return ResponseEntity.ok().body(PilotesMapper.mapOrdersToDto(pilotesOrderEntityList));
  }

  @PostMapping("/customer")
  public ResponseEntity<Customer> insertCustomer( @Valid @RequestBody InsertCustomerRequest insertCustomerRequest){
    logger.debug("Inserting customer {}", insertCustomerRequest);
    var customerEntity = CustomerMapper.mapToEntity(insertCustomerRequest);
    customerEntity.setPilotesOrderEntities(getOrdersWithNumber(customerEntity));

    CustomerEntity savedCustomer = null;
    try {
      savedCustomer = customerRepository.save(customerEntity);
      logger.info("Customer inserted.");
    } catch (Exception e) {
      logAppError("Error while saving customer", e);
      return ResponseEntity.internalServerError().body(null);
    }

    return ResponseEntity.ok().body(CustomerMapper.mapToDto(savedCustomer));
  }

  @PostMapping("/order/{customerTelephone}/pilotes/{numberOfPilotes}")
  public ResponseEntity<PilotesOrder> insertPilotesOrder( @PathVariable String customerTelephone, @PathVariable String numberOfPilotes, @NotNull @RequestParam(required = true) String deliveryAddress){
    logger.debug("Inserting new order of {} pilotes on the customer: {}", numberOfPilotes,
        customerTelephone);

    if (isValidPilotesNumber(Integer.valueOf(numberOfPilotes))) {
      return doProcessInsert(customerTelephone, numberOfPilotes, deliveryAddress);
    } else {
      logAppError("Error while saving customer order",
          new IllegalArgumentException("The number of pilotes in not accepted"));
      return ResponseEntity.internalServerError().body(null);
    }
  }

  @PatchMapping("/order/{orderNumber}")
  public ResponseEntity<PilotesOrder> modifyPilotesOrder(@PathVariable BigDecimal orderNumber, @Valid @RequestBody ModifyOrderRequest modifyOrderRequest) {
    PilotesOrder pilotesOrderReturned = null;
    if (isValidPilotesNumber(modifyOrderRequest.getPilotesNumber())) {
      try {
        var pilotesOrder = pilotesOrderRepository.findByOrderNumber(orderNumber);
        LocalDateTime now = LocalDateTime.now();
        if(pilotesOrder.getCreatedAt().plusMinutes(5).isAfter(now)) {
          pilotesOrder.setDeliveryAddress(modifyOrderRequest.getDeliveryAddress());
          pilotesOrder.setPilotesNumber(modifyOrderRequest.getPilotesNumber());
          pilotesOrderReturned = PilotesMapper.mapToDto(pilotesOrderRepository.save(pilotesOrder));
        } else {
          throw new Exception("Cannot process the mofication of the order because of work in progress");
        }
      } catch (Exception e) {
        logAppError("Error while modifing customer order", e);
        return ResponseEntity.internalServerError().body(null);
      }
    } else {
      logAppError("Error while modifing customer order: ", new IllegalArgumentException("The number of pilotes is not accepted"));
      return ResponseEntity.internalServerError().body(null);
    }
    return ResponseEntity.ok().body(pilotesOrderReturned);
  }
  
  private void logAppError(String message, Exception e) {
    var errorMessage = new StringBuilder();
    errorMessage.append(message);
    errorMessage.append(" ");
    errorMessage.append(e.getMessage());
    logger.error(errorMessage.toString());
  }

  private ResponseEntity<PilotesOrder> doProcessInsert(String customerTelephone,
      String numberOfPilotes, String deliveryAddress) {
    try {

      var customerEntity = customerRepository.findCustomerEntitiesByTelephone(
          customerTelephone);
      var pilotesOrderEntity = new PilotesOrderEntity();
      pilotesOrderEntity.setPilotesNumber(Integer.valueOf(numberOfPilotes));
      pilotesOrderEntity.setOrderNumber(
          new BigDecimal(calculateRandomOrderNumber(pilotesOrderEntity)));
      pilotesOrderEntity.setDeliveryAddress(deliveryAddress);
      pilotesOrderEntity.setCreatedAt(LocalDateTime.now());
      pilotesOrderEntity.setTotalOrderAmount(BigDecimal.valueOf(Integer.valueOf(numberOfPilotes) * 1.33));
      customerEntity.getPilotesOrderEntities().add(pilotesOrderEntity);
      customerRepository.save(customerEntity);
      return ResponseEntity.ok().body(PilotesMapper.mapToDto(pilotesOrderEntity));

    } catch (Exception e) {
      logAppError("Error while saving customer order", e);
      return ResponseEntity.internalServerError().body(null);
    }
  }

  private List<PilotesOrderEntity> getOrdersWithNumber(CustomerEntity customerEntity) {
    List<PilotesOrderEntity> list = customerEntity.getPilotesOrderEntities()
        .stream()
        .filter(o ->
        // input validation if the number of pilotes is not 5 or
        // 10 or 15 we skip the order
        isValidPilotesNumber(o.getPilotesNumber())).collect(Collectors.toList());
    list.forEach(this::calculateRandomOrderNumber);
    return list;
  }

  private long calculateRandomOrderNumber(PilotesOrderEntity p) {
    long theRandomNum = (long) (Math.random() * Math.pow(6, 6));
    p.setOrderNumber(new BigDecimal(theRandomNum));
    return theRandomNum;
  }

  public static boolean isValidPilotesNumber(Integer numberOfPilotes) {
    return numberOfPilotes == 5 || numberOfPilotes == 10 || numberOfPilotes == 15;
  }

  @PostMapping("/generate-token")
  public ResponseEntity<?> generateToken() {
    String jwt = jwtUtils.generateJwtToken();
    List<String> roles = new ArrayList<>();
    roles.add("ADMIN");

    return ResponseEntity.ok(new JwtResponse(jwt, "1", "username",
        "email", roles));
  }

  private void errorlogger(String message, Exception e) {
    var errorMessage = new StringBuilder();
    errorMessage.append(message);
    errorMessage.append(" ");
    errorMessage.append(e.getMessage());
    logger.error(errorMessage.toString());
  }
}
