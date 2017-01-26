package net.novucs.zombieserver.command;

import com.google.common.collect.Multiset;
import com.sk89q.intake.Command;
import net.novucs.zombieserver.GameManager;
import net.novucs.zombieserver.GameState;
import net.novucs.zombieserver.level.*;

import java.util.Collection;
import java.util.StringJoiner;

public class CommandExecutor {

    private final GameManager game;

    public CommandExecutor(GameManager game) {
        this.game = game;
    }

    @Command(aliases = "begin", desc = "")
    public void begin(CommandResult result) {
        if (game.getState() != GameState.START) {
            result.add("<b>That's not a verb I recognise.</b>");
            return;
        }

        result.add(game.getWorld().getInfo());
        game.setState(GameState.RUNNING);
    }

    @Command(aliases = "info", desc = "")
    public void info(CommandResult result) {
        result.add(game.getWorld().getInfo());
    }

    @Command(aliases = "look", desc = "")
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

    private String getItemInfo(Multiset<Item> items) {
        StringBuilder target = new StringBuilder();

        for (Item item : items) {
            target.append(item.getHtml());
        }

        return target.toString();
    }

    private String getEntranceInfo(Collection<Entrance> entrances) {
        StringJoiner target = new StringJoiner(", ");

        for (Entrance entrance : entrances) {
            target.add(entrance.getDirection().getShorthand());
        }

        return "There are entrances to the " + target.toString();
    }

    @Command(aliases = "move", desc = "")
    public void move(CommandResult result, Direction direction) {
        Entrance entrance = game.getCurrentRoom().getEntrances().get(direction);

        if (entrance == null) {
            result.add("No entrance exists");
            return;
        }

        if (entrance.isLocked() && !game.getInventory().remove(Item.KEY)) {
            result.add("A key is needed to open entrance in " + direction.getShorthand());
            return;
        }

        Room room = game.getWorld().getRooms().get(entrance.getTo());
        game.setCurrentRoom(room);
        result.add("You are now in " + room.getName());

        if (room.getZombies() > 0) {
            game.setZombieTimerState(ZombieTimerState.START);
        }
    }

    @Command(aliases = "pickup", desc = "")
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

    @Command(aliases = "kill", desc = "")
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

    @Command(aliases = "drop", desc = "")
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

    @Command(aliases = "timerexpired", desc = "")
    public void timerexpired(CommandResult result) {
        result.add("You have died");
        game.setState(GameState.FINISHED);
    }

    @Command(aliases = "quit", desc = "")
    public void quit(CommandResult result) {
        if (game.getState() != GameState.RUNNING) {
            result.add("<b>That's not a verb I recognise.</b>");
            return;
        }

        result.add("<b>bye bye</b>");
        game.setState(GameState.FINISHED);
    }

    @Command(aliases = "inventory", desc = "")
    public void inventory(CommandResult result) {
        StringBuilder target = new StringBuilder();
        target.append(game.getWorld().getInventoryHtml());

        for (Item item : game.getInventory()) {
            target.append(item.getHtml());
        }

        result.add(target.toString());
    }

    @Command(aliases = "blank", desc = "")
    public void blank(CommandResult result) {
        result.add("I beg your pardon?");
    }
}
