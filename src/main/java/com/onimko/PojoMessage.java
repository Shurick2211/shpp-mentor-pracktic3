package com.onimko;

import java.time.LocalDateTime;

public class PojoMessage {
    String name;
    int count;
    LocalDateTime createdAt;

    public PojoMessage(String name, int count, LocalDateTime createdAt) {
        this.name = name;
        this.count = count;
        this.createdAt = createdAt;
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

    @Override
    public String toString() {
        return "pojo{" +
                "name=\"" + name + '\"' +
                ", count=" + count +
                ", createdAt=" + createdAt +
                '}';
    }
}
