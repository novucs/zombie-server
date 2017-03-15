package net.novucs.zombieserver.level;

import java.util.Objects;

/**
 * Represents an entrance from one {@link Room} to another.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class Entrance {

    private final Direction direction;
    private final String to;
    private boolean locked;

    /**
     * Constructs a new entrance.
     *
     * @param direction the direction this entrance is facing.
     * @param to        the room name this entrance leads to.
     * @param locked    whether the entrance is locked.
     */
    public Entrance(Direction direction, String to, boolean locked) {
        this.direction = direction;
        this.to = to;
        this.locked = locked;
    }

    /**
     * Gets the direction this entrance is facing.
     *
     * @return the direction.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Gets the room name this entrance leads to.
     *
     * @return the room name.
     */
    public String getTo() {
        return to;
    }

    /**
     * Whether this entrance is locked.
     *
     * @return {@code true} if locked, otherwise {@link false}.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets whether this entrance is locked.
     *
     * @param locked {@code true} if the entrance should be locked, otherwise
     *               {@link false}.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrance entrance = (Entrance) o;
        return locked == entrance.locked &&
                direction == entrance.direction &&
                Objects.equals(to, entrance.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, to, locked);
    }

    @Override
    public String toString() {
        return "Entrance{" +
                "direction=" + direction +
                ", to=" + to +
                ", locked=" + locked +
                '}';
    }
}
