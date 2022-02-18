package ru.job4j.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    /*
    true - если абонента не было и абонент был добавлен в хранилище
    false - если уже есть абонент с таким id
     */
    public boolean add(User user) {
        boolean result = true;
        if (users.containsKey(user.getId())) {
            result = false;
        } else {
            users.put(user.getId(), user);
        }
        return result;
    }
    /*
    true - если абонент был в хранилище и он был обновлен
    false - если абонента не было в хранилище или он не был изменен.
     */
    public boolean update(User user) {
        boolean result = true;
        if (!users.containsKey(user.getId()) || users.get(user.getId()).equals(user)) {
            result = false;
        } else {
            users.computeIfPresent(user.getId(), (key, oldUser) -> user);
        }
        return result;
    }

    public boolean delete(User user) {
        return users.remove(user.getId(), user);
    }
    /*
        1. Найти клиента который перечисляет деньги и кому перечисляют
        2. Проверить что у первого баланс больше или равен amount
        3. Уменьшить баланс у первого клиента и увеличить баланс у второго.
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (users.containsKey(fromId)
                && users.containsKey(toId)
                && users.get(fromId).getAmount() >= amount) {
            int fromAmount = users.get(fromId).getAmount();
            int toAmount = users.get(toId).getAmount();
            users.get(fromId).setAmount(fromAmount - amount); /*Уменьшаем баланс отправителя*/
            users.get(toId).setAmount(toAmount + amount); /*Увеличиваем баланс получателя*/
            result = true;
        }
        return result;
    }
}
