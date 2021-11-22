package webServerHomework.dao;

import webServerHomework.model.User;

import java.util.*;

public interface UserDao {

    Optional<User> findById(long id);
    Optional<User> findRandomUser();
    Optional<User> findByLogin(String login);
}