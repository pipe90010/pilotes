package com.tui.proof.pilotes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModifyOrderRequest {
    @JsonProperty("deliveryAddress")
    private String deliveryAddress;
  
    @JsonProperty("pilotesNumber")
    private Integer pilotesNumber;
  
    /**
     **/
    public ModifyOrderRequest deliveryAddress(String deliveryAddress) {
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
  
    /**
     **/
    public ModifyOrderRequest pilotesNumber(Integer pilotesNumber) {
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
  
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deliveryAddress == null) ? 0 : deliveryAddress.hashCode());
        result = prime * result + ((pilotesNumber == null) ? 0 : pilotesNumber.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ModifyOrderRequest other = (ModifyOrderRequest) obj;
        if (deliveryAddress == null) {
            if (other.deliveryAddress != null)
                return false;
        } else if (!deliveryAddress.equals(other.deliveryAddress))
            return false;
        if (pilotesNumber == null) {
            if (other.pilotesNumber != null)
                return false;
        } else if (!pilotesNumber.equals(other.pilotesNumber))
            return false;
        return true;
    }


    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("class ModifyOrderRequest {\n");
      
      sb.append("    deliveryAddress: ").append(toIndentedString(deliveryAddress)).append("\n");
      sb.append("    pilotesNumber: ").append(toIndentedString(pilotesNumber)).append("\n");
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