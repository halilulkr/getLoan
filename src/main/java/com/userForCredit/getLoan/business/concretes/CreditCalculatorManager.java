package com.userForCredit.getLoan.business.concretes;

import com.userForCredit.getLoan.business.abstracts.CreditCalculatorService;
import com.userForCredit.getLoan.core.utilities.constants.Coefficient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CreditCalculatorManager implements CreditCalculatorService {

    private final double coefficient = Coefficient.creditCoefficient;

    @Override
    public Double creditAmountCalculator(Double creditScore, Double salary) {
        double creditAmount;
        if (creditScore < 500){
            return creditAmount=0;
        }else if(creditScore >= 500 && creditScore < 1000) {
            if (salary < 5000) {
                creditAmount=10000;
            }else {
                creditAmount=20000;
            }
        }else {
            creditAmount=(salary*coefficient);
        }

        log.info("Calculated by : " + this.getClass().getName()
        + "\nCreditScore : " + creditScore
        + "\nCreditAmount : " + creditAmount
        + "\nSalary : " + salary);
        return creditAmount;
    }
}
