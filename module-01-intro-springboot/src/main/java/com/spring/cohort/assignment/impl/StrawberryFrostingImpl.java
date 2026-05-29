package com.spring.cohort.assignment.impl;

import com.spring.cohort.assignment.Frosting;
import org.springframework.stereotype.Component;

@Component
public class StrawberryFrostingImpl implements Frosting {

    public void getFrostingType(){
        System.out.println("Strawberry Frosting");
    }
}
