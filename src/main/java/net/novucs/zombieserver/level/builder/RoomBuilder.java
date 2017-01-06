package net.novucs.zombieserver.level.builder;

import net.novucs.zombieserver.level.Entrance;
import net.novucs.zombieserver.level.Item;
import net.novucs.zombieserver.level.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomBuilder {

    private String name;
    private String description;
    private List<Entrance> entrances = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private int zombies;

    public RoomBuilder name(String name) {
        this.name = name;
        return this;
    }

    public RoomBuilder description(String description) {
        this.description = description;
        return this;
    }

    public RoomBuilder entrances(List<Entrance> entrances) {
        this.entrances = entrances;
        return this;
    }

    public RoomBuilder addEntrance(Entrance entrance) {
        entrances.add(entrance);
        return this;
    }

    public RoomBuilder items(List<Item> items) {
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

        return new Room(name, description, new ArrayList<>(entrances), new ArrayList<>(items), zombies);
    }
}
