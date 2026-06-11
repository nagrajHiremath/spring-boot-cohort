package com.spring.cohort.assignment.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String title;
    Boolean isActive;
    String email;
    String creditCard;
    String websiteUrl;
    @Positive
    Integer employeeCount;
    String supportMail;
    private BigDecimal quarterlyBudget;
    Double financialLoss;
    private LocalDate budgetRenewalDate;
    private LocalDate nextExpansionDate;
    Date createdAt  = new Date();
}
