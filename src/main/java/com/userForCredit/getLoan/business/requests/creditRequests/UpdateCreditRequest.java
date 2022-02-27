package com.userForCredit.getLoan.business.requests.creditRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditRequest {

    private Integer id;
    private Double creditAmount;
}
