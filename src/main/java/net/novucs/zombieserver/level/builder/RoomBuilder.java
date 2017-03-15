package net.novucs.zombieserver.level.builder;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import net.novucs.zombieserver.level.Direction;
import net.novucs.zombieserver.level.Entrance;
import net.novucs.zombieserver.level.Item;
import net.novucs.zombieserver.level.Room;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * A {@link Room} builder.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class RoomBuilder {

    private String name;
    private String description;
    private Map<Direction, Entrance> entrances = new EnumMap<>(Direction.class);
    private Multiset<Item> items = HashMultiset.create();
    private int zombies;

    /**
     * Gets the room name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the room description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the room entrances.
     *
     * @return the entrances.
     */
    public Map<Direction, Entrance> getEntrances() {
        return entrances;
    }

    /**
     * Gets the room items.
     *
     * @return the items.
     */
    public Multiset<Item> getItems() {
        return items;
    }

    /**
     * Gets the room zombie count.
     *
     * @return the zombie count.
     */
    public int getZombies() {
        return zombies;
    }

    /**
     * Sets the room name.
     *
     * @param name the name.
     * @return {@code this}.
     */
    public RoomBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the room description.
     *
     * @param description the description.
     * @return {@code this}.
     */
    public RoomBuilder description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the room entrances.
     *
     * @param entrances the entrances.
     * @return {@code this}.
     */
    public RoomBuilder entrances(Map<Direction, Entrance> entrances) {
        this.entrances = entrances;
        return this;
    }

    /**
     * Adds a new entrance to the room.
     *
     * @param entrance the entrance to add.
     * @return {@code this}.
     */
    public RoomBuilder addEntrance(Entrance entrance) {
        entrances.put(entrance.getDirection(), entrance);
        return this;
    }

    /**
     * Sets the room items.
     *
     * @param items the items.
     * @return {@code this}.
     */
    public RoomBuilder items(Multiset<Item> items) {
        this.items = items;
        return this;
    }

    /**
     * Adds a new item to the room.
     *
     * @param item the new item.
     * @return {@code this}.
     */
    public RoomBuilder addItem(Item item) {
        items.add(item);
        return this;
    }

    /**
     * Sets the number of zombies in this room.
     *
     * @param zombies the zombie count.
     * @return {@code this}.
     */
    public RoomBuilder zombies(int zombies) {
        this.zombies = zombies;
        return this;
    }

    /**
     * Builds a new room.
     *
     * @return the room built.
     */
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
