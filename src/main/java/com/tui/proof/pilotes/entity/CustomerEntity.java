package com.tui.proof.pilotes.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import reactor.core.publisher.Mono;

import java.util.List; 

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "customer")
@Entity
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_GEN")
    @SequenceGenerator(name = "customer_GEN", sequenceName = "customer_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String surname;

    @Column(unique=true)
    private String telephone;

    @OneToMany(mappedBy= "customerEntity", cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PilotesOrderEntity> pilotesOrderEntities;
}