package net.novucs.zombieserver.level;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;

import java.util.Map;
import java.util.Objects;

public class Room {

    private final String name;
    private final String description;
    private final ImmutableMap<Direction, Entrance> entrances;
    private final Multiset<Item> items;
    private int zombies;

    public Room(String name, String description, ImmutableMap<Direction, Entrance> entrances, Multiset<Item> items, int zombies) {
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

    public Map<Direction, Entrance> getEntrances() {
        return entrances;
    }

    public Multiset<Item> getItems() {
        return items;
    }

    public int getZombies() {
        return zombies;
    }

    public boolean hasZombies() {
        return zombies > 0;
    }

    public void setZombies(int zombies) {
        this.zombies = zombies;
    }

    public void decrementZombies() {
        zombies--;
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
