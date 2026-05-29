package com.spring.cohort.assignment.impl;

import com.spring.cohort.assignment.Syrup;
import org.springframework.stereotype.Component;

@Component
public class StrawberrySyrupImpl implements Syrup {
    public void getSyrupType(){
        System.out.println("Strawberry Syrup");
    }
}
