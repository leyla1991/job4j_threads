package ru.job4j.ref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return users.get(id);
    }

    public List<User> findAll() {
        List<User> rsl = new ArrayList<>();
        for (User u : users.values()) {
            rsl.add(User.of(u.getName()));
        }
        return rsl;
    }
}