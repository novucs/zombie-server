package net.novucs.zombieserver.level.builder;

import com.google.common.collect.ImmutableList;
import net.novucs.zombieserver.level.ItemType;
import net.novucs.zombieserver.level.Room;
import net.novucs.zombieserver.level.World;

import java.util.ArrayList;
import java.util.List;

public class WorldBuilder {

    private String info;
    private String startHtml;
    private String inventoryHtml;
    private List<ItemType> items = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private Room start;
    private Room finish;

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

    public WorldBuilder items(List<ItemType> items) {
        this.items = items;
        return this;
    }

    public WorldBuilder addItem(ItemType item) {
        items.add(item);
        return this;
    }

    public WorldBuilder rooms(List<Room> rooms) {
        this.rooms = rooms;
        return this;
    }

    public WorldBuilder addRoom(Room room) {
        rooms.add(room);
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

        return new World(info, startHtml, inventoryHtml, ImmutableList.copyOf(items), ImmutableList.copyOf(rooms),
                start, finish);
    }
}
