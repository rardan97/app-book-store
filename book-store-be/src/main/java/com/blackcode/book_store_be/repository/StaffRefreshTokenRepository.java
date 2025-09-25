package com.blackcode.book_store_be.repository;

import com.blackcode.book_store_be.model.staff.Staff;
import com.blackcode.book_store_be.model.staff.StaffRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRefreshTokenRepository extends JpaRepository<StaffRefreshToken, Long> {

    Optional<StaffRefreshToken> findByToken(String token);
    @Query("SELECT rt FROM StaffRefreshToken rt WHERE rt.staff.id = :staffId")
    Optional<StaffRefreshToken> findByStaffId(Long staffId);
    @Modifying
    int deleteByStaff(Staff staff);
}
