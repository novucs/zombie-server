package net.novucs.zombieserver.command;

import com.google.common.collect.Multiset;
import com.sk89q.intake.Command;
import net.novucs.zombieserver.GameManager;
import net.novucs.zombieserver.GameState;
import net.novucs.zombieserver.level.*;

import java.util.List;
import java.util.Optional;

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
                String direction = room.getEntrances().get(0).getDirection().getShorthand();
                result.add("There is an entrance to the " + direction);
                break;
            default:
                result.add(getEntranceInfo(room.getEntrances()));
        }

        result.add(getItemInfo(room.getItems()));
    }

    private String getItemInfo(Multiset<Item> items) {
        StringBuilder target = new StringBuilder();

        for (Item item : items) {
            ItemType.get(item.getItem()).ifPresent(itemType ->
                    target.append(itemType.getHtml()));
        }

        return target.toString();
    }

    private String getEntranceInfo(List<Entrance> entrances) {
        StringBuilder target = new StringBuilder("There are entrances to the ");

        for (int i = 0; i < entrances.size() - 1; i++) {
            target.append(entrances.get(i).getDirection().getShorthand());
            target.append(", ");
        }

        target.append(entrances.get(entrances.size() - 1).getDirection().getShorthand());
        return target.toString();
    }

    @Command(aliases = "move", desc = "")
    public void move(CommandResult result, Direction direction) {
        Optional<Entrance> entrance = getEntrance(direction, game.getCurrentRoom().getEntrances());

        if (!entrance.isPresent()) {
            result.add("No entrance exists");
            return;
        }

        if (entrance.get().isLocked() && !game.getInventory().remove(Item.of(ItemType.KEY))) {
            result.add("A key is needed to open entrance in " + direction.getShorthand());
            return;
        }

        Room room = entrance.get().getTo();
        game.setCurrentRoom(room);
        result.add("You are now in " + room.getName());

        if (room.getZombies() > 0) {
            game.setZombieTimerState(ZombieTimerState.START);
        }
    }

    private Optional<Entrance> getEntrance(Direction direction, List<Entrance> entrances) {
        for (Entrance entrance : entrances) {
            if (entrance.getDirection() == direction) {
                return Optional.of(entrance);
            }
        }

        return Optional.empty();
    }

    @Command(aliases = "pickup", desc = "")
    public void pickup(CommandResult result, Item item) {
        boolean removed = game.getCurrentRoom().getItems().remove(item);

        if (!removed) {
            result.add("<b>This room does not contain a " + item.getItem() + "</b>");
            return;
        }

        result.add("Picked up " + item.getItem());
        game.getInventory().add(item);

        ItemType.get(item.getItem()).ifPresent(itemType -> {
            if (itemType == ItemType.GOLD) {
                game.incrementScore();
            }
        });
    }

    @Command(aliases = "kill", desc = "")
    public void kill(CommandResult result) {
        Room room = game.getCurrentRoom();

        if (!room.hasZombies()) {
            result.add("No zombies found in this room");
            return;
        }

        if (!game.getInventory().remove(Item.of(ItemType.CHAINSAW)) &&
                !game.getInventory().remove(Item.of(ItemType.DAISY))) {
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

        ItemType.get(item.getItem()).ifPresent(itemType -> {
            if (itemType == ItemType.GOLD) {
                game.decrementScore();
            }
        });
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
            ItemType.get(item.getItem()).ifPresent(itemType ->
                    target.append(itemType.getHtml()));
        }

        result.add(target.toString());
    }

    @Command(aliases = "blank", desc = "")
    public void blank(CommandResult result) {
        result.add("I beg your pardon?");
    }
}
