package ru.maximuspokez.ttpclub.ttpclub.model.User.DTO;

import ru.maximuspokez.ttpclub.ttpclub.model.User.User;

import java.time.LocalDate;
import java.time.Period;

public class UserDto {

  private Long id;
  private String username;
  private String email;
  private String phone;
  private String role;
  private Integer age;

  public UserDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.phone = user.getPhone();
    this.role = user.getRole();
    this.age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
}
