package net.novucs.zombieserver;

import java.util.ArrayList;
import java.util.List;

/**
 * class that implements the ZombieBot interface and plays the game
 *
 * @author br-gaster
 */
public class ZombieBot implements world.ZombieBot {

    ZombieBot() {
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
        String[] args = command.split("\\s");
        List<String> result = new ArrayList<>();

        switch (args[0]) {
            case "info":
                result.add("handle info command");
                break;
            case "look":
                result.add("handle look command");
                break;
            case "move":
                result.add("handle move command");
                break;
            case "pickup":
                result.add("handle pickup command");
                break;
            case "kill":
                result.add("handle kill command");
                break;
            case "drop":
                result.add("handle drop command");
                break;
            case "timerexpired":
                result.add("handle timeexpired command");
                break;
            case "quit":
                result.add("handle quit command");
                break;
            case "inventory":
                result.add("handle inventory command");
                break;
            case "blank":
                result.add("I beg your pardon?");
                break;
            case "":
                break;
            default:
                result.add("<b>That's not a verb I recognise.</b>");
        }

        return result;
    }
}
