package net.novucs.zombieserver.command;

import com.sk89q.intake.parametric.ParameterException;
import com.sk89q.intake.parametric.argument.ArgumentStack;
import com.sk89q.intake.parametric.binding.BindingBehavior;
import com.sk89q.intake.parametric.binding.BindingHelper;
import com.sk89q.intake.parametric.binding.BindingMatch;
import net.novucs.zombieserver.level.Item;
import net.novucs.zombieserver.level.ItemType;

import java.util.Optional;

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
        Optional<ItemType> itemType = ItemType.get(itemName);

        if (!itemType.isPresent()) {
            throw new ParameterException("No item of this type exists.");
        }

        return Item.of(itemType.get());
    }
}
