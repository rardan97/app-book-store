package com.blackcode.book_store_be.service.impl;

import com.blackcode.book_store_be.dto.user.*;
import com.blackcode.book_store_be.exception.TokenRefreshException;
import com.blackcode.book_store_be.exception.UsernameAlreadyExistsException;
import com.blackcode.book_store_be.model.user.User;
import com.blackcode.book_store_be.model.user.UserRefreshToken;
import com.blackcode.book_store_be.model.user.UserToken;
import com.blackcode.book_store_be.repository.UserRepository;
import com.blackcode.book_store_be.repository.UserTokenRepository;
import com.blackcode.book_store_be.security.jwt.JwtUtils;
import com.blackcode.book_store_be.security.service.StaffDetailsImpl;
import com.blackcode.book_store_be.security.service.UserDetailsImpl;
import com.blackcode.book_store_be.security.service.UserRefreshTokenService;
import com.blackcode.book_store_be.security.service.UserTokenService;
import com.blackcode.book_store_be.service.AuthUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final UserTokenRepository userTokenRepository;

    private final PasswordEncoder encoder;

    private final UserTokenService userTokenService;

    private final JwtUtils jwtUtils;

    private final UserRefreshTokenService userRefreshTokenService;

    public AuthUserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, UserTokenRepository userTokenRepository, PasswordEncoder encoder, UserTokenService userTokenService, JwtUtils jwtUtils, UserRefreshTokenService userRefreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
        this.encoder = encoder;
        this.userTokenService = userTokenService;
        this.jwtUtils = jwtUtils;
        this.userRefreshTokenService = userRefreshTokenService;
    }

    @Override
    public JwtResponse singIn(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtTokenUser(userDetails);
        userTokenService.processUserTokenRefresh(userDetails.getUsername(), jwt);

        UserRefreshToken refreshToken = userRefreshTokenService.createRefreshToken(jwt, userDetails.getUserId());
        return new JwtResponse(
                jwt,
                refreshToken.getToken(),
                userDetails.getUserId(),
                userDetails.getUsername());
    }

    @Override
    public MessageRes signUp(UserSignUpReq userSignUpReq) {
        if(userRepository.existsByUserName(userSignUpReq.getUsername())){
            throw new UsernameAlreadyExistsException("Username is already taken!");
        }

        User user = new User(
                userSignUpReq.getUserFullName(),
                userSignUpReq.getUsername(),
                encoder.encode(userSignUpReq.getPassword()),
                userSignUpReq.getUserEmail());
        userRepository.save(user);
        return new MessageRes("User registered successfully!");
    }

    @Override
    public UserTokenRefreshRes refreshToken(UserTokenRefreshReq request) {
        UserTokenRefreshRes userTokenRefreshRes = null;
        String requestRefreshToken = request.getRefreshToken();
        Optional<UserRefreshToken> userRefreshToken = userRefreshTokenService.findByToken(requestRefreshToken);
        if(userRefreshToken.isPresent()){
            UserRefreshToken userRefreshToken1 = userRefreshToken.get();
            userRefreshToken1 = userRefreshTokenService.verifyExpiration(userRefreshToken1);
            User user = userRefreshToken1.getUser();
            String token = jwtUtils.generateTokenFromUsername(user.getUserName());

            userTokenService.processUserTokenRefresh(user.getUserName(), token);
            userTokenRefreshRes = new UserTokenRefreshRes(token, requestRefreshToken);
        }else {
            throw new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!");
        }
        return userTokenRefreshRes;
    }

    @Override
    public MessageRes signOut(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String headerAuth = request.getHeader("Authorization");
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                String jwtToken = headerAuth.substring(7);
                StaffDetailsImpl userDetails = (StaffDetailsImpl) authentication.getPrincipal();
                Long userId = userDetails.getStaffId();

                Optional<UserToken> userTokenData = userTokenRepository.findByToken(jwtToken);
                if (userTokenData.isPresent()) {

                    userRefreshTokenService.deleteByUserId(userId);
                    UserToken userToken = userTokenData.get();
                    userToken.setIsActive(false);
                    userTokenRepository.save(userToken);
                    return new MessageRes("Logout successful!");
                } else {
                    return new MessageRes("Token not found, logout failed!");
                }
            } else {
                return new MessageRes("Authorization header is missing or invalid");
            }
        } else {
            return new MessageRes("User is not authenticated");
        }
    }
}
