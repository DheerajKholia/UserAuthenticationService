package com.ecommerce.authentication.services;

import com.ecommerce.authentication.models.Session;
import com.ecommerce.authentication.models.SessionState;
import com.ecommerce.authentication.models.User;
import com.ecommerce.authentication.repositories.SessionRepo;
import com.ecommerce.authentication.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private SecretKey secretKey;

    @Override
    public User signUp(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return null;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }

    @Override
    public Pair<User, MultiValueMap<String, String>> login(String username, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        //Token generation
//        String message = "{\n" +
//                "   \"email\": \"anurag@scaler.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"instructor\",\n" +
//                "      \"buddy\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"25thJuly2024\"\n" +
//                "}";
        Map<String ,Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("user_id_", user.getId());
        claims.put("roles", user.getRoles());
        long nowInMillis = System.currentTimeMillis();
        claims.put("iat", nowInMillis);
        claims.put("exp", nowInMillis+1000000);

//        byte[] content = message.getBytes(StandardCharsets.UTF_8);
//        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        SecretKey secretKey = algorithm.key().build();

        //String token = Jwts.builder().content(content).signWith(secretKey).compact();
        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,token);

        Session session = new Session();
        session.setSessionState(SessionState.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        sessionRepo.save(session);

        return new Pair<User,MultiValueMap<String,String>>(user,headers);
    }

    @Override
    public Boolean validateToken(String token, Long userid) {
        Optional<Session> optionalSession = sessionRepo.findByToken(token);
        if (optionalSession.isEmpty()) {
            System.out.println("Token not found");
            return false;
        }
        Session session = optionalSession.get();
//        if (session.getSessionState() != SessionState.ACTIVE) {
//            System.out.println("Session not active");
//            return false;
//        }
        String storedToken = session.getToken();
        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = parser.parseSignedClaims(storedToken).getPayload();

        Long tokenExpiryTime = (Long) claims.get("exp");
        Long currentTime = System.currentTimeMillis();
        System.out.println("Token expiry time : " + tokenExpiryTime);
        System.out.println("Current time : " + currentTime);
        if (tokenExpiryTime < currentTime) {
            System.out.println("Token is expired");
            //set state to expired in DB
            return false;
        }

        User user = userRepository.findById(userid).get();
        String email = user.getEmail();
        String tokenEmail = (String) claims.get("email");
        if(!email.equals(tokenEmail)) {
            System.out.println("Email does not match");
            return false;
        }
        return true;
    }
}
