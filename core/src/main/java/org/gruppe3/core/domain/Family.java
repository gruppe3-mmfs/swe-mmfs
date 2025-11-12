package org.gruppe3.core.domain;

public class Family {

  private int familyId;
  private String familyName;

  public Family(int familyId, String familyName) {
    this.familyId = familyId;
    this.familyName = familyName;
  }

  public int getFamilyId() {
    return familyId;
  }

  public void setFamilyId(int familyId) {
    this.familyId = familyId;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }
}
