package net.novucs.zombieserver.command;

import com.sk89q.intake.parametric.ParameterException;
import com.sk89q.intake.parametric.argument.ArgumentStack;
import com.sk89q.intake.parametric.binding.BindingBehavior;
import com.sk89q.intake.parametric.binding.BindingHelper;
import com.sk89q.intake.parametric.binding.BindingMatch;
import net.novucs.zombieserver.level.Direction;
import net.novucs.zombieserver.level.Item;

import java.util.Optional;

/**
 * All argument bindings, allows Intake to consume arguments supplied by the
 * player in their command and returns their relevant data type.
 *
 * @author William Randall
 * @author Gareth Perry
 * @author Chris Taylor
 */
public class Bindings extends BindingHelper {

    @BindingMatch(type = CommandResult.class, behavior = BindingBehavior.PROVIDES)
    public CommandResult getCommandResult(ArgumentStack context) throws ParameterException {
        CommandResult result = context.getContext().getLocals().get(CommandResult.class);

        if (result == null) {
            throw new ParameterException("No command result.");
        }

        return result;
    }

    @BindingMatch(type = Item.class, behavior = BindingBehavior.CONSUMES, consumedCount = 1)
    public Item getItem(ArgumentStack context) throws ParameterException {
        String itemName = context.next();
        Optional<Item> itemType = Item.get(itemName);

        if (!itemType.isPresent()) {
            throw new ParameterException("No item of this type exists.");
        }

        return itemType.get();
    }

    @BindingMatch(type = Direction.class, behavior = BindingBehavior.CONSUMES, consumedCount = 1)
    public Direction getDirection(ArgumentStack context) throws ParameterException {
        String shorthand = context.next();
        Optional<Direction> direction = Direction.getByShorthand(shorthand);

        if (!direction.isPresent()) {
            throw new ParameterException("No direction of this type exists.");
        }

        return direction.get();
    }
}
