package com.userForCredit.getLoan.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditListDto {

    private Integer id;
    private Integer userId;
    private String nationalityNumber;
    private String userFirstName;
    private String userLastName;
    private Double creditAmount;
    private Date createdDate;
}
