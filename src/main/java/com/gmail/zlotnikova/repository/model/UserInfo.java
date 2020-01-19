package com.gmail.zlotnikova.repository.model;

public class UserInfo {

    private int userId;
    private String address;
    private String telephone;

    public static UserInfo.Builder newBuilder() {
        return new UserInfo.Builder();
    }

    private UserInfo(UserInfo.Builder builder) {
        userId = builder.userId;
        address = builder.address;
        telephone = builder.telephone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

        private int userId;
        private String address;
        private String telephone;

        private Builder() {}

        public UserInfo.Builder userId(int val) {
            userId = val;
            return this;
        }

        public UserInfo.Builder address(String val) {
            address = val;
            return this;
        }

        public UserInfo.Builder telephone(String val) {
            telephone = val;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(this);
        }

    }

}
