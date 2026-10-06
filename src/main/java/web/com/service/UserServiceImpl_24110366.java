package web.com.service;

import web.com.dao.IUserDao_24110366;
import web.com.dao.UserDaoImpl_24110366;
import web.com.model.User_24110366;

import java.util.List;

public class UserServiceImpl_24110366 implements IUserService_24110366 {

    private final IUserDao_24110366 userDao;

    public UserServiceImpl_24110366() {
        this.userDao = new UserDaoImpl_24110366();
    }

    public UserServiceImpl_24110366(IUserDao_24110366 userDao) {
        this.userDao = userDao;
    }

    @Override
    public User_24110366 login(String username, String password) {
        User_24110366 user = userDao.findByUsername(username);
        if (user != null && user.getPassword() != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public User_24110366 findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User_24110366 findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User_24110366> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User_24110366> findAll(int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 6;
        return userDao.findAll(page, pageSize);
    }

    @Override
    public int count() {
        return userDao.count();
    }

    @Override
    public void insert(User_24110366 user) {
        userDao.insert(user);
    }

    @Override
    public void update(User_24110366 user) {
        userDao.update(user);
    }

    @Override
    public void delete(String username) {
        userDao.delete(username);
    }

    @Override
    public void updateActive(String username, boolean active) {
        userDao.updateActive(username, active);
    }

    @Override
    public boolean register(User_24110366 user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            return false;
        }
        if (userDao.findByEmail(user.getEmail()) != null) {
            return false;
        }
        userDao.insert(user);
        return true;
    }
}
