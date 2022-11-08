package com.onimko;

import java.time.LocalDateTime;

public class PojoMessage {
    String name;
    int count;
    LocalDateTime createdAt;

    public PojoMessage(String name, int count) {
        this.name = name;
        this.count = count;
        this.createdAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
