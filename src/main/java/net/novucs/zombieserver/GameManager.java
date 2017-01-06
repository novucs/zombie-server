package net.novucs.zombieserver;

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
import net.novucs.zombieserver.network.ConnectionManager;

import java.util.List;

/**
 * The core game manager, provides accessor methods for the {@link ConnectionManager}
 * to use.
 *
 * @author William Randall
 */
public class GameManager {

    private final Dispatcher commandDispatcher;

    private GameManager(Dispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    public static GameManager create() {
        Dispatcher commandDispatcher = new SimpleDispatcher();

        // Use the parametric builder to construct CommandCallable objects from annotations.
        ParametricBuilder builder = new ParametricBuilder();
        builder.addBinding(new Bindings(), CommandResult.class);

        // Create and register CommandCallable objects from the methods of each passed object.
        builder.registerMethodsAsCommands(commandDispatcher, new CommandExecutor());

        return new GameManager(commandDispatcher);
    }

    /**
     * Determines whether the game should quit.
     *
     * @return <tt>true</tt> if the game should quit.
     */
    public boolean shouldQuit() {
        return false;
    }

    /**
     * Gets the text sent to the player when the game begins.
     *
     * @return the text sent to the player.
     */
    public String begin() {
        return "this is the message that gets displayed when game begins";
    }

    /**
     * Gets the current score.
     *
     * @return the score.
     */
    public int currentScore() {
        return 0;
    }

    /**
     * Gets if the zombie timer should be started on the client. This returns
     * <tt>true</tt> when the player moves to a new room that contains zombies.
     * After being is called, the server-side timer state is reset until the
     * player enters another zombie-infested room.
     *
     * @return <tt>true</tt> if the client zombie timer should be enabled.
     */
    public boolean enableTimer() {
        return false;
    }

    /**
     * Gets if the zombie timer should be disabled on the client. This returns
     * <tt>true</tt> when the player has just killed all zombies in the current
     * room. After being called, the server-side state resets and future calls
     * to this method return <tt>false</tt>.
     *
     * @return <tt>true</tt> if the client zombie timer should be disabled.
     */
    public boolean disableTimer() {
        return false;
    }

    /**
     * Process commands sent by the player.
     *
     * @param command the command to be processed.
     * @return the output to be displayed.
     */
    public List<String> processCmd(String command) {
        // Create the locals.
        CommandResult result = new CommandResult();
        CommandLocals locals = new CommandLocals();
        locals.put(CommandResult.class, result);

        try {
            // Execute commandDispatcher with the fully reconstructed command message.
            commandDispatcher.call(command, locals, new String[0]);
        } catch (InvalidUsageException e) {
            // Invalid command usage should not be harmful. Print something friendly.
            result.get().add("<b>That's not a verb I recognise.</b>");
        } catch (AuthorizationException e) {
            // Print friendly message in case of permission failure.
            result.get().add("<b>You do not have permission.</b>");
        } catch (CommandException e) {
            // Everything else is unexpected and should be considered an error.
            throw new RuntimeException(e);
        }

        return result.get();
    }
}
