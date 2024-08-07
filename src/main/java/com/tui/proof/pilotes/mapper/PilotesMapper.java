package com.tui.proof.pilotes.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.tui.proof.pilotes.dto.PilotesOrder;
import com.tui.proof.pilotes.entity.PilotesOrderEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PilotesMapper {
    

  public static PilotesOrder mapToDto(PilotesOrderEntity pilotesOrderEntity) {
    PilotesOrder pilotesOrder = new PilotesOrder();
    if (pilotesOrderEntity != null) {
      pilotesOrder.setOrderNumber(pilotesOrderEntity.getOrderNumber());
      pilotesOrder.setPilotesNumber(pilotesOrderEntity.getPilotesNumber());
      pilotesOrder.setCreatedAt(pilotesOrderEntity.getCreatedAt());
      pilotesOrder.setDeliveryAddress(pilotesOrderEntity.getDeliveryAddress());
      pilotesOrder.setTotalOrderAmount(pilotesOrderEntity.getTotalOrderAmount());
    }
    return pilotesOrder;
  }

  public static final Function<PilotesOrderEntity, PilotesOrder> toDto = pilotesOrderEntity -> {
    if (pilotesOrderEntity == null) {
        return null;
    }
    PilotesOrder pilotesOrder = new PilotesOrder();
    pilotesOrder.setOrderNumber(pilotesOrderEntity.getOrderNumber());
    pilotesOrder.setPilotesNumber(pilotesOrderEntity.getPilotesNumber());
    pilotesOrder.setCreatedAt(pilotesOrderEntity.getCreatedAt());
    pilotesOrder.setDeliveryAddress(pilotesOrderEntity.getDeliveryAddress());
    pilotesOrder.setTotalOrderAmount(pilotesOrderEntity.getTotalOrderAmount());
    return pilotesOrder;
};/* 

    public static Mono<PilotesOrder> monoMapOrderToDto(Mono<PilotesOrderEntity> mono) {
        return mono.map(order -> {
            PilotesOrder pilotesOrder = new PilotesOrder();
            pilotesOrder.setOrderNumber(order.getOrderNumber());
            pilotesOrder.setPilotesNumber(order.getPilotesNumber());
            pilotesOrder.setTotalOrderAmount(order.getTotalOrderAmount());
            pilotesOrder.setDeliveryAddress(order.getDeliveryAddress());
            pilotesOrder.setCreatedAt(order.getCreatedAt());
            return pilotesOrder;
        });
    }

    // Method to map a Flux<PilotesOrderEntity> to a Flux<PilotesOrder> reactively
    public static Flux<PilotesOrder> fluxMapOrdersToDto(Flux<PilotesOrderEntity> flux) {
        return flux.map(order -> {
            PilotesOrder pilotesOrder = new PilotesOrder();
            pilotesOrder.setOrderNumber(order.getOrderNumber());
            pilotesOrder.setPilotesNumber(order.getPilotesNumber());
            pilotesOrder.setTotalOrderAmount(order.getTotalOrderAmount());
            pilotesOrder.setDeliveryAddress(order.getDeliveryAddress());
            pilotesOrder.setCreatedAt(order.getCreatedAt());
            return pilotesOrder;
        });
    }*/

    public static List<PilotesOrder> mapOrdersToDto(List<PilotesOrderEntity> input) {
        return input.stream().map(order -> {
          PilotesOrder pilotesOrder = new PilotesOrder();
          pilotesOrder.setOrderNumber(order.getOrderNumber());
          pilotesOrder.setPilotesNumber(order.getPilotesNumber());
          pilotesOrder.setTotalOrderAmount(order.getTotalOrderAmount());
          pilotesOrder.setDeliveryAddress(order.getDeliveryAddress());
          pilotesOrder.setCreatedAt(order.getCreatedAt());
    
          return pilotesOrder;
        }).collect(Collectors.toList());
    }

}
