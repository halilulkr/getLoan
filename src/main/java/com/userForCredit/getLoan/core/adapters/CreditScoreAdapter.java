package com.userForCredit.getLoan.core.adapters;

import com.userForCredit.getLoan.core.externalServices.FakeCreditScoreService;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreAdapter implements CreditScoreService{

    private final FakeCreditScoreService fakeCreditScoreService;

    public CreditScoreAdapter(FakeCreditScoreService fakeCreditScoreService) {
        this.fakeCreditScoreService = fakeCreditScoreService;
    }

    @Override
    public Double getUserCreditScore(String nationalityNumber) {
        return fakeCreditScoreService.getUserCreditScore(nationalityNumber);
    }
}
