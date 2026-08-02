package com.sangam.connect.filters;

import com.sangam.connect.Utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String authorizationHeader=request.getHeader("Authorization");
            String token=null;
            String userName=null;
            if(authorizationHeader!=null&&authorizationHeader.startsWith("Bearer ")){
                token=authorizationHeader.substring(7);
                userName=jwtUtils.extractUserName(token);
                if(userName!=null&&SecurityContextHolder.getContext().getAuthentication()==null){
                    UserDetails userDetails= userDetailsService.loadUserByUsername(userName);
                    if(jwtUtils.validateToken(token,userDetails)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }

            }
        }catch (Exception e){
            System.out.println("vnjefnvkjefn");
        }
        filterChain.doFilter(request,response);
    }
}
