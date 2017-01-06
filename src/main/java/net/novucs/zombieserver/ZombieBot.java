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

import java.util.List;

/**
 * class that implements the ZombieBot interface and plays the game
 *
 * @author br-gaster
 */
public class ZombieBot implements world.ZombieBot {

    private final Dispatcher commandDispatcher;

    private ZombieBot(Dispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    public static ZombieBot create() {
        Dispatcher commandDispatcher = new SimpleDispatcher();

        // Use the parametric builder to construct CommandCallable objects from annotations.
        ParametricBuilder builder = new ParametricBuilder();
        builder.addBinding(new Bindings(), CommandResult.class);

        // Create and register CommandCallable objects from the methods of each passed object.
        builder.registerMethodsAsCommands(commandDispatcher, new CommandExecutor());

        return new ZombieBot(commandDispatcher);
    }

    /**
     * should game quit
     *
     * @return return true if exit program, otherwise false
     */
    @Override
    public boolean shouldQuit() {
        return false;
    }

    /**
     * prompt to be displayed to user
     *
     * @return
     */
    @Override
    public String begin() {
        return "this is the message that gets displayed when game begins";
    }

    /**
     * compute current score
     *
     * @return current score
     */
    @Override
    public int currentScore() {
        return 0;
    }

    /**
     * should timer be enabled? if should be enabled, then method returns true,
     * and goes back into state of not enable.
     *
     * @return true if enable timer, otherwise false
     */
    public boolean enableTimer() {
        return false;
    }

    /**
     * should timer be disabled? if should be disabled, then method returns
     * true, and goes back into state of don't disable.
     *
     * @return
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
    @Override
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
