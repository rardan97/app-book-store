package com.blackcode.book_store_be.security.jwt;

import com.blackcode.book_store_be.model.user.User;
import com.blackcode.book_store_be.model.user.UserToken;
import com.blackcode.book_store_be.repository.UserRepository;
import com.blackcode.book_store_be.repository.UserTokenRepository;
import com.blackcode.book_store_be.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class UserAuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserTokenRepository userTokenRepository;

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserAuthTokenFilter.class);

    public UserAuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService, UserTokenRepository userTokenRepository, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.userTokenRepository = userTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                Optional<User> dataUser = userRepository.findByUserName(username);

                if(dataUser.isPresent()){
                    User user = dataUser.get();
                    Optional<UserToken> userTokenData = userTokenRepository.findByUserId(user.getUserId());

                    if(userTokenData.isPresent()){
                        UserToken userToken = userTokenData.get();

                        if(!userToken.getToken().equals(jwt)){
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has been invalidated");
                            return;
                        }

                        if(!userToken.getIsActive()){
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is inactive or expired");
                            return;
                        }

                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }else{
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token not found");
                    return;
                }
            }
        }catch (Exception e){
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Only filter requests starting with /api/user/
        return !request.getRequestURI().startsWith("/api/user/");
    }


}
