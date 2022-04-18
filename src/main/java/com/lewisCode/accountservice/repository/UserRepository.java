package com.lewisCode.accountservice.repository;

import com.lewisCode.accountservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    long deleteUserByEmail(String email);

    @Modifying
    @Query("UPDATE user SET failedAttempt = ?1 WHERE email = ?2")
    int updateFailedAttempts(int failedAttempt, String email);

    @Modifying
    @Query("update user set accountNonLocked = ?1 where email = ?2")
    void updateUserAccountNonLocked (boolean accountNonLocked, String email);

}
