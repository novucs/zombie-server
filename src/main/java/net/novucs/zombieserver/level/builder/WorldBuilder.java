package net.novucs.zombieserver.level.builder;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.novucs.zombieserver.level.Item;
import net.novucs.zombieserver.level.Room;
import net.novucs.zombieserver.level.World;

import java.util.*;

public class WorldBuilder {

    private String info;
    private String startHtml;
    private String inventoryHtml;
    private Set<Item> items = new HashSet<>();
    private Map<String, Room> rooms = new HashMap<>();
    private Room start;
    private Room finish;

    public String getInfo() {
        return info;
    }

    public String getStartHtml() {
        return startHtml;
    }

    public String getInventoryHtml() {
        return inventoryHtml;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public Room getStart() {
        return start;
    }

    public Room getFinish() {
        return finish;
    }

    public WorldBuilder info(String info) {
        this.info = info;
        return this;
    }

    public WorldBuilder startHtml(String startHtml) {
        this.startHtml = startHtml;
        return this;
    }

    public WorldBuilder inventoryHtml(String inventoryHtml) {
        this.inventoryHtml = inventoryHtml;
        return this;
    }

    public WorldBuilder items(Set<Item> items) {
        this.items = items;
        return this;
    }

    public WorldBuilder addItem(Item item) {
        items.add(item);
        return this;
    }

    public WorldBuilder rooms(Map<String, Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public WorldBuilder addRoom(Room room) {
        rooms.put(room.getName(), room);
        return this;
    }

    public WorldBuilder start(Room start) {
        this.start = start;
        return this;
    }

    public WorldBuilder finish(Room finish) {
        this.finish = finish;
        return this;
    }

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
