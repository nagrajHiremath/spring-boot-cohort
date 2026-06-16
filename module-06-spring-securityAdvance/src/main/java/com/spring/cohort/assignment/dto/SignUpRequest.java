package com.spring.cohort.assignment.dto;

import com.spring.cohort.assignment.entity.enums.SubscriptionPlan;
import lombok.Data;

@Data
public class SignUpRequest {
    String name;
    String userName;
    String password;
    SubscriptionPlan subscriptionPlan;
}
