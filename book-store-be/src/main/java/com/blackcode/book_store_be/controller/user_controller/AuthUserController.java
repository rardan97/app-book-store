package com.blackcode.book_store_be.controller.user_controller;

import com.blackcode.book_store_be.dto.user.*;
import com.blackcode.book_store_be.exception.TokenRefreshException;
import com.blackcode.book_store_be.model.user.User;
import com.blackcode.book_store_be.model.user.UserRefreshToken;
import com.blackcode.book_store_be.model.user.UserToken;
import com.blackcode.book_store_be.repository.UserRepository;
import com.blackcode.book_store_be.repository.UserTokenRepository;
import com.blackcode.book_store_be.security.jwt.JwtUtils;
import com.blackcode.book_store_be.security.service.UserRefreshTokenService;
import com.blackcode.book_store_be.security.service.UserDetailsImpl;
import com.blackcode.book_store_be.security.service.UserTokenService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/user")
public class AuthUserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTokenRepository userTokenRepository;



    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRefreshTokenService userRefreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateJwtTokenUser(userDetails);
            userTokenService.processUserTokenRefresh(userDetails.getUsername(), jwt);

            UserRefreshToken refreshToken = userRefreshTokenService.createRefreshToken(jwt, userDetails.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(
                    jwt,
                    refreshToken.getToken(),
                    userDetails.getUserId(),
                    userDetails.getUsername()));
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignUpReq signupRequest){
        System.out.println("username : "+signupRequest.getUsername());
        if(userRepository.existsByUserName(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageRes("Error : Username is already taken!"));
        }

        User user = new User(
                signupRequest.getUserFullName(),
                signupRequest.getUsername(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getUserEmail());
        userRepository.save(user);
        return ResponseEntity.ok(new MessageRes("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody UserTokenRefreshReq request){
        String requestRefreshToken = request.getRefreshToken();
        return userRefreshTokenService.findByToken(requestRefreshToken)
                .map(userRefreshTokenService::verifyExpiration)
                .map(UserRefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUserName());
                    userTokenService.processUserTokenRefresh(user.getUserName(), token);
                    return ResponseEntity.ok(new UserTokenRefreshRes(token, requestRefreshToken));
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
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                Long userId = userDetails.getUserId();
                String jwtToken = token.substring(7);
                Optional<UserToken> userTokenData = userTokenRepository.findByToken(jwtToken);
                if(userTokenData.isPresent()){
                    System.out.println("Validasi 3");
                    userRefreshTokenService.deleteByUserId(userId);
                    userTokenData.get().setIsActive(false);
                    userTokenRepository.save(userTokenData.get());
                    return ResponseEntity.ok(new MessageRes("Log out successful!"));
                }else{
                    System.out.println("check token not found");
                    return ResponseEntity.ok(new MessageRes("Log out Failed!!!"));
                }
            } else {
                return ResponseEntity.ok(new MessageRes("Authorization not null"));
            }
        }else {
            return ResponseEntity.ok(new MessageRes("authentication not found"));
        }
    }
}
