package com.gmail.zlotnikova.service.model;

public class UserInfoDTO {

    private int userId;
    private String address;
    private String telephone;

    public static UserInfoDTO.Builder newBuilder() {
        return new UserInfoDTO.Builder();
    }

    private UserInfoDTO(UserInfoDTO.Builder builder) {
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

        public UserInfoDTO.Builder userId(int val) {
            userId = val;
            return this;
        }

        public UserInfoDTO.Builder address(String val) {
            address = val;
            return this;
        }

        public UserInfoDTO.Builder telephone(String val) {
            telephone = val;
            return this;
        }

        public UserInfoDTO build() {
            return new UserInfoDTO(this);
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("user id ").append(this.userId)
                .append(" address ").append(this.address)
                .append(" telephone ").append(this.telephone);
        return stringBuilder.toString();
    }

}
