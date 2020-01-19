package com.gmail.zlotnikova.service.model;

public class NewUserDTO {

    private int id;
    private String username;
    private String password;
    private Boolean isActive;
    private int userGroupId;
    private int age;
    private String address;
    private String telephone;

    public static NewUserDTO.Builder newBuilder() {
        return new NewUserDTO.Builder();
    }

    private NewUserDTO(NewUserDTO.Builder builder) {
        username = builder.username;
        password = builder.password;
        isActive = builder.isActive;
        userGroupId = builder.userGroupId;
        age = builder.age;
        address = builder.address;
        telephone = builder.telephone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public static final class Builder {

        private String username;
        private String password;
        private Boolean isActive;
        private int userGroupId;
        private int age;
        private String address;
        private String telephone;

        private Builder() {}

        public NewUserDTO.Builder username(String val) {
            username = val;
            return this;
        }

        public NewUserDTO.Builder password(String val) {
            password = val;
            return this;
        }

        public NewUserDTO.Builder isActive(Boolean val) {
            isActive = val;
            return this;
        }

        public NewUserDTO.Builder userGroupId(int val) {
            userGroupId = val;
            return this;
        }

        public NewUserDTO.Builder age(int val) {
            age = val;
            return this;
        }

        public NewUserDTO.Builder address(String val) {
            address = val;
            return this;
        }

        public NewUserDTO.Builder telephone(String val) {
            telephone = val;
            return this;
        }

        public NewUserDTO build() {
            return new NewUserDTO(this);
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("user id ").append(this.id)
                .append(" username ").append(this.username)
                .append(" password ").append(this.password)
                .append(" is active ").append(this.isActive)
                .append(" user group ").append(this.userGroupId)
                .append(" age ").append(this.age)
                .append(" address ").append(this.address)
                .append(" telephone ").append(this.telephone);
        return stringBuilder.toString();
    }

}
