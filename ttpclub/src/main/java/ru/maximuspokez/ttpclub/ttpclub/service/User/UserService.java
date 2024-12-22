package ru.maximuspokez.ttpclub.ttpclub.service.User;

import ru.maximuspokez.ttpclub.ttpclub.model.User.User;

import java.util.List;

public interface UserService {
  List<User> getUsers();
  List<User> getCoaches();
  List<User> getUsersSortedByRating();
  User getUserById(Long id);
  User getUserByEmail(String email);
  User getUserByPhone(String phone);
  User getUserByUsername(String username);
  User createUser(User user);
  User updateUser(Long id, User user);
  void deleteUser(Long id);
  void deleteUserByEmail(String email);
  void deleteUserByPhone(String phone);
  void deleteUserByUsername(String username);
}
