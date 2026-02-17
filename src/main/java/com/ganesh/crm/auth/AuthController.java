package com.ganesh.crm.auth;

import com.ganesh.crm.security.CustomUserDetails;
import com.ganesh.crm.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getPhoneNumber(),
                                request.getPassword()
                        ));

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return jwtUtil.generateToken(userDetails);
    }
}
