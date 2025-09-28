package com.blackcode.book_store_be.repository;

import com.blackcode.book_store_be.model.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByStaffUsername(String staffUsername);

    Boolean existsByStaffUsername(String staffUsername);
}
