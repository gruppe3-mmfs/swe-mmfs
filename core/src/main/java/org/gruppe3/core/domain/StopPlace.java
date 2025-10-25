package org.gruppe3.core.domain;

public record StopPlace(
    String id, String name, double latitude, double longitude, String description) {}
