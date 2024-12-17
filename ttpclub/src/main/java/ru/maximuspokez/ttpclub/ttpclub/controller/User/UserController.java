package ru.maximuspokez.ttpclub.ttpclub.controller.User;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.User.User;
import ru.maximuspokez.ttpclub.ttpclub.service.User.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<User>> getUsers() {
    List<User> users = userService.getUsers();
    return ResponseEntity.ok(users);
  }

  @PostMapping("/register")
  public ResponseEntity<User> addUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.createUser(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    try {
      User user = userService.getUserById(id);
      return ResponseEntity.ok(user);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @GetMapping("get_email/{email}")
  public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
    try {
      User user = userService.getUserByEmail(email);
      return ResponseEntity.ok(user);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @GetMapping("get_username/{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    try {
      User user = userService.getUserByUsername(username);
      return ResponseEntity.ok(user);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @GetMapping("get_phone/{phone}")
  public ResponseEntity<User> getUserByPhone(@PathVariable String phone) {
    try {
      User user = userService.getUserByPhone(phone);
      return ResponseEntity.ok(user);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @PutMapping("update_user/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
    try {
      User updatedUser = userService.updateUser(id, user);
      return ResponseEntity.ok(updatedUser);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @DeleteMapping("delete_user/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }

  @DeleteMapping("delete_user/email/{email}")
  public void deleteUserByEmail(@PathVariable String email) {
    userService.deleteUserByEmail(email);
  }

  @DeleteMapping("delete_user/phone/{phone}")
  public void deleteUserByPhone(@PathVariable String phone) {
    userService.deleteUserByPhone(phone);
  }

  @DeleteMapping("delete_user/username/{username}")
  public void deleteUserByUsername(@PathVariable String username) {
    userService.deleteUserByUsername(username);
  }
}
