package com.gmail.zlotnikova.repository.model;

public class UserGroup {

    private int id;
    private String name;

    public static Builder newBuilder() {
        return new Builder();
    }

    private UserGroup(Builder builder) {
        name = builder.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final class Builder {

        private String name;

        private Builder() {}

        public Builder name(String val) {
            name = val;
            return this;
        }

        public UserGroup build() {
            return new UserGroup(this);
        }

    }

}