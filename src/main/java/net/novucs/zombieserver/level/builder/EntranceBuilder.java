package net.novucs.zombieserver.level.builder;

import net.novucs.zombieserver.level.Direction;
import net.novucs.zombieserver.level.Entrance;

import java.util.Objects;

public class EntranceBuilder {

    private Direction direction = null;
    private String to = null;
    private boolean locked = false;

    public Direction getDirection() {
        return direction;
    }

    public String getTo() {
        return to;
    }

    public boolean isLocked() {
        return locked;
    }

    public EntranceBuilder direction(Direction direction) {
        this.direction = direction;
        return this;
    }

    public EntranceBuilder to(String to) {
        this.to = to;
        return this;
    }

    public EntranceBuilder locked(boolean locked) {
        this.locked = locked;
        return this;
    }

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
