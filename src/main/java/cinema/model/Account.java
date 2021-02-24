package cinema.model;

import java.util.List;
import java.util.Objects;

public class Account {
    private int id;
    private String name;
    private String phone;
    private List<Integer> seats;

    public Account(List<Integer> seats) {
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id && Objects.equals(name, account.name) && Objects.equals(phone, account.phone) && Objects.equals(seats, account.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, seats);
    }
}
