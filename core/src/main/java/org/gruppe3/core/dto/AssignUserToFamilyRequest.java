package org.gruppe3.core.dto;

public class AssignUserToFamilyRequest {
  private final int userId;
  private final int familyId;

  public AssignUserToFamilyRequest(int userId, int familyId) {
    this.userId = userId;
    this.familyId = familyId;
  }

  public int getUserId() {
    return userId;
  }

  public int getFamilyId() {
    return familyId;
  }
}
