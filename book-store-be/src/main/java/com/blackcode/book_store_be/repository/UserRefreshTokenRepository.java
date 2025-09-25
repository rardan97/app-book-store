package com.blackcode.book_store_be.repository;

import com.blackcode.book_store_be.model.user.User;
import com.blackcode.book_store_be.model.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    Optional<UserRefreshToken> findByToken(String token);
    @Query("SELECT rt FROM UserRefreshToken rt WHERE rt.user.id = :userId")
    Optional<UserRefreshToken> findByUserId(Long userId);
    @Modifying
    int deleteByUser(User user);
}