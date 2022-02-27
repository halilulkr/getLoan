package com.userForCredit.getLoan.business.requests.userRequests;

import com.userForCredit.getLoan.core.utilities.constants.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private Integer id;
    private String firstName;
    private String lastName;
    @NotBlank(message = Messages.userNationalityNumberNotBlank)
    private String nationalityNumber;
    private Double salary;
    private String phoneNumber;
}
