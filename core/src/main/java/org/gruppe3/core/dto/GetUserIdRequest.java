package org.gruppe3.core.dto;

public class GetUserIdRequest {
    private final int userId;

    public GetUserIdRequest(int userId) {
        this.userId = userId;
    }   

    public int getUserId() {
        return userId;
    }
}
