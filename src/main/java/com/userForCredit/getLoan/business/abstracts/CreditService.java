package com.userForCredit.getLoan.business.abstracts;

import com.userForCredit.getLoan.business.dtos.CreditListDto;
import com.userForCredit.getLoan.business.requests.creditRequests.CreateCreditRequest;
import com.userForCredit.getLoan.business.requests.creditRequests.UpdateCreditRequest;
import com.userForCredit.getLoan.core.utilities.results.DataResult;
import com.userForCredit.getLoan.core.utilities.results.Result;

import java.util.List;

public interface CreditService {

    Result add(CreateCreditRequest createCreditRequest);
    Result update(UpdateCreditRequest updateCreditRequest);
    Result delete(Integer creditId);

    DataResult<List<CreditListDto>> getAll();
    DataResult<CreditListDto> getById(Integer creditId);
    DataResult<CreditListDto> getByNationalityNumber(String nationalityNumber);
}
