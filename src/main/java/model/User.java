package model;

import exceptions.InvalidTaskException;

public class User {
    private String username;
    private String password;

    public User(String username, String password) throws InvalidTaskException {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidTaskException("نام کاربری نمی‌تواند خالی باشد!");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidTaskException("رمز عبور نمی‌تواند خالی باشد!");
        }
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws InvalidTaskException {
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidTaskException("نام کاربری نمی‌تواند خالی باشد!");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidTaskException {
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidTaskException("رمز عبور نمی‌تواند خالی باشد!");
        }
        this.password = password;
    }
}
