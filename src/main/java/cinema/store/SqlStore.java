package cinema.store;

import cinema.model.Account;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class SqlStore implements Store {
    private final BasicDataSource pool = new BasicDataSource();
    private static final Logger LOG = LoggerFactory.getLogger(SqlStore.class.getName());
    private static final ConcurrentHashMap<String, Account> CACHE = new ConcurrentHashMap<>();

    private SqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(new FileReader("cinema_db.properties"))) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Holder {
        private static final Store INST = new SqlStore();
    }

    public static Store instOf() {
        return Holder.INST;
    }

    @Override
    public void addToCache(String session, Account account) {
        CACHE.put(session, account);
    }

    @Override
    public Account getFromCache(String session) {
        return CACHE.get(session);
    }

    @Override
    public void deleteFromCache(String session) {
        CACHE.remove(session);
    }

    @Override
    public List<Integer> getOccupiedSeats() {
        List<Integer> seats = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select id from hall where occupied = 1")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    seats.add(it.getInt("id"));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return seats;
    }

    @Override
    public void reserveSeats(Account account) {
        List<Integer> seats = account.getSeats();
        String name = account.getName();
        String phone = account.getPhone();
        for (Integer seat : seats) {
            try (Connection cn = pool.getConnection();
                 PreparedStatement ps = cn.prepareStatement(
                         "update hall set occupied = 1 where id = ?")) {
                ps.setInt(1, seat);
                ps.execute();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
            addNewAccount(name, phone, seat);
        }
    }

    private void addNewAccount(String name, String phone, int seat) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "with ins as (insert into accounts (name, phone, seat) values (?, ?, ?) returning id) "
                             + "update hall set client = ins.id from ins where hall.id = ?",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setInt(3, seat);
            ps.setInt(4, seat);
            ps.execute();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
