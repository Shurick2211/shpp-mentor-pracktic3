package com.onimko.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class PojoMessage{
    private String name;
    private int count;
    private LocalDateTime createdAt;
    public PojoMessage(@JsonProperty(value = "name")String name, @JsonProperty(value = "count")int count,
                       @JsonProperty(value = "created_at")
                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
                       LocalDateTime createdAt) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PojoMessage)) return false;
        PojoMessage that = (PojoMessage) o;
        return getCount() == that.getCount() && getName().equals(that.getName()) && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCount(), getCreatedAt());
    }

    @Override
    public String toString() {
        return "pojo{name:"+name + ", count:" + count + ", created_at:" + createdAt + "}";
    }
}
