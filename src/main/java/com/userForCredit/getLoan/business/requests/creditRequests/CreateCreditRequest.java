package com.userForCredit.getLoan.business.requests.creditRequests;

import com.userForCredit.getLoan.core.utilities.constants.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditRequest {

    @NotBlank(message = Messages.userNationalityNumberNotBlank)
    private String nationalityNumber;
}
