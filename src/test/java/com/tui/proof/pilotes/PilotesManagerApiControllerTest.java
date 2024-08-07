package com.tui.proof.pilotes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.proof.pilotes.controller.OrderController;
import com.tui.proof.pilotes.dto.InsertCustomerRequest;
import com.tui.proof.pilotes.dto.ModifyOrderRequest;
import com.tui.proof.pilotes.dto.PilotesOrder;
import com.tui.proof.pilotes.entity.CustomerEntity;
import com.tui.proof.pilotes.entity.PilotesOrderEntity;
import com.tui.proof.pilotes.repository.CustomerRepository;
import com.tui.proof.pilotes.repository.PilotesOrderRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PilotesManagerApiControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  PilotesOrderRepository pilotesOrderRepository;

  @BeforeEach
  void setUp() {
    // Initialization code, if needed
  }

  @Test
  public void testInsertCustomerWithCorrectNumberOfPilotes() throws Exception {

    InsertCustomerRequest insertCustomerRequest = new InsertCustomerRequest();
    insertCustomerRequest.setName("insertedname");
    insertCustomerRequest.setSurname("insertedsurname");
    insertCustomerRequest.setTelephoneNumber("1");
    List<PilotesOrder> pilotes = new ArrayList<>();
    PilotesOrder pilotesOrder1 = new PilotesOrder();
    pilotesOrder1.setPilotesNumber(5);
    pilotesOrder1.setDeliveryAddress("address");
    pilotes.add(pilotesOrder1);
    insertCustomerRequest.setPilotesOrders(pilotes);

    mvc.perform(post("/pilotes/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(insertCustomerRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("insertedname"))
            .andExpect(jsonPath("$.pilotesOrders.length()").value(1));
  }

  @Test
  public void testInsertCustomerWithIncorrectNumberOfPilotes() throws Exception {
    // When
    InsertCustomerRequest insertCustomerRequest = new InsertCustomerRequest();
    insertCustomerRequest.setName("insertedname");
    insertCustomerRequest.setSurname("insertedsurname");
    insertCustomerRequest.setTelephoneNumber("2");
    List<PilotesOrder> pilotes = new ArrayList<>();
    PilotesOrder pilotesOrder1 = new PilotesOrder();
    pilotesOrder1.setPilotesNumber(1);
    pilotesOrder1.setDeliveryAddress("address");
    pilotes.add(pilotesOrder1);
    insertCustomerRequest.setPilotesOrders(pilotes);

    mvc.perform(post("/pilotes/customer")
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(insertCustomerRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("insertedname"))
            .andExpect(jsonPath("$.pilotesOrders.length()").value(0));
  }

  @Test
  public void testInsertCustomerWithException() throws Exception {

    InsertCustomerRequest insertCustomerRequest = new InsertCustomerRequest();
    insertCustomerRequest.setName("insertedname");
    insertCustomerRequest.setSurname("insertedsurname");
    insertCustomerRequest.setTelephoneNumber("telephone4");
    List<PilotesOrder> pilotes = new ArrayList<>();
    PilotesOrder pilotesOrder1 = new PilotesOrder();
    pilotesOrder1.setPilotesNumber(1);
    pilotesOrder1.setDeliveryAddress("address");
    pilotes.add(pilotesOrder1);
    insertCustomerRequest.setPilotesOrders(pilotes);

    mvc.perform(post("/pilotes/customer")
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(insertCustomerRequest)))
            .andExpect(status().isInternalServerError());
  }
  
  @Test
  public void testGetAllPilotesOrder() throws Exception {
    CustomerEntity customer = new CustomerEntity();
    customer.setName("name");
    customer.setSurname("surname");
    customer.setTelephone("telephone");
    List<PilotesOrderEntity> pilotesOrderEntityList = new ArrayList<>();
    PilotesOrderEntity pilotesOrderEntity = new PilotesOrderEntity();
    pilotesOrderEntity.setPilotesNumber(5);
    pilotesOrderEntity.setDeliveryAddress("deliveryaddress");
    pilotesOrderEntity.setCreatedAt(LocalDateTime.now());
    pilotesOrderEntity.setOrderNumber(new BigDecimal(calculateRandomOrderNumber(pilotesOrderEntity)));
    pilotesOrderEntity.setTotalOrderAmount(new BigDecimal(1.33*pilotesOrderEntity.getPilotesNumber()));
    pilotesOrderEntityList.add(pilotesOrderEntity);
    customer.setPilotesOrderEntities(pilotesOrderEntityList);
    customerRepository.save(customer);
    mvc.perform(get("/pilotes/orders")
        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTY3ODQ2MDk5NywiZXhwIjoxNjc4NTM3Mjk3fQ.iJTIkqrSEkw2lk3Y1q9TfoUXW-3sglMV5qOUUroQ2nMl0XBzpf7mAu_cebqTonfec-iNwmBgdgEBd902xY1MLg"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(4));
    
  }

  @Test
  public void testGetAllOrdersByPartialCustomerName() throws Exception {
    CustomerEntity customer = new CustomerEntity();
    customer.setName("name");
    customer.setSurname("surname");
    customer.setTelephone("telephone2");
    List<PilotesOrderEntity> pilotesOrderEntityList = new ArrayList<>();
    PilotesOrderEntity pilotesOrderEntity = new PilotesOrderEntity();
    pilotesOrderEntity.setPilotesNumber(5);
    pilotesOrderEntity.setDeliveryAddress("deliveryaddress");
    pilotesOrderEntity.setCreatedAt(LocalDateTime.now());
    pilotesOrderEntity.setOrderNumber(new BigDecimal(calculateRandomOrderNumber(pilotesOrderEntity)));
    pilotesOrderEntity.setTotalOrderAmount(new BigDecimal(1.33 * pilotesOrderEntity.getPilotesNumber()));
    pilotesOrderEntityList.add(pilotesOrderEntity);
    pilotesOrderEntity.setCustomerEntity(customer);
    customer.setPilotesOrderEntities(pilotesOrderEntityList);
    customerRepository.save(customer);

    mvc.perform(get("/pilotes/orders/nam")
        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTY3ODQ2MDk5NywiZXhwIjoxNjc4NTM3Mjk3fQ.iJTIkqrSEkw2lk3Y1q9TfoUXW-3sglMV5qOUUroQ2nMl0XBzpf7mAu_cebqTonfec-iNwmBgdgEBd902xY1MLg"))
            .andExpect(status().isOk());

  }

  @Test
  public void testGetAllOrdersIncorrectNumberOfPilotesByPartialCustomerName() throws Exception {
    CustomerEntity customer = new CustomerEntity();
    customer.setName("name");
    customer.setSurname("surname");
    customer.setTelephone("telephone3");
    List<PilotesOrderEntity> pilotesOrderEntityList = new ArrayList<>();
    if(OrderController.isValidPilotesNumber(1)) {
      PilotesOrderEntity pilotesOrderEntity = new PilotesOrderEntity();
      pilotesOrderEntity.setPilotesNumber(1);
      pilotesOrderEntity.setDeliveryAddress("deliveryaddress");
      pilotesOrderEntity.setCreatedAt(LocalDateTime.now());
      pilotesOrderEntity.setOrderNumber(new BigDecimal(calculateRandomOrderNumber(pilotesOrderEntity)));
      pilotesOrderEntity.setTotalOrderAmount(new BigDecimal(1.33 * pilotesOrderEntity.getPilotesNumber()));
      pilotesOrderEntityList.add(pilotesOrderEntity);
      pilotesOrderEntity.setCustomerEntity(customer);
      customer.setPilotesOrderEntities(pilotesOrderEntityList);
    }
    customerRepository.save(customer);

    mvc.perform(get("/pilotes/orders/nam")
        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTY3ODQ2MDk5NywiZXhwIjoxNjc4NTM3Mjk3fQ.iJTIkqrSEkw2lk3Y1q9TfoUXW-3sglMV5qOUUroQ2nMl0XBzpf7mAu_cebqTonfec-iNwmBgdgEBd902xY1MLg"))
            .andExpect(status().isOk());
  }

  @Test
  public void testInsertPilotesOrderWithCustomerAndIncorrectNumberOfPilotes() throws Exception {
    CustomerEntity customer = new CustomerEntity();
    customer.setName("name");
    customer.setSurname("surname");
    customer.setTelephone("telephone4");
    List<PilotesOrderEntity> pilotesOrderEntityList = new ArrayList<>();
    PilotesOrderEntity pilotesOrderEntity = new PilotesOrderEntity();
    pilotesOrderEntity.setPilotesNumber(1);
    pilotesOrderEntity.setDeliveryAddress("deliveryaddress");
    pilotesOrderEntity.setCreatedAt(LocalDateTime.now());
    pilotesOrderEntity.setOrderNumber(new BigDecimal(calculateRandomOrderNumber(pilotesOrderEntity)));
    pilotesOrderEntity.setTotalOrderAmount(new BigDecimal(1.33 * pilotesOrderEntity.getPilotesNumber()));
    pilotesOrderEntityList.add(pilotesOrderEntity);
    pilotesOrderEntity.setCustomerEntity(customer);
    customer.setPilotesOrderEntities(pilotesOrderEntityList);
    customerRepository.save(customer);
    mvc.perform(post("/pilotes/order/2/pilotes/5")
                    .param("deliveryAddress", "delivery")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());

  }

  @Test
  public void testModifyOrder() throws Exception {
    CustomerEntity customer = new CustomerEntity();
    customer.setName("name");
    customer.setSurname("surname");
    customer.setTelephone("telephone5");
    List<PilotesOrderEntity> pilotesOrderEntityList = new ArrayList<>();
    PilotesOrderEntity pilotesOrderEntity = new PilotesOrderEntity();
    pilotesOrderEntity.setPilotesNumber(5);
    pilotesOrderEntity.setDeliveryAddress("deliveryaddress");
    pilotesOrderEntity.setCreatedAt(LocalDateTime.now());
    pilotesOrderEntity.setOrderNumber(new BigDecimal(calculateRandomOrderNumber(pilotesOrderEntity)));
    pilotesOrderEntity.setTotalOrderAmount(new BigDecimal(1.33 * pilotesOrderEntity.getPilotesNumber()));
    pilotesOrderEntityList.add(pilotesOrderEntity);
    pilotesOrderEntity.setCustomerEntity(customer);
    customer.setPilotesOrderEntities(pilotesOrderEntityList);

    ModifyOrderRequest modifyOrderRequest = new ModifyOrderRequest();
    modifyOrderRequest.setPilotesNumber(10);
    modifyOrderRequest.setDeliveryAddress("new address");
    customerRepository.save(customer);
    mvc.perform(patch("/pilotes/order/" + pilotesOrderEntity.getOrderNumber())
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(modifyOrderRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.deliveryAddress").value("new address"));
  }

  @Test
  public void testModifyOrderAfterFiveMinutes() throws Exception {
    CustomerEntity customer = new CustomerEntity();
    customer.setName("name");
    customer.setSurname("surname");
    customer.setTelephone("telephone6");
    List<PilotesOrderEntity> pilotesOrderEntityList = new ArrayList<>();
    PilotesOrderEntity pilotesOrderEntity = new PilotesOrderEntity();
    pilotesOrderEntity.setPilotesNumber(5);
    pilotesOrderEntity.setDeliveryAddress("deliveryaddress");
    pilotesOrderEntity.setCreatedAt(LocalDateTime.now().minusMinutes(10));
    pilotesOrderEntity.setOrderNumber(new BigDecimal(calculateRandomOrderNumber(pilotesOrderEntity)));
    pilotesOrderEntity.setTotalOrderAmount(new BigDecimal(1.33 * pilotesOrderEntity.getPilotesNumber()));
    pilotesOrderEntityList.add(pilotesOrderEntity);
    pilotesOrderEntity.setCustomerEntity(customer);
    customer.setPilotesOrderEntities(pilotesOrderEntityList);

    ModifyOrderRequest modifyOrderRequest = new ModifyOrderRequest();
    modifyOrderRequest.setPilotesNumber(10);
    modifyOrderRequest.setDeliveryAddress("new address");
    customerRepository.save(customer);
    mvc.perform(patch("/pilotes/order/" + pilotesOrderEntity.getOrderNumber())
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(modifyOrderRequest)))
            .andExpect(status().isInternalServerError());
  }

  @Test
  public void testModifyOrderWrongOrderNumberWrongNumberOfPilotes() throws Exception {
    CustomerEntity customer = new CustomerEntity();
    customer.setName("name");
    customer.setSurname("surname");
    customer.setTelephone("telephone7");
    List<PilotesOrderEntity> pilotesOrderEntityList = new ArrayList<>();
    PilotesOrderEntity pilotesOrderEntity = new PilotesOrderEntity();
    pilotesOrderEntity.setPilotesNumber(1);
    pilotesOrderEntity.setDeliveryAddress("deliveryaddress");
    pilotesOrderEntity.setCreatedAt(LocalDateTime.now().minusMinutes(10));
    pilotesOrderEntity.setOrderNumber(new BigDecimal(calculateRandomOrderNumber(pilotesOrderEntity)));
    pilotesOrderEntity.setTotalOrderAmount(new BigDecimal(1.33 * pilotesOrderEntity.getPilotesNumber()));
    pilotesOrderEntityList.add(pilotesOrderEntity);
    pilotesOrderEntity.setCustomerEntity(customer);
    customer.setPilotesOrderEntities(pilotesOrderEntityList);

    ModifyOrderRequest modifyOrderRequest = new ModifyOrderRequest();
    modifyOrderRequest.setPilotesNumber(10);
    modifyOrderRequest.setDeliveryAddress("new address");
    customerRepository.save(customer);
    mvc.perform(patch("/pilotes/order/11")
                    .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(modifyOrderRequest)))
            .andExpect(status().isInternalServerError());
  }


  private long calculateRandomOrderNumber(PilotesOrderEntity p) {
    long theRandomNum = (long) (Math.random() * Math.pow(6, 6));
    p.setOrderNumber(new BigDecimal(theRandomNum));
    return theRandomNum;
  }

}
