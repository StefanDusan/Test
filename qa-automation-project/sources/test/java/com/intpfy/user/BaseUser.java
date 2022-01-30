package com.intpfy.user;

import java.util.Objects;

public abstract class BaseUser {

    private final String email;
    private final String password;

    public BaseUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseUser)) return false;
        BaseUser baseUser = (BaseUser) o;
        return Objects.equals(getEmail(), baseUser.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
