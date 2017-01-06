package net.novucs.zombieserver.command;

import com.sk89q.intake.Command;
import net.novucs.zombieserver.GameManager;
import net.novucs.zombieserver.GameState;

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

        result.get().add("this is the message that gets displayed when game begins");
        game.setState(GameState.RUNNING);
    }

    @Command(aliases = "info", desc = "")
    public void info(CommandResult result) {
        result.get().add("handle info command");
    }

    @Command(aliases = "look", desc = "")
    public void look(CommandResult result) {
        result.get().add("handle look command");
    }

    @Command(aliases = "move", desc = "")
    public void move(CommandResult result) {
        result.get().add("handle move command");
    }

    @Command(aliases = "pickup", desc = "")
    public void pickup(CommandResult result) {
        result.get().add("handle pickup command");
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
