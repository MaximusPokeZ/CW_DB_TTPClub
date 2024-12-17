package ru.maximuspokez.ttpclub.ttpclub.service.Login;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maximuspokez.ttpclub.ttpclub.model.LoginRequest.LoginRequest;
import ru.maximuspokez.ttpclub.ttpclub.model.User.User;
import ru.maximuspokez.ttpclub.ttpclub.repository.User.UserRepository;

import java.util.Optional;

@Service
public class LoginService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean login(LoginRequest LoginUser) {
    Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(LoginUser.getEmail()));
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return passwordEncoder.matches(LoginUser.getPassword(), user.getPasswordHash());
    }
    return false;
  }
}
