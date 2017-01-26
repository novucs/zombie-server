package net.novucs.zombieserver.level;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Objects;

public class World {

    private final String info;
    private final String startHtml;
    private final String inventoryHtml;
    private final ImmutableSet<Item> items;
    private final ImmutableMap<String, Room> rooms;
    private final Room start;
    private final Room finish;

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

    public String getInfo() {
        return info;
    }

    public String getStartHtml() {
        return startHtml;
    }

    public String getInventoryHtml() {
        return inventoryHtml;
    }

    public ImmutableSet<Item> getItems() {
        return items;
    }

    public ImmutableMap<String, Room> getRooms() {
        return rooms;
    }

    public Room getStart() {
        return start;
    }

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
