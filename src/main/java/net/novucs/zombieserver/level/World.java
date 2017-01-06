package net.novucs.zombieserver.level;

import com.google.common.collect.ImmutableList;

import java.util.Objects;

public class World {

    private final String info;
    private final String startHtml;
    private final String inventoryHtml;
    private final ImmutableList<ItemType> items;
    private final ImmutableList<Room> rooms;
    private final String start;
    private final String finish;

    public World(String info, String startHtml, String inventoryHtml, ImmutableList<ItemType> items,
                 ImmutableList<Room> rooms, String start, String finish) {
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

    public ImmutableList<ItemType> getItems() {
        return items;
    }

    public ImmutableList<Room> getRooms() {
        return rooms;
    }

    public String getStart() {
        return start;
    }

    public String getFinish() {
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