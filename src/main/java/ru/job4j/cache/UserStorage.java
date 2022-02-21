package ru.job4j.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public boolean add(User user) {
        synchronized (this) {
            return users.putIfAbsent(user.getId(), user) == null;
        }
    }

    public boolean update(User user) {
        synchronized (this) {
            return users.replace(user.getId(), user) != null;
        }
    }

    public boolean delete(User user) {
        synchronized (this) {
            return users.remove(user.getId(), user);
        }
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User userFrom = users.get(fromId);
        User userTo = users.get(toId);
        if (userFrom != null && userTo != null && userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            result = true;
        }
        return result;
    }
}
