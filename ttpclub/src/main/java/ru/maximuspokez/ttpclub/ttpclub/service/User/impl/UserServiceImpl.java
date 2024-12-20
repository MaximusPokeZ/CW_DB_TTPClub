package ru.maximuspokez.ttpclub.ttpclub.service.User.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximuspokez.ttpclub.ttpclub.model.User.User;
import ru.maximuspokez.ttpclub.ttpclub.repository.User.UserRepository;
import ru.maximuspokez.ttpclub.ttpclub.service.User.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public List<User> getUsersSortedByRating() {
    return userRepository.findAllByOrderByRatingDesc();
  }

  @Override
  public User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(("User with ID " + id + " not found")));
  }

  @Override
  public User getUserByEmail(String email) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new EntityNotFoundException("User with email " + email + " not found");
    }
    return userRepository.findByEmail(email);
  }

  @Override
  public User getUserByPhone(String phone) {
    User user = userRepository.findByPhone(phone);
    if (user == null) {
      throw new EntityNotFoundException("User with phone " + phone + " not found");
    }
    return userRepository.findByPhone(phone);
  }

  @Override
  public User getUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new EntityNotFoundException("User with username " + username + " not found");
    }
    return userRepository.findByUsername(username);
  }

  @Override
  public User createUser(User user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    Integer age = calculateAge(user.getBirthDate());
    user.setAge(age);
    user.setPasswordHash(encodedPassword);
    return userRepository.save(user);
  }

  private Integer calculateAge(LocalDate birthDate) {
    if (birthDate == null) {
      throw new IllegalArgumentException("Birth date cannot be null");
    }
    return Period.between(birthDate, LocalDate.now()).getYears();
  }

  @Override
  public User updateUser(Long id, User user) {
    Optional<User> userOptional = userRepository.findById(id);
    if (userOptional.isEmpty()) {
      throw new EntityNotFoundException("User with ID " + id + " not found");
    }

    User userToUpdate = userOptional.get();
    userToUpdate.setUsername(user.getUsername());
    userToUpdate.setEmail(user.getEmail());
    userToUpdate.setCreatedAt(user.getCreatedAt());
    userToUpdate.setRole(user.getRole());
    userToUpdate.setRating(user.getRating());
    userToUpdate.setPhone(user.getPhone());
    userToUpdate.setAge(user.getAge());
    userToUpdate.setBirthDate(user.getBirthDate());


    return userRepository.save(userToUpdate);
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteUserByEmail(String email) {
    userRepository.deleteByEmail(email);
  }

  @Override
  @Transactional
  public void deleteUserByPhone(String phone) {
    userRepository.deleteByPhone(phone);
  }

  @Override
  @Transactional
  public void deleteUserByUsername(String username) {
    userRepository.deleteByUsername(username);
  }
}
