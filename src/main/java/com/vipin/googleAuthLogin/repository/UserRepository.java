package com.vipin.googleAuthLogin.repository;

import com.vipin.googleAuthLogin.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {

    Optional<UserData> findByEmail(String email);

    Optional<UserData> findByUserName(String userName);

}
