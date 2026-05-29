package com.spring.cohort.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CakeBaker {
    @Qualifier("chocalateFrostingImpl")
    @Autowired
    Frosting frosting;
    @Qualifier("choclateSyrupImpl")
    @Autowired
    Syrup syrup;

    public void bakeCake(){

        frosting.getFrostingType();
        syrup.getSyrupType();
    }
}
