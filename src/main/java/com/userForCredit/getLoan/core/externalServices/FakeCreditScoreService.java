package com.userForCredit.getLoan.core.externalServices;

import org.springframework.stereotype.Service;

@Service
public class FakeCreditScoreService {

    public Double getUserCreditScore(String nationalityNumber){
        Double creditScore;
        creditScore = (Math.random() * 2000);
        return creditScore;
    }
}
