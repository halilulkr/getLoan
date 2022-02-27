package com.userForCredit.getLoan.dataAccess.abstracts;

import com.userForCredit.getLoan.entities.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditDao extends JpaRepository<Credit, Integer> {

    Optional<Credit> findCreditAmountByUser_NationalityNumber(String nationalityNumber);
    Optional<List<Credit>> findCreditByUser_NationalityNumberOrderByIdDesc(String nationalityNumber);
    Optional<Credit> findCreditByUser_NationalityNumber(String nationalityNumber);


}
