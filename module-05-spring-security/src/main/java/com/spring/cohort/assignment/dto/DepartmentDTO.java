package com.spring.cohort.assignment.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    Long id;
    @NotBlank
    @Size(min=2, max=50, message = "title must be bettween 2 - 50 chars")
    String title;
    @AssertTrue(message = "must be active")
    Boolean isActive;
    @Email(message = "Please provide a valid support email address (e.g., tech@company.com)")
    String email;
    @CreditCardNumber(message = "The corporate expense card number is invalid")
    String creditCard;
    @URL(protocol = "https", message = "The internal portal link must be a valid secure HTTPS URL")
    String websiteUrl;
    @Positive
    Integer employeeCount;
    String supportMail;
    @DecimalMin(value = "1000.00", message = "Quarterly budget must be at least 1000.00")
    @DecimalMax(value = "999999.99", message = "Quarterly budget cannot exceed 999,999.99")
    @Digits(integer = 6, fraction = 2, message = "Budget format must be up to 6 digits total and 2 decimals")
    private BigDecimal quarterlyBudget;
    Double financialLoss;
    @FutureOrPresent(message = "The budget renewal date must be today or a future date")
    private LocalDate budgetRenewalDate;
    @Future(message = "The scheduled department expansion date must be in the future")
    private LocalDate nextExpansionDate;
    Date createdAt;
}
