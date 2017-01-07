package net.novucs.zombieserver.level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    Direction(String shorthand) {
        this.shorthand = shorthand;
    }

    public String getShorthand() {
        return shorthand;
    }

    public static Optional<Direction> getByShorthand(String shorthand) {
        return Optional.ofNullable(BY_SHORTHAND.get(shorthand));
    }

    static {
        for (Direction direction : values()) {
            BY_SHORTHAND.put(direction.shorthand, direction);
        }
    }
}
