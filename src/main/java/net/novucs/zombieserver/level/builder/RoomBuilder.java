package net.novucs.zombieserver.level.builder;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import net.novucs.zombieserver.level.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class RoomBuilder {

    private String name;
    private String description;
    private Map<Direction, Entrance> entrances = new EnumMap<>(Direction.class);
    private Multiset<Item> items = HashMultiset.create();
    private int zombies;

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

    public RoomBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RoomBuilder description(String description) {
        this.description = description;
        return this;
    }

    public RoomBuilder entrances(Map<Direction, Entrance> entrances) {
        this.entrances = entrances;
        return this;
    }

    public RoomBuilder addEntrance(Entrance entrance) {
        entrances.put(entrance.getDirection(), entrance);
        return this;
    }

    public RoomBuilder items(Multiset<Item> items) {
        this.items = items;
        return this;
    }

    public RoomBuilder addItem(Item item) {
        items.add(item);
        return this;
    }

    public RoomBuilder zombies(int zombies) {
        this.zombies = zombies;
        return this;
    }

    public Room build() {
        if (name == null || description == null || entrances == null || items == null || zombies < 0) {
            throw new IllegalStateException("Room has not been fully built");
        }

        ImmutableMap<Direction, Entrance> entrances = ImmutableMap.copyOf(this.entrances);
        Multiset<Item> items = HashMultiset.create(this.items);

        return new Room(name, description, entrances, items, zombies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomBuilder builder = (RoomBuilder) o;
        return zombies == builder.zombies &&
                Objects.equals(name, builder.name) &&
                Objects.equals(description, builder.description) &&
                Objects.equals(entrances, builder.entrances) &&
                Objects.equals(items, builder.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, entrances, items, zombies);
    }

    @Override
    public String toString() {
        return "RoomBuilder{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", entrances=" + entrances +
                ", items=" + items +
                ", zombies=" + zombies +
                '}';
    }
}
