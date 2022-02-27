package com.userForCredit.getLoan.test.unit;

import com.userForCredit.getLoan.dataAccess.abstracts.UserDao;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import com.userForCredit.getLoan.entities.User;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

//Unit test for User Entity

@SpringBootTest
public class TestUserEntity {

    @Autowired
    private UserDao userDao;

    //CREATE
    @Test
    void addTest(){
        User user = new User();
        user.setFirstName("Halil");
        user.setLastName("Ülker");
        user.setNationalityNumber("123");
        user.setSalary(1234.5);
        user.setPhoneNumber("321");
        this.userDao.save(user);
        Optional<User> response = this.userDao.findById(1);
        Assertions.assertNotNull(response);
    }

    //FIND
    @Test
    void findTest(){
        User response = this.userDao.findById(1).get();
        Assertions.assertEquals("Halil", response.getFirstName());
    }

    //LIST
    @Test
    void listTest(){
        List<User> userList = this.userDao.findAll();
        assertThat(userList).size().isGreaterThan(0);
    }

    //UPDATE
    @Test
    void updateTest(){
        User update = this.userDao.findById(1).get();
        update.setFirstName("Innova");
        this.userDao.save(update);
        Assertions.assertNotEquals("Halil", update.getFirstName());
    }

    //DELETE
    @Test
    void deleteTest(){
        this.userDao.deleteById(1);
        AssertionsForClassTypes.assertThat(this.userDao.existsById(1)).isFalse();
    }
}
