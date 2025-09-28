package com.blackcode.book_store_be.security.service;

import com.blackcode.book_store_be.model.staff.Staff;
import com.blackcode.book_store_be.repository.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StaffDetailsServiceImpl implements UserDetailsService {


    private final StaffRepository staffRepository;

    public StaffDetailsServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByStaffUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Staff Not Found with username: " + username));;

        return StaffDetailsImpl.build(staff);
    }
}
