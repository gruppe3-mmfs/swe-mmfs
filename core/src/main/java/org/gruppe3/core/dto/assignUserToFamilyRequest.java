package org.gruppe3.core.dto;

public class assignUserToFamilyRequest {
  private final int userId;
  private final int familyId;

  public assignUserToFamilyRequest(int userId, int familyId) {
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
