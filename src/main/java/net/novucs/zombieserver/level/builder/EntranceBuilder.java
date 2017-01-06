package net.novucs.zombieserver.level.builder;

import net.novucs.zombieserver.level.Direction;
import net.novucs.zombieserver.level.Entrance;
import net.novucs.zombieserver.level.Room;

public class EntranceBuilder {

    private Direction direction = null;
    private Room to = null;
    private boolean locked = false;

    public EntranceBuilder direction(Direction direction) {
        this.direction = direction;
        return this;
    }

    public EntranceBuilder to(Room to) {
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
}
