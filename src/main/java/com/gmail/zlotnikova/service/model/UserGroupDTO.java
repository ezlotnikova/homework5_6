package com.gmail.zlotnikova.service.model;

public class UserGroupDTO {

    private String name;
    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("group name: ").append(this.name).
                append(", group size: ").append(this.size);
        return stringBuilder.toString();
    }

}