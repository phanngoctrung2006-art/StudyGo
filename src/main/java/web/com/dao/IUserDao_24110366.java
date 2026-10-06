package web.com.dao;

import java.util.List;

import web.com.model.User_24110366;

public interface IUserDao_24110366 {
    User_24110366 findByUsername(String username);
    User_24110366 findByEmail(String email);
    List<User_24110366> findAll();
    List<User_24110366> findAll(int page, int pageSize);
    int count();
    void insert(User_24110366 user);
    void update(User_24110366 user);
    void delete(String username);
    void updateActive(String username, boolean active);
}
