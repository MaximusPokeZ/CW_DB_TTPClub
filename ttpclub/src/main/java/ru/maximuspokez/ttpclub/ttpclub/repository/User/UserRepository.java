package ru.maximuspokez.ttpclub.ttpclub.repository.User;


import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maximuspokez.ttpclub.ttpclub.model.User.User;

import java.util.List;

@Repository
@Primary
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findAllByOrderByRatingDesc();
  List<User> findAllByRole(String role);
  User findByUsername(String username);
  User findByEmail(String email);
  User findByPhone(String phone);
  void deleteByUsername(String username);
  void deleteByEmail(String email);
  void deleteByPhone(String phone);
}
