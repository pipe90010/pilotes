package com.tui.proof.pilotes.dto;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsertCustomerRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("telephoneNumber")
    private String telephoneNumber;

    @JsonProperty("pilotesOrders")
    private List<PilotesOrder> pilotesOrders = null;

    public InsertCustomerRequest name(String name) {
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

    public InsertCustomerRequest surname(String surname) {
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

    public InsertCustomerRequest telephoneNumber(String telephoneNumber) {
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

    public InsertCustomerRequest pilotesOrders(List<PilotesOrder> pilotesOrders) {
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InsertCustomerRequest other = (InsertCustomerRequest) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        if (telephoneNumber == null) {
            if (other.telephoneNumber != null)
                return false;
        } else if (!telephoneNumber.equals(other.telephoneNumber))
            return false;
        if (pilotesOrders == null) {
            if (other.pilotesOrders != null)
                return false;
        } else if (!pilotesOrders.equals(other.pilotesOrders))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((telephoneNumber == null) ? 0 : telephoneNumber.hashCode());
        result = prime * result + ((pilotesOrders == null) ? 0 : pilotesOrders.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InsertCustomerRequest {\n");

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