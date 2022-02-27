package com.userForCredit.getLoan.dataAccess.abstracts;

import com.userForCredit.getLoan.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    Optional<User> findByNationalityNumber(String nationalityNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
