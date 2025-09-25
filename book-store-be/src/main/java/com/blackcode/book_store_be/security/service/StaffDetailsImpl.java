package com.blackcode.book_store_be.security.service;

import com.blackcode.book_store_be.model.staff.Staff;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StaffDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1322963087909878166L;

    private Long staffId;
    private String staffUsername;

    @JsonIgnore
    private String staffPassword;

    // If you're not using roles at all, just return an empty list
    private final Collection<? extends GrantedAuthority> authorities;

    public StaffDetailsImpl(Long staffId, String staffUsername, String staffPassword, Collection<? extends GrantedAuthority> authorities) {
        this.staffId = staffId;
        this.staffUsername = staffUsername;
        this.staffPassword = staffPassword;
        this.authorities = authorities; // no roles
    }

    public static StaffDetailsImpl build(Staff staff) {
        String roleName = staff.getStaffRole() != null ? staff.getStaffRole().getRoleStaffName() : "DEFAULT";
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()));
        return new StaffDetailsImpl(
                staff.getStaffId(),
                staff.getStaffUsername(),
                staff.getStaffPassword(),
                authorities
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getStaffId() {
        return staffId;
    }

    @Override
    public String getPassword() {
        return staffPassword;
    }

    @Override
    public String getUsername() {
        return staffUsername;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StaffDetailsImpl staff = (StaffDetailsImpl) o;
        return Objects.equals(staffId, staff.staffId);
    }
}
