package net.novucs.zombieserver.level.builder;

import net.novucs.zombieserver.level.Direction;
import net.novucs.zombieserver.level.Entrance;

import java.util.Objects;

/**
 * An {@link Entrance} builder.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class EntranceBuilder {

    private Direction direction = null;
    private String to = null;
    private boolean locked = false;

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
     * Gets whether this entrance is locked.
     *
     * @return {@code true} if locked, otherwise {@code false}.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the direction this entrance is facing.
     *
     * @param direction the direction.
     * @return {@code this}.
     */
    public EntranceBuilder direction(Direction direction) {
        this.direction = direction;
        return this;
    }

    /**
     * Sets the room name this entrance leads to.
     *
     * @param to the room name.
     * @return {@code this}.
     */
    public EntranceBuilder to(String to) {
        this.to = to;
        return this;
    }

    /**
     * Sets whether this room is locked.
     *
     * @param locked {@code true} if it should be locked, otherwise
     *               {@code false}.
     * @return {@code this}.
     */
    public EntranceBuilder locked(boolean locked) {
        this.locked = locked;
        return this;
    }

    /**
     * Builds the new entrance.
     *
     * @return the new entrance.
     */
    public Entrance build() {
        if (to == null || direction == null) {
            throw new IllegalStateException("Entrance has not been fully built");
        }

        return new Entrance(direction, to, locked);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntranceBuilder that = (EntranceBuilder) o;
        return locked == that.locked &&
                direction == that.direction &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, to, locked);
    }

    @Override
    public String toString() {
        return "EntranceBuilder{" +
                "direction=" + direction +
                ", to='" + to + '\'' +
                ", locked=" + locked +
                '}';
    }
}
