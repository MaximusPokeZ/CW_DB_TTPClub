package ru.maximuspokez.ttpclub.ttpclub.controller.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maximuspokez.ttpclub.ttpclub.model.User.DTO.UserDto;
import ru.maximuspokez.ttpclub.ttpclub.model.User.User;
import ru.maximuspokez.ttpclub.ttpclub.service.User.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getUsers() {
    List<User> users = userService.getUsers();
    return ResponseEntity.ok(users.stream().map(UserDto::new).collect(Collectors.toList()));
  }

  @GetMapping("/sorted_by_rating")
  public ResponseEntity<List<UserDto>> getUsersByRating() {
    List<User> users = userService.getUsersSortedByRating();
    return ResponseEntity.ok(users.stream().map(UserDto::new).collect(Collectors.toList()));
  }

  @PostMapping("/register")
  public ResponseEntity<User> addUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.createUser(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    User user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

  @GetMapping("get_email/{email}")
  public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
    User user = userService.getUserByEmail(email);
    return ResponseEntity.ok(user);
  }

  @GetMapping("get_username/{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    User user = userService.getUserByUsername(username);
    return ResponseEntity.ok(user);
  }

  @GetMapping("get_phone/{phone}")
  public ResponseEntity<User> getUserByPhone(@PathVariable String phone) {
    User user = userService.getUserByPhone(phone);
    return ResponseEntity.ok(user);
  }

  @PutMapping("update_user/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
      User updatedUser = userService.updateUser(id, user);
      return ResponseEntity.ok(updatedUser);
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
