package com.DoItNow.PersonalTaskManagementSoftware.repository;

import com.DoItNow.PersonalTaskManagementSoftware.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

//    Optional<UserModel> findByLoginAndPassword(String login, String password);
//
//    Optional<UserModel> findFirstByLogin(String login);
    @Query(value = "SELECT * FROM USERS u WHERE u.login = ?1",
            nativeQuery = true)
    UserModel findByLogin(String email);
}
