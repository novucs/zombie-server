package net.novucs.zombieserver.level.generator;

import com.google.common.collect.HashMultiset;
import net.novucs.zombieserver.level.*;
import net.novucs.zombieserver.level.builder.EntranceBuilder;
import net.novucs.zombieserver.level.builder.RoomBuilder;
import net.novucs.zombieserver.level.builder.WorldBuilder;

/**
 * Randomly generates a new world. (Original intention)
 *
 * Currently this simply returns the default world provided by Ben.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class WorldGenerator {

    public World generate() {
        // TODO: Randomly generate the world.
        WorldBuilder worldBuilder = new WorldBuilder();
        worldBuilder.info("<p class=\\\"lead\\\">Welcome to OOSD1 Zombies</p>" +
                "<p>Zombies are real, no matter what your mum told you! UWE's campus has been invaded and you must kill " +
                "the zombies and get to the exit room, to save yourself and the University. Good luck!</p>");
        worldBuilder.startHtml("let's play");
        worldBuilder.inventoryHtml("<img src=\\\"../assets/backpack.png\\\">");

        worldBuilder.addItem(Item.GOLD);
        worldBuilder.addItem(Item.KEY);
        worldBuilder.addItem(Item.CHAINSAW);
        worldBuilder.addItem(Item.DAISY);

        // TOILET
        RoomBuilder roomBuilder = new RoomBuilder();
        roomBuilder.name("Toilet");
        roomBuilder.description("You are in the toilet");
        roomBuilder.items(HashMultiset.create());
        roomBuilder.addItem(Item.GOLD);
        roomBuilder.addItem(Item.CHAINSAW);
        roomBuilder.addItem(Item.DAISY);
        roomBuilder.zombies(0);

        EntranceBuilder entranceBuilder = new EntranceBuilder();
        entranceBuilder.direction(Direction.NORTH_EAST);
        entranceBuilder.to("Exit");
        entranceBuilder.locked(true);
        roomBuilder.addEntrance(entranceBuilder.build());

        entranceBuilder.direction(Direction.SOUTH_EAST);
        entranceBuilder.to("R3");
        entranceBuilder.locked(false);
        roomBuilder.addEntrance(entranceBuilder.build());

        entranceBuilder.direction(Direction.SOUTH_WEST);
        entranceBuilder.to("R4");
        roomBuilder.addEntrance(entranceBuilder.build());

        Room toilet = roomBuilder.build();
        worldBuilder.addRoom(toilet);

        // EXIT
        roomBuilder.name("Exit");
        roomBuilder.description("This is the final room...");
        roomBuilder.items(HashMultiset.create());
        roomBuilder.addItem(Item.GOLD);
        roomBuilder.zombies(1);

        Room exit = roomBuilder.build();
        worldBuilder.addRoom(exit);

        // R3
        roomBuilder.name("R3");
        roomBuilder.description("Level R3");
        roomBuilder.items(HashMultiset.create());
        roomBuilder.addItem(Item.KEY);
        roomBuilder.addItem(Item.CHAINSAW);
        roomBuilder.zombies(2);

        entranceBuilder.direction(Direction.NORTH_WEST);
        entranceBuilder.to("Toilet");
        roomBuilder.addEntrance(entranceBuilder.build());

        Room r3 = roomBuilder.build();
        worldBuilder.addRoom(r3);

        // R4
        roomBuilder.name("R4");
        roomBuilder.description("Level R4");
        roomBuilder.items(HashMultiset.create());
        roomBuilder.addItem(Item.GOLD);
        roomBuilder.addItem(Item.CHAINSAW);
        roomBuilder.zombies(0);

        entranceBuilder.direction(Direction.NORTH_EAST);
        roomBuilder.addEntrance(entranceBuilder.build());

        Room r4 = roomBuilder.build();
        worldBuilder.addRoom(r4);

        worldBuilder.start(toilet);
        worldBuilder.finish(exit);

        return worldBuilder.build();
    }
}
