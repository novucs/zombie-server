package net.novucs.zombieserver.level.generator;

import com.google.common.collect.HashMultiset;
import net.novucs.zombieserver.level.*;
import net.novucs.zombieserver.level.builder.EntranceBuilder;
import net.novucs.zombieserver.level.builder.RoomBuilder;
import net.novucs.zombieserver.level.builder.WorldBuilder;

public class WorldGenerator {

    public World generate() {
        // TODO: Randomly generate the world.
        WorldBuilder worldBuilder = new WorldBuilder();
        worldBuilder.info("<p class=\\\"lead\\\">Welcome to OOSD1 Zombies</p>" +
                "<p>Zombies are real, no matter what your mum told you! UWE's campus has been invaded and you must kill " +
                "the zombies and get to the exit room, to save yourself and the University. Good luck!</p>");
        worldBuilder.startHtml("let's play");
        worldBuilder.inventoryHtml("<img src=\\\"../assets/backpack.png\\\">");

        worldBuilder.addItem(ItemType.GOLD);
        worldBuilder.addItem(ItemType.KEY);
        worldBuilder.addItem(ItemType.CHAINSAW);
        worldBuilder.addItem(ItemType.DAISY);

        RoomBuilder roomBuilder = new RoomBuilder();
        roomBuilder.name("Toilet");
        roomBuilder.description("You are in the toilet");
        roomBuilder.items(HashMultiset.create());
        roomBuilder.addItem(Item.of(ItemType.GOLD));
        roomBuilder.addItem(Item.of(ItemType.CHAINSAW));
        roomBuilder.addItem(Item.of(ItemType.DAISY));
        roomBuilder.zombies(0);
        Room toilet = roomBuilder.build();

        roomBuilder.name("Exit");
        roomBuilder.description("This is the final room...");
        roomBuilder.items(HashMultiset.create());
        roomBuilder.addItem(Item.of(ItemType.GOLD));
        roomBuilder.zombies(1);
        Room exit = roomBuilder.build();

        roomBuilder.name("R3");
        roomBuilder.description("Level R3");
        roomBuilder.items(HashMultiset.create());
        roomBuilder.addItem(Item.of(ItemType.KEY));
        roomBuilder.addItem(Item.of(ItemType.CHAINSAW));
        roomBuilder.zombies(2);
        Room r3 = roomBuilder.build();

        roomBuilder.name("R4");
        roomBuilder.description("Level R4");
        roomBuilder.items(HashMultiset.create());
        roomBuilder.addItem(Item.of(ItemType.GOLD));
        roomBuilder.addItem(Item.of(ItemType.CHAINSAW));
        roomBuilder.zombies(0);
        Room r4 = roomBuilder.build();

        EntranceBuilder entranceBuilder = new EntranceBuilder();
        entranceBuilder.direction(Direction.NORTH_EAST);
        entranceBuilder.to(exit);
        entranceBuilder.locked(true);
        toilet.getEntrances().add(entranceBuilder.build());

        entranceBuilder.direction(Direction.SOUTH_EAST);
        entranceBuilder.to(r3);
        entranceBuilder.locked(false);
        toilet.getEntrances().add(entranceBuilder.build());

        entranceBuilder.direction(Direction.SOUTH_WEST);
        entranceBuilder.to(r4);
        toilet.getEntrances().add(entranceBuilder.build());

        entranceBuilder.direction(Direction.NORTH_WEST);
        entranceBuilder.to(toilet);
        r3.getEntrances().add(entranceBuilder.build());

        entranceBuilder.direction(Direction.NORTH_EAST);
        r4.getEntrances().add(entranceBuilder.build());

        worldBuilder.start(toilet);
        worldBuilder.finish(exit);
        return worldBuilder.build();
    }
}
