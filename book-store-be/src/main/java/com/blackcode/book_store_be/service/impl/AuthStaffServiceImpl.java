package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.staff.*;
import com.blackcode.book_store_be.exception.RoleNotFoundException;
import com.blackcode.book_store_be.exception.TokenRefreshException;
import com.blackcode.book_store_be.exception.UsernameAlreadyExistsException;
import com.blackcode.book_store_be.model.staff.Staff;
import com.blackcode.book_store_be.model.staff.StaffRefreshToken;
import com.blackcode.book_store_be.model.staff.StaffRole;
import com.blackcode.book_store_be.model.staff.StaffToken;
import com.blackcode.book_store_be.repository.StaffRepository;
import com.blackcode.book_store_be.repository.StaffRoleRepository;
import com.blackcode.book_store_be.repository.StaffTokenRepository;
import com.blackcode.book_store_be.security.jwt.JwtUtils;
import com.blackcode.book_store_be.security.service.StaffDetailsImpl;
import com.blackcode.book_store_be.security.service.StaffRefreshTokenService;
import com.blackcode.book_store_be.security.service.StaffTokenService;
import com.blackcode.book_store_be.service.AuthStaffService;
import com.blackcode.book_store_be.service.StaffRoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthStaffServiceImpl implements AuthStaffService {

    private final AuthenticationManager authenticationManager;

    private final StaffRepository staffRepository;

    private final StaffTokenRepository staffTokenRepository;

    private final StaffRoleRepository staffRoleRepository;

    private final PasswordEncoder encoder;

    private final StaffTokenService staffTokenService;

    private final StaffRefreshTokenService staffRefreshTokenService;

    private final JwtUtils jwtUtils;

    public AuthStaffServiceImpl(AuthenticationManager authenticationManager, StaffRepository staffRepository, StaffTokenRepository staffTokenRepository, StaffRoleRepository staffRoleRepository, PasswordEncoder encoder, StaffTokenService staffTokenService, StaffRefreshTokenService staffRefreshTokenService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.staffRepository = staffRepository;
        this.staffTokenRepository = staffTokenRepository;
        this.staffRoleRepository = staffRoleRepository;
        this.encoder = encoder;
        this.staffTokenService = staffTokenService;
        this.staffRefreshTokenService = staffRefreshTokenService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public StaffJwtRes singIn(StaffLoginReq loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        StaffDetailsImpl staffDetails = (StaffDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtTokenStaff(staffDetails);
        staffTokenService.processStaffTokenAdd(staffDetails.getStaffId(), jwt);
        List<String> roles = staffDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        StaffRefreshToken refreshToken = staffRefreshTokenService.createRefreshToken(jwt, staffDetails.getStaffId());
        return new StaffJwtRes(
                jwt,
                refreshToken.getToken(),
                staffDetails.getStaffId(),
                staffDetails.getUsername(),
                roles);
    }

    @Override
    public StaffMessageRes signUp(StaffSignUpReq staffSignUpReq) {
        System.out.println("username : "+staffSignUpReq.getStaffUsername());
        if(staffRepository.existsByStaffUsername(staffSignUpReq.getStaffUsername())){
            throw new UsernameAlreadyExistsException("Username is already taken!");
        }
        System.out.println("username : "+staffSignUpReq.getRole());
        long roleId = Long.parseLong(staffSignUpReq.getRole());

        StaffRole roleData = staffRoleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id:" + roleId));

        Staff staff = new Staff(
                staffSignUpReq.getStaffFullName(),
                staffSignUpReq.getStaffEmail(),
                staffSignUpReq.getStaffUsername(),
                encoder.encode(staffSignUpReq.getStaffPassword()),
                roleData);
        staffRepository.save(staff);
        return new StaffMessageRes("User registered successfully!");
    }

    @Override
    public StaffTokenRefreshRes refreshToken(StaffTokenRefreshReq request) {
        StaffTokenRefreshRes staffTokenRefreshRes = null;
        String requestRefreshToken = request.getRefreshToken();
        Optional<StaffRefreshToken> petugasRefreshToken = staffRefreshTokenService.findByToken(requestRefreshToken);
        if(petugasRefreshToken.isPresent()){
            StaffRefreshToken staffRefreshToken1 = petugasRefreshToken.get();
            staffRefreshToken1 = staffRefreshTokenService.verifyExpiration(staffRefreshToken1);
            Staff staff = staffRefreshToken1.getStaff();
            String token = jwtUtils.generateTokenFromUsername(staff.getStaffUsername());
            staffTokenService.processStaffTokenRefresh(staff.getStaffUsername(), token);
            staffTokenRefreshRes = new StaffTokenRefreshRes(token, requestRefreshToken);
        }else {
            throw new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!");
        }
        return staffTokenRefreshRes;
    }

    @Override
    public StaffMessageRes signOut(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String headerAuth = request.getHeader("Authorization");
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                String jwtToken = headerAuth.substring(7);
                StaffDetailsImpl userDetails = (StaffDetailsImpl) authentication.getPrincipal();
                Long userId = userDetails.getStaffId();

                Optional<StaffToken> staffTokenData = staffTokenRepository.findByToken(jwtToken);
                if (staffTokenData.isPresent()) {
                    staffRefreshTokenService.deleteByStaffId(userId);
                    StaffToken staffToken = staffTokenData.get();
                    staffToken.setIsActive(false);
                    staffTokenRepository.save(staffToken);
                    return new StaffMessageRes("Logout successful!");
                } else {
                    return new StaffMessageRes("Token not found, logout failed!");
                }
            } else {
                return new StaffMessageRes("Authorization header is missing or invalid");
            }
        } else {
            return new StaffMessageRes("User is not authenticated");
        }
    }
}
