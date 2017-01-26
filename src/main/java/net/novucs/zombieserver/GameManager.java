package net.novucs.zombieserver;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.sk89q.intake.CommandException;
import com.sk89q.intake.InvalidUsageException;
import com.sk89q.intake.context.CommandLocals;
import com.sk89q.intake.dispatcher.Dispatcher;
import com.sk89q.intake.dispatcher.SimpleDispatcher;
import com.sk89q.intake.parametric.ParametricBuilder;
import com.sk89q.intake.util.auth.AuthorizationException;
import net.novucs.zombieserver.command.Bindings;
import net.novucs.zombieserver.command.CommandExecutor;
import net.novucs.zombieserver.command.CommandResult;
import net.novucs.zombieserver.level.*;
import net.novucs.zombieserver.network.ConnectionManager;

import java.util.regex.Pattern;

/**
 * The core game manager, provides accessor methods for the {@link ConnectionManager}
 * to use.
 *
 * @author William Randall
 */
public class GameManager {

    private static final Pattern IGNORED_MESSAGES = Pattern.compile("(For parameter '\\w+':)|( Unused parameters:.*)");
    private final Dispatcher commandDispatcher;
    private final World world;
    private final Multiset<Item> inventory = HashMultiset.create();
    private int score;
    private Room currentRoom;
    private GameState state = GameState.START;
    private ZombieTimerState zombieTimerState = ZombieTimerState.UNCHANGED;

    private GameManager(Dispatcher commandDispatcher, World world, Room currentRoom) {
        this.commandDispatcher = commandDispatcher;
        this.world = world;
        this.currentRoom = currentRoom;
    }

    public static GameManager create(World world) {
        Dispatcher commandDispatcher = new SimpleDispatcher();
        GameManager game = new GameManager(commandDispatcher, world, world.getStart());

        // Use the parametric builder to construct CommandCallable objects from annotations.
        ParametricBuilder builder = new ParametricBuilder();
        builder.addBinding(new Bindings(), CommandResult.class, Item.class, Direction.class);

        // Create and register CommandCallable objects from the methods of each passed object.
        builder.registerMethodsAsCommands(commandDispatcher, new CommandExecutor(game));

        return game;
    }

    public Dispatcher getCommandDispatcher() {
        return commandDispatcher;
    }

    public World getWorld() {
        return world;
    }

    public Multiset<Item> getInventory() {
        return inventory;
    }

    /**
     * Gets the current score.
     *
     * @return the score.
     */
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore() {
        this.score++;
    }

    public void decrementScore() {
        this.score--;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public ZombieTimerState getZombieTimerState() {
        return zombieTimerState;
    }

    public void setZombieTimerState(ZombieTimerState zombieTimerState) {
        this.zombieTimerState = zombieTimerState;
    }

    /**
     * Process commands sent by the player.
     *
     * @param command the command to be processed.
     * @return the output to be displayed.
     */
    public CommandResult executeCommand(String command) {
        // Create the locals.
        CommandResult result = new CommandResult();
        CommandLocals locals = new CommandLocals();
        locals.put(CommandResult.class, result);

        try {
            // Execute commandDispatcher with the fully reconstructed command message.
            commandDispatcher.call(command, locals, new String[0]);
        } catch (InvalidUsageException e) {
            // Invalid command usage should not be harmful. Print something friendly.
            if (e.getCommand() == commandDispatcher) {
                result.add("<b>That's not a verb I recognise.</b>");
            } else {
                result.add("<b>" + IGNORED_MESSAGES.matcher(e.getMessage()).replaceAll("") + "</b>");
            }
        } catch (AuthorizationException e) {
            // Print friendly message in case of permission failure.
            result.add("<b>You do not have permission.</b>");
        } catch (CommandException e) {
            // Everything else is unexpected and should be considered an error.
            throw new RuntimeException(e);
        }

        return result;
    }
}
