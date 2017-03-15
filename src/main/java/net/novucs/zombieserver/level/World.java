package net.novucs.zombieserver.level;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Objects;

/**
 * Represents the in game world.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class World {

    private final String info;
    private final String startHtml;
    private final String inventoryHtml;
    private final ImmutableSet<Item> items;
    private final ImmutableMap<String, Room> rooms;
    private final Room start;
    private final Room finish;

    /**
     * Constructs a new world.
     *
     * @param info          the message to be sent on the info command.
     * @param startHtml     the message to be sent when the player first logs in.
     * @param inventoryHtml the inventory icon.
     * @param items         all item types this world contains.
     * @param rooms         all world rooms.
     * @param start         the room the player should start in.
     * @param finish        the room the player should finish in.
     */
    public World(String info, String startHtml, String inventoryHtml, ImmutableSet<Item> items,
                 ImmutableMap<String, Room> rooms, Room start, Room finish) {
        this.info = info;
        this.startHtml = startHtml;
        this.inventoryHtml = inventoryHtml;
        this.items = items;
        this.rooms = rooms;
        this.start = start;
        this.finish = finish;
    }

    /**
     * Gets the world information message.
     *
     * @return the information message.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Gets the start message.
     *
     * @return the start message.
     */
    public String getStartHtml() {
        return startHtml;
    }

    /**
     * Gets the inventory icon.
     *
     * @return the inventory icon.
     */
    public String getInventoryHtml() {
        return inventoryHtml;
    }

    /**
     * Gets all item types in this world.
     *
     * @return the available items.
     */
    public ImmutableSet<Item> getItems() {
        return items;
    }

    /**
     * Gets all worlds in this world.
     *
     * @return the rooms.
     */
    public ImmutableMap<String, Room> getRooms() {
        return rooms;
    }

    /**
     * Gets the room the player should start in.
     *
     * @return the start room.
     */
    public Room getStart() {
        return start;
    }

    /**
     * Gets the room the player should finish in.
     *
     * @return the finish room.
     */
    public Room getFinish() {
        return finish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        World world = (World) o;
        return Objects.equals(info, world.info) &&
                Objects.equals(startHtml, world.startHtml) &&
                Objects.equals(inventoryHtml, world.inventoryHtml) &&
                Objects.equals(items, world.items) &&
                Objects.equals(rooms, world.rooms) &&
                Objects.equals(start, world.start) &&
                Objects.equals(finish, world.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, startHtml, inventoryHtml, items, rooms, start, finish);
    }

    @Override
    public String toString() {
        return "World{" +
                "info='" + info + '\'' +
                ", startHtml='" + startHtml + '\'' +
                ", inventoryHtml='" + inventoryHtml + '\'' +
                ", items=" + items +
                ", rooms=" + rooms +
                ", start='" + start + '\'' +
                ", finish='" + finish + '\'' +
                '}';
    }
}
