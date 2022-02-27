package com.userForCredit.getLoan.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String nationalityNumber;
    private Double salary;
    private String phoneNumber;
}
