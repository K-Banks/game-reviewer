package models;

import java.util.Objects;

public class Developer {
    private String name;
    private int id;

    public Developer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id &&
                Objects.equals(name, developer.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id);
    }
}
