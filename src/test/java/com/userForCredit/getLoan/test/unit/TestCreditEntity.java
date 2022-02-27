package com.userForCredit.getLoan.test.unit;

import com.userForCredit.getLoan.dataAccess.abstracts.CreditDao;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import com.userForCredit.getLoan.entities.Credit;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

//Unit test for Credit Entity

@SpringBootTest
public class TestCreditEntity {

    @Autowired
    private CreditDao creditDao;

    //CREATE
    @Test
    void addTest(){
        Credit credit = new Credit();
        credit.setCreditAmount(1234.5);
        this.creditDao.save(credit);
        Optional<Credit> response = this.creditDao.findById(1);
        Assertions.assertNotNull(response);
    }

    //FIND
    @Test
    void findTest(){
        Credit response = this.creditDao.findById(1).get();
        Assertions.assertEquals(1234.5, response.getCreditAmount());
    }

    //LIST
    @Test
    void listTest(){
        List<Credit> creditList = this.creditDao.findAll();
        assertThat(creditList).size().isGreaterThan(0);
    }

    //UPDATE
    @Test
    void updateTest(){
        Credit update = this.creditDao.findById(1).get();
        update.setCreditAmount(5432.1);
        this.creditDao.save(update);
        Assertions.assertNotEquals(1234.5, update.getCreditAmount());
    }

    //DELETE
    @Test
    void deleteTest(){
        this.creditDao.deleteById(1);
        AssertionsForClassTypes.assertThat(this.creditDao.existsById(1)).isFalse();
    }
}
