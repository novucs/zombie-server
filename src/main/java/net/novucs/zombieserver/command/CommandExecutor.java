package net.novucs.zombieserver.command;

import com.sk89q.intake.Command;
import net.novucs.zombieserver.GameManager;
import net.novucs.zombieserver.GameState;
import net.novucs.zombieserver.level.Entrance;
import net.novucs.zombieserver.level.Item;
import net.novucs.zombieserver.level.ItemType;
import net.novucs.zombieserver.level.Room;

import java.util.List;

public class CommandExecutor {

    private final GameManager game;

    public CommandExecutor(GameManager game) {
        this.game = game;
    }

    @Command(aliases = "begin", desc = "")
    public void begin(CommandResult result) {
        if (game.getState() != GameState.START) {
            result.get().add("<b>That's not a verb I recognise.</b>");
            return;
        }

        result.get().add(game.getWorld().getInfo());
        game.setState(GameState.RUNNING);
    }

    @Command(aliases = "info", desc = "")
    public void info(CommandResult result) {
        result.get().add(game.getWorld().getInfo());
    }

    @Command(aliases = "look", desc = "")
    public void look(CommandResult result) {
        Room room = game.getCurrentRoom();
        result.get().add("<b>" + room.getDescription() + "</b>");

        switch (room.getEntrances().size()) {
            case 0:
                break;
            case 1:
                String direction = room.getEntrances().get(0).getDirection().getShorthand();
                result.get().add("There is an entrance to the " + direction);
                break;
            default:
                result.get().add(getEntranceInfo(room.getEntrances()));
        }

        result.get().add(getItemInfo(room.getItems()));
    }

    private String getItemInfo(List<Item> items) {
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
    public void move(CommandResult result) {
        result.get().add("handle move command");
    }

    @Command(aliases = "pickup", desc = "")
    public void pickup(CommandResult result, Item item) {
        boolean removed = game.getCurrentRoom().getItems().remove(item);

        if (!removed) {
            result.get().add("<b>This room does not contain a " + item.getItem() + "</b>");
            return;
        }

        result.get().add("Picked up " + item.getItem());
        game.getInventory().add(item);
    }

    @Command(aliases = "kill", desc = "")
    public void kill(CommandResult result) {
        result.get().add("handle kill command");
    }

    @Command(aliases = "drop", desc = "")
    public void drop(CommandResult result) {
        result.get().add("handle drop command");
    }

    @Command(aliases = "timerexpired", desc = "")
    public void timerexpired(CommandResult result) {
        result.get().add("handle timerexpired command");
    }

    @Command(aliases = "quit", desc = "")
    public void quit(CommandResult result) {
        if (game.getState() != GameState.RUNNING) {
            result.get().add("<b>That's not a verb I recognise.</b>");
            return;
        }

        result.get().add("<b>bye bye</b>");
        game.setState(GameState.FINISHED);
    }

    @Command(aliases = "inventory", desc = "")
    public void inventory(CommandResult result) {
        result.get().add("handle inventory command");
    }

    @Command(aliases = "blank", desc = "")
    public void blank(CommandResult result) {
        result.get().add("I beg your pardon?");
    }
}
