package com.lewisCode.accountservice.repository;

import com.lewisCode.accountservice.entity.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SignUpRepository extends JpaRepository<SignUp,Long> {

    Optional<SignUp> findByEmail(String email);

}
