package cinema.store;

import cinema.model.Account;

import java.util.List;

public interface Store {

    void addToCache(String session, Account account);

    Account getFromCache(String session);

    void deleteFromCache(String session);

    List<Integer> getOccupiedSeats();

    void reserveSeats(Account account);
}
