package com.blackcode.book_store_be.repository;

import com.blackcode.book_store_be.model.staff.StaffToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffTokenRepository extends JpaRepository<StaffToken, Long> {

    Optional<StaffToken> findByToken(String token);
    Optional<StaffToken> findByStaffId(Long staffId);
    Optional<StaffToken> findByStaffIdAndToken(Long staffId, String token);
    void deleteByToken(String token);
}
