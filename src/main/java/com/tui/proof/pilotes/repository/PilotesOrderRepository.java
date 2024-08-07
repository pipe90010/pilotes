package com.tui.proof.pilotes.repository;

import java.math.BigDecimal;
import org.springframework.data.repository.CrudRepository;

import com.tui.proof.pilotes.entity.PilotesOrderEntity;

public interface PilotesOrderRepository extends CrudRepository<PilotesOrderEntity, Long> {

    PilotesOrderEntity findByOrderNumber(BigDecimal orderNumber);
}