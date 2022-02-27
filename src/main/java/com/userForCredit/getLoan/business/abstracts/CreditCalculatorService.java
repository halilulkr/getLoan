package com.userForCredit.getLoan.business.abstracts;

public interface CreditCalculatorService {

    public Double creditAmountCalculator(Double creditScore, Double salary);
}
