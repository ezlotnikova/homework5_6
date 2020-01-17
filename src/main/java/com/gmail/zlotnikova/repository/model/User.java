package com.gmail.zlotnikova.repository.model;

public class User {

    private int id;
    private String username;
    private String password;
    private Boolean isActive;
    private int userGroupId;
    private int age;

    public static User.Builder newBuilder() {
        return new User.Builder();
    }

    private User(User.Builder builder) {
        username = builder.username;
        password = builder.password;
        isActive = builder.isActive;
        userGroupId = builder.userGroupId;
        age = builder.age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static final class Builder {

        private String username;
        private String password;
        private Boolean isActive;
        private int userGroupId;
        private int age;

        private Builder() {}

        public User.Builder username(String val) {
            username = val;
            return this;
        }

        public User.Builder password(String val) {
            password = val;
            return this;
        }

        public User.Builder isActive(Boolean val) {
            isActive = val;
            return this;
        }

        public User.Builder userGroupId(int val) {
            userGroupId = val;
            return this;
        }

        public User.Builder age(int val) {
            age = val;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("user id ").append(this.id).
                append(" username ").append(this.username).
                append(" password ").append(this.password).
                append(" is active=").append(this.isActive).
                append(" user group ").append(this.userGroupId).
                append(" age=").append(this.age);
        return stringBuilder.toString();
    }

}