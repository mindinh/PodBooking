package com.md.PODBooking.service.impl;

import com.md.PODBooking.entity.User;
import com.md.PODBooking.repository.UsersRepository;
import com.md.PODBooking.service.LoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    @Value("${jwt.key}")
    private String keyJwt;

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    public LoginServiceImpl(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public String login(String phone, String password) {
        String token = "";
        Optional<User> user =usersRepository.findByUserPhone(phone);
        if (user.isPresent()) {
            User u = user.get();

            if (passwordEncoder.matches(password, u.getPassword())) {
                SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyJwt));

                token = Jwts.builder().subject(u.getRole().name()).signWith(key).compact();
            }
        }
        return token;
    }
}
