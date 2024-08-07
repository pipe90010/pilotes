package com.tui.proof.pilotes.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PilotesOrder {

    @JsonProperty("orderNumber")
    private BigDecimal orderNumber;

    @JsonProperty("deliveryAddress")
    private String deliveryAddress;

    @JsonProperty("pilotesNumber")
    private Integer pilotesNumber;

    @JsonProperty("totalOrderAmount")
    private BigDecimal totalOrderAmount;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    public PilotesOrder orderNumber(BigDecimal orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @JsonProperty("orderNumber")
    public BigDecimal getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(BigDecimal orderNumber) {
        this.orderNumber = orderNumber;
    }

    public PilotesOrder deliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    @JsonProperty("deliveryAddress")
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public PilotesOrder pilotesNumber(Integer pilotesNumber) {
        this.pilotesNumber = pilotesNumber;
        return this;
    }

    @JsonProperty("pilotesNumber")
    public Integer getPilotesNumber() {
        return pilotesNumber;
    }
    public void setPilotesNumber(Integer pilotesNumber) {
        this.pilotesNumber = pilotesNumber;
    }

    public PilotesOrder totalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
        return this;
    }

    @JsonProperty("totalOrderAmount")
    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }
    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    /**
     **/
    public PilotesOrder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @JsonProperty("createdAt")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, deliveryAddress, pilotesNumber, totalOrderAmount, createdAt);
    }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PilotesOrder {\n");
    
    sb.append("    orderNumber: ").append(toIndentedString(orderNumber)).append("\n");
    sb.append("    deliveryAddress: ").append(toIndentedString(deliveryAddress)).append("\n");
    sb.append("    pilotesNumber: ").append(toIndentedString(pilotesNumber)).append("\n");
    sb.append("    totalOrderAmount: ").append(toIndentedString(totalOrderAmount)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
    
}
