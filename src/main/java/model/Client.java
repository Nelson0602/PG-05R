package model;

import java.time.LocalDate;

public class Client extends Person {
    private String service;
    private LocalDate date;

    public Client(String id, String name, int age, String service, LocalDate date) {
        super(id, name, age, 0.0, 0.0);
        this.service = service;
        this.date    = date;
    }

    public String    getService() { return service; }
    public LocalDate getDate()    { return date; }

    @Override
    public String getRoleDescription() { return "Bank Client - " + service; }

    @Override
    public String toString() {
        return "Client{id=" + getId() + ", name='" + getName() + "', age=" + getAge()
                + ", service name='" + service + "', date=" + date + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        return getId().equals(((Client) o).getId());
    }

    @Override
    public int hashCode() { return getId().hashCode(); }
}

