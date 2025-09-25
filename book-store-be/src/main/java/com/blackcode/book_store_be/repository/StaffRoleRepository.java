package com.blackcode.book_store_be.repository;

import com.blackcode.book_store_be.model.staff.StaffRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRoleRepository extends JpaRepository<StaffRole, Long> {
    Optional<StaffRole> findByRoleStaffName(String roleStaffName);
}
