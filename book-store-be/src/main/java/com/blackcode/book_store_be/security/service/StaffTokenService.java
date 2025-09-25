package com.blackcode.book_store_be.security.service;

import com.blackcode.book_store_be.model.staff.Staff;
import com.blackcode.book_store_be.model.staff.StaffToken;
import com.blackcode.book_store_be.model.user.User;
import com.blackcode.book_store_be.model.user.UserToken;
import com.blackcode.book_store_be.repository.StaffRepository;
import com.blackcode.book_store_be.repository.StaffTokenRepository;
import com.blackcode.book_store_be.repository.UserRepository;
import com.blackcode.book_store_be.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class StaffTokenService {
    @Value("${blackcode.app.jwtRefreshExpirationMs}")
    private int refreshTokenDurationMs;

    @Value("${blackcode.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    StaffTokenRepository staffTokenRepository;

    @Autowired
    StaffRepository staffRepository;

    public void processStaffTokenAdd(Long staffId, String jwt){
        Date date = new Date((new Date()).getTime() + jwtExpirationMs);
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Optional<StaffToken> staffTokenData = staffTokenRepository.findByStaffId(staffId);
        if(staffTokenData.isPresent()){
            staffTokenData.get().setToken(jwt);
            staffTokenData.get().setIsActive(true);
            staffTokenData.get().setExpiryDate(localDateTime);
            staffTokenData.get().setUpdatedAt(LocalDateTime.now());
            staffTokenRepository.save(staffTokenData.get());
        }else{
            StaffToken staffToken = new StaffToken();
            staffToken.setToken(jwt);
            staffToken.setStaffId(staffId);
            staffToken.setIsActive(true);
            staffToken.setExpiryDate(localDateTime);
            staffToken.setCreatedAt(LocalDateTime.now());
            staffToken.setUpdatedAt(LocalDateTime.now());
            staffTokenRepository.save(staffToken);
        }

    }

    public void processStaffTokenRefresh(String userName, String jwt){
        Date date = new Date((new Date()).getTime() + jwtExpirationMs);
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Optional<Staff> dataStaff = staffRepository.findByStaffUsername(userName);
        if(dataStaff.isPresent()){
            Optional<StaffToken> staffTokenData = staffTokenRepository.findByStaffId(dataStaff.get().getStaffId());
            if(staffTokenData.isPresent()){
                staffTokenData.get().setToken(jwt);
                staffTokenData.get().setIsActive(true);
                staffTokenData.get().setExpiryDate(localDateTime);
                staffTokenData.get().setUpdatedAt(LocalDateTime.now());
                staffTokenRepository.save(staffTokenData.get());
            }
        }
    }
}
