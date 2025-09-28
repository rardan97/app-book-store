package com.blackcode.book_store_be.security.jwt;

import com.blackcode.book_store_be.model.staff.Staff;
import com.blackcode.book_store_be.model.staff.StaffToken;
import com.blackcode.book_store_be.repository.StaffRepository;
import com.blackcode.book_store_be.repository.StaffTokenRepository;
import com.blackcode.book_store_be.security.service.StaffDetailsServiceImpl;
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
public class StaffAuthTokenFilter extends OncePerRequestFilter {


    private final JwtUtils jwtUtils;


    private final StaffDetailsServiceImpl staffDetailsService;


    private final StaffTokenRepository staffTokenRepository;


    private final StaffRepository staffRepository;

    private static final Logger logger = LoggerFactory.getLogger(StaffAuthTokenFilter.class);

    public StaffAuthTokenFilter(JwtUtils jwtUtils, StaffDetailsServiceImpl staffDetailsService, StaffTokenRepository staffTokenRepository, StaffRepository staffRepository) {
        this.jwtUtils = jwtUtils;
        this.staffDetailsService = staffDetailsService;
        this.staffTokenRepository = staffTokenRepository;
        this.staffRepository = staffRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            System.out.println("Proccess 1 authtokenfilter");
            String jwt = parseJwt(request);
            System.out.println("Incoming JWT Token: " + jwt);
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                System.out.println("Proccess 2 authtokenfilter");
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                Optional<Staff> dataStaff = staffRepository.findByStaffUsername(username);
                if(dataStaff.isPresent()){
                    System.out.println("Proccess 3 authtokenfilter");
                    Staff staff = dataStaff.get();
                    Optional<StaffToken> staffToken = staffTokenRepository.findByStaffId(staff.getStaffId());
                    if(staffToken.isPresent()){
                        System.out.println("Proccess 4 authtokenfilter");
                        StaffToken staffToken1 = staffToken.get();
                        if(!staffToken1.getToken().equals(jwt)){
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has been invalidated");
                            return;
                        }

                        if(!staffToken1.getIsActive()){
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is inactive or expired");
                            return;
                        }
                        UserDetails userDetails = staffDetailsService.loadUserByUsername(username);
                        System.out.println("Authorities: " + userDetails.getAuthorities());
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    }else{
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token not found");
                        return;
                    }
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
        // Only filter requests starting with /api/staff/
        return !request.getRequestURI().startsWith("/api/staff/");
    }
}
