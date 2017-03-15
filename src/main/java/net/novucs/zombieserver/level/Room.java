package net.novucs.zombieserver.level;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;

import java.util.Map;
import java.util.Objects;

/**
 * Represents an in game room.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class Room {

    private final String name;
    private final String description;
    private final ImmutableMap<Direction, Entrance> entrances;
    private final Multiset<Item> items;
    private int zombies;

    /**
     * Constructs a new room.
     *
     * @param name        the room name.
     * @param description the room description.
     * @param entrances   all entrances to other rooms.
     * @param items       the items in this room.
     * @param zombies     the number of zombies in this room.
     */
    public Room(String name, String description, ImmutableMap<Direction, Entrance> entrances, Multiset<Item> items, int zombies) {
        this.name = name;
        this.description = description;
        this.entrances = entrances;
        this.items = items;
        this.zombies = zombies;
    }

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
     * Gets the items in this room.
     *
     * @return the items.
     */
    public Multiset<Item> getItems() {
        return items;
    }

    /**
     * Gets the zombie count for this room.
     *
     * @return the number of zombies.
     */
    public int getZombies() {
        return zombies;
    }

    /**
     * Checks if this room contains any zombies.
     *
     * @return {@code true} if this room contains zombies, otherwise {@code false}.
     */
    public boolean hasZombies() {
        return zombies > 0;
    }

    /**
     * Sets the number of zombies in this room.
     *
     * @param zombies the zombie count.
     */
    public void setZombies(int zombies) {
        this.zombies = zombies;
    }

    /**
     * Decrements the number of zombies in this room.
     */
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
