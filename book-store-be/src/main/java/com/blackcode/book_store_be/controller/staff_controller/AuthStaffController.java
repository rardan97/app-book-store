package com.blackcode.book_store_be.controller.staff_controller;

import com.blackcode.book_store_be.dto.roles.StaffRoleRes;
import com.blackcode.book_store_be.dto.staff.*;
import com.blackcode.book_store_be.exception.TokenRefreshException;
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
import com.blackcode.book_store_be.service.StaffRoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/staff")
public class AuthStaffController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    StaffTokenRepository staffTokenRepository;

    @Autowired
    StaffRoleRepository staffRoleRepository;

    @Autowired
    private StaffRoleService staffRoleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private StaffTokenService staffTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    StaffRefreshTokenService staffRefreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody StaffLoginReq loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            StaffDetailsImpl staffDetails = (StaffDetailsImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateJwtTokenStaff(staffDetails);
            staffTokenService.processStaffTokenAdd(staffDetails.getStaffId(), jwt);
            List<String> roles = staffDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            StaffRefreshToken refreshToken = staffRefreshTokenService.createRefreshToken(jwt, staffDetails.getStaffId());
            return ResponseEntity.status(HttpStatus.OK).body(new StaffJwtRes(
                    jwt,
                    refreshToken.getToken(),
                    staffDetails.getStaffId(),
                    staffDetails.getUsername(),
                    roles));
        }catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (AccountExpiredException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account has expired");
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account is locked");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the login request");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody StaffSignUpReq staffSignUpReq){
        System.out.println("username : "+staffSignUpReq.getStaffUsername());
        if(staffRepository.existsByStaffUsername(staffSignUpReq.getStaffUsername())){
            return ResponseEntity.badRequest().body(new StaffMessageRes("Error : Username is already taken!"));
        }
        System.out.println("username : "+staffSignUpReq.getRole());
        long roleId = Long.valueOf(staffSignUpReq.getRole());
        Optional<StaffRole> roleData = staffRoleRepository.findById(roleId);
        if(roleData == null || roleData.isEmpty()){
            return ResponseEntity.badRequest().body(new StaffMessageRes("Error: Role Not Found!"));
        }

        Staff staff = new Staff(
                staffSignUpReq.getStaffFullName(),
                staffSignUpReq.getStaffEmail(),
                staffSignUpReq.getStaffUsername(),
                encoder.encode(staffSignUpReq.getStaffPassword()),
                roleData.get());
        staffRepository.save(staff);
        return ResponseEntity.ok(new StaffMessageRes("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody StaffTokenRefreshReq request){
        String requestRefreshToken = request.getRefreshToken();
        return staffRefreshTokenService.findByToken(requestRefreshToken)
                .map(staffRefreshTokenService::verifyExpiration)
                .map(StaffRefreshToken::getStaff)
                .map(staff -> {
                    String token = jwtUtils.generateTokenFromUsername(staff.getStaffUsername());
                    staffTokenService.processStaffTokenRefresh(staff.getStaffFullName(), token);
                    return ResponseEntity.ok(new StaffTokenRefreshRes(token, requestRefreshToken));
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logout(
            HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println("Test request : "+request.getMethod());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            System.out.println("Validasi 1");
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                System.out.println("Validasi 2");
                StaffDetailsImpl userDetails = (StaffDetailsImpl) authentication.getPrincipal();
                Long userId = userDetails.getStaffId();
                String jwtToken = token.substring(7);
                Optional<StaffToken> userTokenData = staffTokenRepository.findByToken(jwtToken);
                if(userTokenData.isPresent()){
                    System.out.println("Validasi 3");
                    staffRefreshTokenService.deleteByStaffId(userId);
                    userTokenData.get().setIsActive(false);
                    staffTokenRepository.save(userTokenData.get());
                    return ResponseEntity.ok(new StaffMessageRes("Log out successful!"));
                }else{
                    System.out.println("check token not found");
                    return ResponseEntity.ok(new StaffMessageRes("Log out Failed!!!"));
                }
            } else {
                return ResponseEntity.ok(new StaffMessageRes("Authorization not null"));
            }
        }else {
            return ResponseEntity.ok(new StaffMessageRes("authentication not found"));
        }
    }

    @GetMapping("/getRoleListAll")
    public ResponseEntity<List<StaffRoleRes>> getRoleListAll(){
        List<StaffRoleRes> staffRoleListRes = staffRoleService.getListAllRole();
        System.out.println(staffRoleListRes.size());
        return ResponseEntity.ok(staffRoleListRes);
    }
}