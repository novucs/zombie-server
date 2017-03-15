package net.novucs.zombieserver.level.builder;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.novucs.zombieserver.level.Item;
import net.novucs.zombieserver.level.Room;
import net.novucs.zombieserver.level.World;

import java.util.*;

/**
 * A {@link World} builder.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class WorldBuilder {

    private String info;
    private String startHtml;
    private String inventoryHtml;
    private Set<Item> items = new HashSet<>();
    private Map<String, Room> rooms = new HashMap<>();
    private Room start;
    private Room finish;

    /**
     * Gets the world information message.
     *
     * @return the information message.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Gets the start html.
     *
     * @return the start html.
     */
    public String getStartHtml() {
        return startHtml;
    }

    /**
     * Gets the inventory html.
     *
     * @return the inventory html.
     */
    public String getInventoryHtml() {
        return inventoryHtml;
    }

    /**
     * Gets all available items in this world.
     *
     * @return the items.
     */
    public Set<Item> getItems() {
        return items;
    }

    /**
     * Gets all rooms in the world.
     *
     * @return the rooms.
     */
    public Map<String, Room> getRooms() {
        return rooms;
    }

    /**
     * Gets the start room.
     *
     * @return the start.
     */
    public Room getStart() {
        return start;
    }

    /**
     * Gets the finish room.
     *
     * @return the finish.
     */
    public Room getFinish() {
        return finish;
    }

    /**
     * Sets the world info message.
     *
     * @param info the info message.
     * @return {@code this}.
     */
    public WorldBuilder info(String info) {
        this.info = info;
        return this;
    }

    /**
     * Sets the world start html.
     *
     * @param startHtml the start html.
     * @return {@code this}.
     */
    public WorldBuilder startHtml(String startHtml) {
        this.startHtml = startHtml;
        return this;
    }

    /**
     * Sets the world inventory html.
     *
     * @param inventoryHtml the inventory html.
     * @return {@code this}.
     */
    public WorldBuilder inventoryHtml(String inventoryHtml) {
        this.inventoryHtml = inventoryHtml;
        return this;
    }

    /**
     * Sets the items available in this world.
     *
     * @param items the items.
     * @return {@code this}.
     */
    public WorldBuilder items(Set<Item> items) {
        this.items = items;
        return this;
    }

    /**
     * Adds an item type to this world.
     *
     * @param item the item.
     * @return {@code this}.
     */
    public WorldBuilder addItem(Item item) {
        items.add(item);
        return this;
    }

    /**
     * Gets all rooms from this world.
     *
     * @param rooms the rooms.
     * @return {@code this}.
     */
    public WorldBuilder rooms(Map<String, Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    /**
     * Adds a new room to this world.
     *
     * @param room the new room.
     * @return {@code this}.
     */
    public WorldBuilder addRoom(Room room) {
        rooms.put(room.getName(), room);
        return this;
    }

    /**
     * Sets the start room.
     *
     * @param start the start.
     * @return {@code this}.
     */
    public WorldBuilder start(Room start) {
        this.start = start;
        return this;
    }

    /**
     * Sets the finish room.
     *
     * @param finish the finish.
     * @return {@code this}.
     */
    public WorldBuilder finish(Room finish) {
        this.finish = finish;
        return this;
    }

    /**
     * Builds a new world.
     *
     * @return the new world.
     */
    public World build() {
        if (info == null || startHtml == null || inventoryHtml == null || items == null || rooms == null ||
                start == null || finish == null) {
            throw new IllegalStateException("World has not been fully built");
        }

        ImmutableSet<Item> items = ImmutableSet.copyOf(this.items);
        ImmutableMap<String, Room> rooms = ImmutableMap.copyOf(this.rooms);

        return new World(info, startHtml, inventoryHtml, items, rooms, start, finish);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldBuilder builder = (WorldBuilder) o;
        return Objects.equals(info, builder.info) &&
                Objects.equals(startHtml, builder.startHtml) &&
                Objects.equals(inventoryHtml, builder.inventoryHtml) &&
                Objects.equals(items, builder.items) &&
                Objects.equals(rooms, builder.rooms) &&
                Objects.equals(start, builder.start) &&
                Objects.equals(finish, builder.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, startHtml, inventoryHtml, items, rooms, start, finish);
    }

    @Override
    public String toString() {
        return "WorldBuilder{" +
                "info='" + info + '\'' +
                ", startHtml='" + startHtml + '\'' +
                ", inventoryHtml='" + inventoryHtml + '\'' +
                ", items=" + items +
                ", rooms=" + rooms +
                ", start=" + start +
                ", finish=" + finish +
                '}';
    }
}
