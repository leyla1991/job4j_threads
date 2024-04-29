package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
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
        if (getById(fromId).isPresent() && getById(toId).isPresent()) {
            Account accountFrom = getById(fromId).get();
            Account accountTo = getById(toId).get();
            if (accountFrom.amount() - amount >= 0) {
                accounts.put(fromId, new Account(fromId, (accountFrom.amount() - amount)));
                accounts.put(toId, new Account(toId, (accountTo.amount() + amount)));
                rsl = true;
            }
        }
        return rsl;
    }
}