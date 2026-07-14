package com.spring.cohort.assignment;

import com.spring.cohort.assignment.dto.DepartmentDTO;
import com.spring.cohort.assignment.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AspectTest {

    @Autowired
    private DepartmentService departmentService;


    @Test
    public void departmentServeTest(){
        departmentService.createDepartment(DepartmentDTO.builder()
                .title("Test Department")
                .isActive(true)
                .email("test@company.com")
                .creditCard("3566002020360505")
                .websiteUrl("https://test.company.com")
                .employeeCount(10)
                .supportMail("support@company.com")
                .quarterlyBudget(new java.math.BigDecimal("5000.00"))
                .financialLoss(1000.0).budgetRenewalDate(java.time.LocalDate.now())
                .nextExpansionDate(java.time.LocalDate.now().plusMonths(6))
                .createdAt(new java.util.Date()).build());
    }
    @Test
    public void isExistException(){
        departmentService.getDepartment(1L);
    }
}
