package com.tui.proof.pilotes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class Customer   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("surname")
  private String surname;

  @JsonProperty("telephoneNumber")
  private String telephoneNumber;

  @JsonProperty("pilotesOrders")
  private List<PilotesOrder> pilotesOrders = null;

  /**
   **/
  public Customer name(String name) {
    this.name = name;
    return this;
  }


  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  public Customer surname(String surname) {
    this.surname = surname;
    return this;
  }


  @JsonProperty("surname")
  public String getSurname() {
    return surname;
  }
  public void setSurname(String surname) {
    this.surname = surname;
  }

  /**
   **/
  public Customer telephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
    return this;
  }


  @JsonProperty("telephoneNumber")
  public String getTelephoneNumber() {
    return telephoneNumber;
  }
  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }

  /**
   **/
  public Customer pilotesOrders(List<PilotesOrder> pilotesOrders) {
    this.pilotesOrders = pilotesOrders;
    return this;
  }


  @JsonProperty("pilotesOrders")
  public List<PilotesOrder> getPilotesOrders() {
    return pilotesOrders;
  }
  public void setPilotesOrders(List<PilotesOrder> pilotesOrders) {
    this.pilotesOrders = pilotesOrders;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return Objects.equals(name, customer.name) &&
        Objects.equals(surname, customer.surname) &&
        Objects.equals(telephoneNumber, customer.telephoneNumber) &&
        Objects.equals(pilotesOrders, customer.pilotesOrders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname, telephoneNumber, pilotesOrders);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Customer {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
    sb.append("    telephoneNumber: ").append(toIndentedString(telephoneNumber)).append("\n");
    sb.append("    pilotesOrders: ").append(toIndentedString(pilotesOrders)).append("\n");
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