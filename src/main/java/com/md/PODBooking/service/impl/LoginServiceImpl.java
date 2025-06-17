package com.md.PODBooking.service.impl;

import com.md.PODBooking.dto.AuthenticationResponse;
import com.md.PODBooking.entity.User;
import com.md.PODBooking.repository.UsersRepository;
import com.md.PODBooking.service.LoginService;
import com.md.PODBooking.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    @Value("${jwt.key}")
    private String keyJwt;

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JwtHelper jwtHelper;

    public LoginServiceImpl(PasswordEncoder passwordEncoder, UsersRepository usersRepository, JwtHelper jwtHelper) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public AuthenticationResponse login(String phone, String password) {
        String accessToken = "", refreshToken = "";

        Optional<User> user = usersRepository.findByUserPhone(phone);
        if (user.isPresent()) {
            User u = user.get();

            if (passwordEncoder.matches(password, u.getPassword())) {
//                SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJwt));
//
//                token = Jwts.builder().subject(u.getRole().name()).signWith(key).compact();
                accessToken = jwtHelper.generateAccessToken(String.valueOf(u.getRole()));
                refreshToken = jwtHelper.generateRefreshToken(String.valueOf(u.getRole()));
            }
        }
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        String accessToken = "";
        System.out.println(refreshToken);

        if (refreshToken != null && !jwtHelper.isTokenExpired(refreshToken)) {
            String role = jwtHelper.getDataToken(refreshToken);
//            System.out.println("Refresh: " + role);
            accessToken = jwtHelper.generateAccessToken(role);
        }

        return accessToken;
    }
}
