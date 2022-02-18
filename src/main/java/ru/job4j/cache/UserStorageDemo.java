package ru.job4j.cache;

public class UserStorageDemo {
    public static void main(String[] args) {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        User user2 = new User(2, 200);
        storage.add(user1);
        storage.add(user2);
        System.out.println("Amount user 1: " + user1.getAmount() + "$");
        System.out.println("Amount user 2: " + user2.getAmount() + "$");
        System.out.println("Transfering money (50$) from User1 to User2");
        storage.transfer(1, 2, 50);
        System.out.println("Amount user 1: " + user1.getAmount() + "$");
        System.out.println("Amount user 2: " + user2.getAmount() + "$");
    }
}
