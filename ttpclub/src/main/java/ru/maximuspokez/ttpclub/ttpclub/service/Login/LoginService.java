package ru.maximuspokez.ttpclub.ttpclub.service.Login;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maximuspokez.ttpclub.ttpclub.model.LoginRequest.LoginRequest;
import ru.maximuspokez.ttpclub.ttpclub.model.TokenResponse.TokenResponse;
import ru.maximuspokez.ttpclub.ttpclub.model.User.User;
import ru.maximuspokez.ttpclub.ttpclub.repository.User.UserRepository;

import java.util.Date;
import java.util.Optional;


//TODO jwt
@Service
public class LoginService {
  private static final String SECRET_KEY = "ImJustACoolGuy";
  private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 10; // 1 min
  private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 30; // 2 * min

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public TokenResponse login(LoginRequest loginRequest) {
    Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(loginRequest.getEmail()));
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
        String accessToken = generateToken(user.getEmail(), ACCESS_TOKEN_EXPIRATION);
        String refreshToken = generateToken(user.getEmail(), REFRESH_TOKEN_EXPIRATION);
        return new TokenResponse(accessToken, refreshToken);
      }
    }
    return null;
  }

  public String refreshToken(String refreshToken) {
    try {
      String email = Jwts.parser()
              .setSigningKey(SECRET_KEY)
              .parseClaimsJws(refreshToken)
              .getBody()
              .getSubject();
      return generateToken(email, ACCESS_TOKEN_EXPIRATION);
    } catch (Exception e) {
      return null;
    }
  }

  private String generateToken(String email, long expirationTime) {
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
  }
}
