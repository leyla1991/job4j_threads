package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl = false;
        if (!accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        boolean rsl = false;
        if (accounts.containsKey(account.id())) {
                accounts.put(account.id(), account);
                rsl = true;
            }
        return rsl;
        }

    public synchronized void delete(int id) {
            if (accounts.containsKey(id)) {
                accounts.remove(id);
        }
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        if (accounts.containsKey(fromId) && accounts.containsKey(toId)) {
            if (accounts.get(fromId).amount() - amount >= 0) {
                accounts.put(fromId, new Account(fromId, (accounts.get(fromId).amount() - amount)));
                accounts.put(toId, new Account(toId, (accounts.get(toId).amount() + amount)));
                rsl = true;
            }
        }
        return rsl;
    }
}