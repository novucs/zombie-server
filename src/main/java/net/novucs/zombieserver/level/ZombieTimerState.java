package net.novucs.zombieserver.level;

/**
 * Identifies the state of the zombie timer.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public enum ZombieTimerState {

    // Starts a new timer, should be set when a player walks into a room
    // filled with zombies.
    START,

    // When the player has neither killed any zombies or moved into a room
    // with more zombies during the current tick.
    UNCHANGED,

    // Stops the timer, should be set when the player kills all viewed zombies.
    STOP
}
