package com.dekankilic.cqrswithaxonserver.query.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Account {
    @Id
    private String accountId;

    private BigDecimal balance;
    private String status;

}
