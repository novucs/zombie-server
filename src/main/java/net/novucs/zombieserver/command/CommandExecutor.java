package net.novucs.zombieserver.command;

import com.google.common.collect.Multiset;
import com.sk89q.intake.Command;
import net.novucs.zombieserver.GameManager;
import net.novucs.zombieserver.GameState;
import net.novucs.zombieserver.level.*;

import java.util.Collection;
import java.util.StringJoiner;

/**
 * Handles all commands that the player may execute.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class CommandExecutor {

    private final GameManager game;

    /**
     * Constructs a new {@link CommandExecutor}.
     *
     * @param game the game manager.
     */
    public CommandExecutor(GameManager game) {
        this.game = game;
    }

    @Command(aliases = "begin", desc = "Updates the game state and displays the begin message")
    public void begin(CommandResult result) {
        if (game.getState() != GameState.START) {
            result.add("<b>That's not a verb I recognise.</b>");
            return;
        }

        result.add(game.getWorld().getInfo());
        game.setState(GameState.RUNNING);
    }

    @Command(aliases = "info", desc = "Displays the world information")
    public void info(CommandResult result) {
        result.add(game.getWorld().getInfo());
    }

    @Command(aliases = "look", desc = "Displays all contents in the current room")
    public void look(CommandResult result) {
        Room room = game.getCurrentRoom();
        result.add("<b>" + room.getDescription() + "</b>");

        switch (room.getEntrances().size()) {
            case 0:
                break;
            case 1:
                String direction = room.getEntrances().values().iterator().next().getDirection().getShorthand();
                result.add("There is an entrance to the " + direction);
                break;
            default:
                result.add(getEntranceInfo(room.getEntrances().values()));
        }

        result.add(getItemInfo(room.getItems()));
    }

    /**
     * Gets the concatenation of all item html messages.
     *
     * @param items the items to parse.
     * @return the info message of all provided items.
     */
    private String getItemInfo(Multiset<Item> items) {
        StringBuilder target = new StringBuilder();

        for (Item item : items) {
            target.append(item.getHtml());
        }

        return target.toString();
    }

    /**
     * Gets the concatenated information of all entrances.
     *
     * @param entrances the entrances to get information of.
     * @return the information message of all provided entrances.
     */
    private String getEntranceInfo(Collection<Entrance> entrances) {
        StringJoiner target = new StringJoiner(", ");

        for (Entrance entrance : entrances) {
            target.add(entrance.getDirection().getShorthand());
        }

        return "There are entrances to the " + target.toString();
    }

    @Command(aliases = "move", desc = "Moves player into a new room")
    public void move(CommandResult result, Direction direction) {
        Entrance entrance = game.getCurrentRoom().getEntrances().get(direction);

        if (entrance == null) {
            result.add("No entrance exists");
            return;
        }

        if (entrance.isLocked()) {
            if (!game.getInventory().remove(Item.KEY)) {
                result.add("A key is needed to open entrance in " + direction.getShorthand());
                return;
            }

            entrance.setLocked(false);
        }

        Room room = game.getWorld().getRooms().get(entrance.getTo());
        game.setCurrentRoom(room);
        result.add("You are now in " + room.getName());

        if (room.getZombies() > 0) {
            game.setZombieTimerState(ZombieTimerState.START);
        }
    }

    @Command(aliases = "pickup", desc = "Picks up an item in the current room")
    public void pickup(CommandResult result, Item item) {
        boolean removed = game.getCurrentRoom().getItems().remove(item);

        if (!removed) {
            result.add("<b>This room does not contain a " + item.getName() + "</b>");
            return;
        }

        result.add("Picked up " + item.getName());
        game.getInventory().add(item);

        if (item == Item.GOLD) {
            game.incrementScore();
        }
    }

    @Command(aliases = "kill", desc = "Kills a zombie if present")
    public void kill(CommandResult result) {
        Room room = game.getCurrentRoom();

        if (!room.hasZombies()) {
            result.add("No zombies found in this room");
            return;
        }

        if (!game.getInventory().remove(Item.CHAINSAW) &&
                !game.getInventory().remove(Item.DAISY)) {
            result.add("Unable to kill zombie, no weapons available");
            return;
        }

        room.decrementZombies();

        if (!room.hasZombies()) {
            game.setZombieTimerState(ZombieTimerState.STOP);
        }

        result.add("Zombie killed");
    }

    @Command(aliases = "drop", desc = "Drops an item in the current room")
    public void drop(CommandResult result, Item item) {
        boolean removed = game.getInventory().remove(item);

        if (!removed) {
            result.add("No such item");
            return;
        }

        result.add("Item dropped");
        game.getCurrentRoom().getItems().add(item);

        if (item == Item.GOLD) {
            game.decrementScore();
        }
    }

    @Command(aliases = "timerexpired", desc = "Sent when player is dead, ends the game")
    public void timerexpired(CommandResult result) {
        result.add("You have died");
        game.setState(GameState.FINISHED);
    }

    @Command(aliases = "quit", desc = "Quits the game")
    public void quit(CommandResult result) {
        if (game.getState() != GameState.RUNNING) {
            result.add("<b>That's not a verb I recognise.</b>");
            return;
        }

        result.add("<b>bye bye</b>");
        game.setState(GameState.FINISHED);
    }

    @Command(aliases = "inventory", desc = "Displays player their inventory")
    public void inventory(CommandResult result) {
        StringBuilder target = new StringBuilder();
        target.append(game.getWorld().getInventoryHtml());

        for (Item item : game.getInventory()) {
            target.append(item.getHtml());
        }

        result.add(target.toString());
    }

    @Command(aliases = "blank", desc = "Returns a fancy message")
    public void blank(CommandResult result) {
        result.add("I beg your pardon?");
    }
}
