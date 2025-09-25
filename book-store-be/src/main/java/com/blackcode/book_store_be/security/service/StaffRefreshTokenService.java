package com.blackcode.book_store_be.security.service;

import com.blackcode.book_store_be.exception.TokenRefreshException;
import com.blackcode.book_store_be.model.staff.Staff;
import com.blackcode.book_store_be.model.staff.StaffRefreshToken;
import com.blackcode.book_store_be.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffRefreshTokenService {
    @Value("${blackcode.app.jwtRefreshExpirationMs}")
    private int refreshTokenDurationMs;

    @Autowired
    private StaffRefreshTokenRepository staffRefreshTokenRepository;

    @Autowired
    private StaffTokenRepository staffTokenRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffTokenService staffTokenService;

    public Optional<StaffRefreshToken> findByToken(String token){
        return staffRefreshTokenRepository.findByToken(token);
    }

    public StaffRefreshToken createRefreshToken(String jwt, Long staffId){
        StaffRefreshToken staffRefreshToken = null;
        Optional<StaffRefreshToken> existingToken = staffRefreshTokenRepository.findByStaffId(staffId);
        if (existingToken.isPresent()) {
            staffRefreshToken = new StaffRefreshToken();
            staffRefreshToken.setId(existingToken.get().getId());
            staffRefreshToken.setStaff(existingToken.get().getStaff());
            staffRefreshToken.setExpiryDate(existingToken.get().getExpiryDate());
            staffRefreshToken.setToken(existingToken.get().getToken());
        }else{
            Staff dataStaff = staffRepository.findById(staffId).get();
            staffRefreshToken = new StaffRefreshToken();
            staffRefreshToken.setStaff(dataStaff);
            staffRefreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            staffRefreshToken.setToken(UUID.randomUUID().toString());
            staffRefreshToken = staffRefreshTokenRepository.save(staffRefreshToken);
        }
        staffTokenService.processStaffTokenAdd(staffId, jwt);
        return staffRefreshToken;
    }

    public StaffRefreshToken verifyExpiration(StaffRefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now()) < 0){
            staffRefreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public void deleteByStaffId(Long staffId){
        staffRefreshTokenRepository.deleteByStaff(staffRepository.findById(staffId).get());
    }
}
