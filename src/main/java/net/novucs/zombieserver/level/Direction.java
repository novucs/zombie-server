package net.novucs.zombieserver.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A direction, used for determining where {@link Entrance}'s are facing.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public enum Direction {

    NORTH("N"),
    NORTH_WEST("NW"),
    WEST("W"),
    SOUTH_WEST("SW"),
    SOUTH("S"),
    SOUTH_EAST("SE"),
    EAST("E"),
    NORTH_EAST("NE");

    private static final Map<String, Direction> BY_SHORTHAND = new HashMap<>();
    private final String shorthand;

    /**
     * Constructs a new direction.
     * @param shorthand the shorthand name of this direction.
     */
    Direction(String shorthand) {
        this.shorthand = shorthand;
    }

    /**
     * Gets the shorthand name of this direction.
     * @return the shorthand name.
     */
    public String getShorthand() {
        return shorthand;
    }

    /**
     * Gets a direction by its shorthand.
     * @param shorthand the shorthand name.
     * @return the found direction or {@code Optional#empty()}
     */
    public static Optional<Direction> getByShorthand(String shorthand) {
        return Optional.ofNullable(BY_SHORTHAND.get(shorthand.toUpperCase()));
    }

    static {
        for (Direction direction : values()) {
            BY_SHORTHAND.put(direction.shorthand, direction);
        }
    }
}
