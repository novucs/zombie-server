package net.novucs.zombieserver.level;

import java.util.List;
import java.util.Objects;

public class Room {

    private final String name;
    private final String description;
    private final List<Entrance> entrances;
    private final List<Item> items;
    private int zombies;

    public Room(String name, String description, List<Entrance> entrances, List<Item> items, int zombies) {
        this.name = name;
        this.description = description;
        this.entrances = entrances;
        this.items = items;
        this.zombies = zombies;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Entrance> getEntrances() {
        return entrances;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getZombies() {
        return zombies;
    }

    public void setZombies(int zombies) {
        this.zombies = zombies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return zombies == room.zombies &&
                Objects.equals(name, room.name) &&
                Objects.equals(description, room.description) &&
                Objects.equals(entrances, room.entrances) &&
                Objects.equals(items, room.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, entrances, items, zombies);
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", entrances=" + entrances +
                ", items=" + items +
                ", zombies=" + zombies +
                '}';
    }
}
