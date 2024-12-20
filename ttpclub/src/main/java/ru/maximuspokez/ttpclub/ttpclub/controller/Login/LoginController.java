package ru.maximuspokez.ttpclub.ttpclub.controller.Login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.LoginRequest.LoginRequest;
import ru.maximuspokez.ttpclub.ttpclub.model.TokenResponse.TokenResponse;
import ru.maximuspokez.ttpclub.ttpclub.service.Login.LoginService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  //TODO jwt
  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
    TokenResponse tokens = loginService.login(loginRequest);
    if (tokens != null) {
      return ResponseEntity.ok(tokens);
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<String> refresh(@RequestBody String refreshToken) {
    String newAccessToken = loginService.refreshToken(refreshToken);
    if (newAccessToken != null) {
      return ResponseEntity.ok(newAccessToken);
    } else {
      return ResponseEntity.badRequest().body("Invalid refresh token");
    }
  }

}
