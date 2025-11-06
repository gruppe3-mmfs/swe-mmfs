package org.gruppe3.core.domain;

public class User {

  private int userId;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String email;
  private int familyId;

  public User(
      int userId,
      String firstName,
      String lastName,
      String phoneNumber,
      String email,
      int familyId) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.familyId = familyId;
  }

  public User(int userId, String firstName, String lastName, String phoneNumber, String email) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public User(String firstName, String lastName, String phoneNumber, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  @Override
  public String toString() {
    return this.firstName + " " + this.lastName;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getFamilyId() {
    return familyId;
  }

  public void setFamilyId(int familyId) {
    this.familyId = familyId;
  }
}
