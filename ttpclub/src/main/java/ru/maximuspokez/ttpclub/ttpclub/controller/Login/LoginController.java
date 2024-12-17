package ru.maximuspokez.ttpclub.ttpclub.controller.Login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.LoginRequest.LoginRequest;
import ru.maximuspokez.ttpclub.ttpclub.service.Login.LoginService;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
    boolean success = loginService.login(loginRequest);
    if (success) {
      return ResponseEntity.ok().body("Login success");
    } else {
      return ResponseEntity.badRequest().body("Login failed");
    }
  }

}
